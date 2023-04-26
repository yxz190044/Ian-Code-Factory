
package com.amazon.a4kcontentmanagement.tools.search.backfill;

import amazon.platform.config.AppConfig;
import com.amazon.a4k.data.dao.QueryDAO;
import com.amazon.a4k.elasticsearch.model.ElasticSearchDocument;
import com.amazon.a4k.itemids.ItemIdUtil;
import com.amazon.a4kcontentmanagement.tools.model.A4KCatalogModel;
import com.amazon.contentsearch.evaluation.CatalogMetadataEvaluator;
import com.amazon.contentsearch.model.ErrorResult;
import com.amazon.contentsearch.model.SearchUpdateKey;
import com.amazon.contentsearch.upload.CatalogDataUploader;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import io.searchbox.core.BulkResult;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Log4j2
public class SearchBackfillRunnable {
	
	//    @FlagSpec(help=".....", name="enable_execl_dao")
	private static final Boolean MANUAL_UPLOAD = AppConfig.findBoolean("manualUpload", false);
	
	private static final String PRODUCT_TYPE_VIDEO = "VIDEO";
	private final QueryDAO<DynamoDBScanExpression, A4KCatalogModel> subscriptionCatalogDao;
	private final Supplier<DynamoDBScanExpression> querySupplier;
	private final CatalogMetadataEvaluator catalogMetadataEvaluator;
	private final CatalogDataUploader catalogDataUploader;
	private final String marketPlaceId;
	private final ItemIdUtil itemIdUtil;
	private final String subscriptionId;
	
	private final ExcelDao excelDao;
	
	@Inject
	SearchBackfillRunnable(final QueryDAO<DynamoDBScanExpression, A4KCatalogModel> subscriptionCatalogDao,
						   final Supplier<DynamoDBScanExpression> querySupplier,
						   final @Named("marketplaceId") String marketPlaceId,
						   final @Named("subscriptionId") String subscriptionId,
						   final ItemIdUtil itemIdUtil,
						   final CatalogMetadataEvaluator catalogMetadataEvaluator,
						   final CatalogDataUploader catalogDataUploader,
						   final ExcelDao excelDao) {
		this.subscriptionCatalogDao = subscriptionCatalogDao;
		this.querySupplier = querySupplier;
		this.catalogMetadataEvaluator = catalogMetadataEvaluator;
		this.catalogDataUploader = catalogDataUploader;
		this.marketPlaceId = marketPlaceId;
		this.itemIdUtil = itemIdUtil;
		this.subscriptionId = subscriptionId;
		this.excelDao = excelDao;
	}
	
	public void run() {
	http://log.info("Running search backfill");
		
		final Stream<A4KCatalogModel> catalogStream = subscriptionCatalogDao.query(querySupplier.get());
		//        String filePath = "S3???";
		
		
		final Stream<A4KCatalogModel> excelStream = excelDao.getExcelInfo();
		
		final List<SearchUpdateKey> updateKeys = MANUAL_UPLOAD ? getSearchUpdateKeys(excelStream) : getSearchUpdateKeys(catalogStream);
		
		final List<Either<ErrorResult, List<ElasticSearchDocument>>> elasticSearchDocs =
		catalogMetadataEvaluator.evaluateMetadataForCatalog(updateKeys);
		final List<String> errorMessages = new ArrayList<>();
		final Set<ErrorResult> erroredBatches = elasticSearchDocs.stream()
		.filter(Either::isLeft)
		.map(Either::getLeft)
		.collect(Collectors.toSet());
		
		errorMessages.addAll(erroredBatches.stream()
							 .map(ErrorResult::getErrorMessage)
							 .collect(Collectors.toList()));
		log.error("Error messages are  [{}] ", errorMessages);
		
		final List<ElasticSearchDocument> esDocUpload = elasticSearchDocs.stream()
		.filter(Either::isRight)
		.map(Either::get)
		.flatMap(List::stream)
		// Need this filter as catalogMetadataEvaluator gets CatalogRecord for every contentId
		// present in SearchUpdateKeys. A particular content can be present in more than one
		// catalog.
		.filter(doc -> doc.getSubscriptionId().equals(subscriptionId))
		.collect(toList());
		
	http://log.info("Uploading doc for subscription id [{}] of size {}", subscriptionId, esDocUpload.size());
		final List<BulkResult> results = catalogDataUploader.uploadCatalogRecordIntoElasticSearch(esDocUpload);
		final List<BulkResult> failedResult = results.stream()
		.filter(res -> !res.isSucceeded())
		.collect(Collectors.toList());
		log.error("Failed result is [{}] ",  failedResult);
	}
	
	private List<SearchUpdateKey> getSearchUpdateKeys(Stream<A4KCatalogModel> catalogModelStream) {
		final List<SearchUpdateKey> searchUpdateKeys = new ArrayList<>();
		catalogModelStream.forEach(model -> {
			if (shouldUpdateContent(model)) {
				searchUpdateKeys.add(SearchUpdateKey.builder()
									 .contentId(model.getContentId())
									 .productType(model.getProductType())
									 .marketplaceId(marketPlaceId)
									 .build());
			}
		});
		return searchUpdateKeys;
	}
	
	private boolean shouldUpdateContent(final A4KCatalogModel model) {
		if (PRODUCT_TYPE_VIDEO.equals(model.getProductType()) && itemIdUtil.isValidGUID(model.getContentId())) {
			return false;
		}
		return true;
	}
}
