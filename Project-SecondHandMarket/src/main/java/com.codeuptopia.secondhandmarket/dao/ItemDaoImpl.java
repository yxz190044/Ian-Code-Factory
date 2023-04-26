import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ItemDaoImpl implements ItemDao {
    
    private final DatabaseConnection databaseConnection;
    private final RestHighLevelClient elasticSearchClient;
    
    @Inject
    public ItemDaoImpl(DatabaseConnection databaseConnection, RestHighLevelClient elasticSearchClient) {
        this.databaseConnection = databaseConnection;
        this.elasticSearchClient = elasticSearchClient;
    }
    
    @Override
    public void addItem(Item item) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO items (name, description, price) VALUES (?, ?, ?)")) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.executeUpdate();
            indexItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateItem(Item item) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE items SET name = ?, description = ?, price = ? WHERE id = ?")) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getId());
            stmt.executeUpdate();
            indexItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void deleteItem(long itemId) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM items WHERE id = ?")) {
            stmt.setLong(1, itemId);
            stmt.executeUpdate();
            // TODO: delete item from ElasticSearch index
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Item getItemById(long itemId) {
        Item item = null;
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items WHERE id = ?")) {
            stmt.setLong(1, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                item = new Item(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                null
                                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
    
    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Item item = new Item(
                                     rs.getInt("id"),
                                     rs.getString("name"),
                                     rs.getString("description"),
                                     rs.getDouble("price"),
                                     null
                                     );
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    @Override
    public void indexItem(Item item) {
        try {
            IndexRequest indexRequest = new IndexRequest("items");
            indexRequest.id(String.valueOf(item.getId()));
            indexRequest.source(item.toJson(), XContentType.JSON);
            elasticSearchClient.index(indexRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Item> searchItems(String query) {
        try {
            SearchRequest searchRequest = new SearchRequest("items");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));
            SearchHits searchHits = elasticSearchClient.search(searchRequest).getHits();
            List<Item> items = new ArrayList<>();
            for (SearchHit hit : searchHits) {
                Item item = Item.fromJson(hit.getSourceAsString());
                items.add(item);
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
