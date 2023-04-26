
package com.codeuptopia.secondhandmarket.service;

import com.codeuptopia.secondhandmarket.model.Item;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SearchService {
    private RestHighLevelClient esClient;
    
    public SearchService(RestHighLevelClient esClient) {
        this.esClient = esClient;
    }
    
    public List<Item> searchItems(String query) {
        List<Item> items = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("items");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", query).fuzziness(Fuzziness.AUTO));
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(new TimeValue(1, TimeUnit.MINUTES));
        try {
            SearchResponse searchResponse = esClient.search(searchRequest);
            String scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            while (searchHits != null && searchHits.length > 0) {
                for (SearchHit hit : searchHits) {
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    String title = (String) sourceAsMap.get("title");
                    String description = (String) sourceAsMap.get("description");
                    // Create an Item object from the retrieved data and add it to the list
                    Item item = new Item(title, description);
                    items.add(item);
                }
                SearchResponse scrollResponse = esClient.prepareSearchScroll(scrollId)
                .setScroll(new TimeValue(1, TimeUnit.MINUTES))
                .execute().actionGet();
                scrollId = scrollResponse.getScrollId();
                searchHits = scrollResponse.getHits().getHits();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
