package com.amazon.a4kcontentmanagement.tools.search.backfill;

import amazon.platform.config.AppConfig;
import com.amazon.a4k.data.dao.QueryDAO;
import com.amazon.a4k.elasticsearch.model.ElasticSearchDocument;
import com.amazon.a4k.itemids.ItemIdUtil;
import com.amazon.a4kcontentmanagement.tools.model.A4KCatalogModel;
import com.amazon.contentsearch.evaluation.CatalogMetadataEvaluator;
import com.amazon.contentsearch.model.ErrorResult;
import com.amazon.contentsearch.upload.CatalogDataUploader;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.google.common.collect.Lists;
import io.vavr.control.Either;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class
SearchBackfillRunnableTest {
	private static final String INDEX = "sub_test3";
	private static final String END_POINT = "endpoint";
	private static String MARKET_PLACE_ID = "marketplace";
	private static String SUBSCRIPTION_ID = "subscriptionId";
	
	private List<A4KCatalogModel> catalog;
	private SearchBackfillRunnable searchBackfillRunnable;
	
	@Mock
	private QueryDAO<DynamoDBScanExpression, A4KCatalogModel> queryDao;
	@Mock
	private Supplier<DynamoDBScanExpression> supplier;
	@Mock
	private DynamoDBScanExpression dynamoDBScanExpression;
	@Mock
	private ItemIdUtil itemIdUtil;
	@Mock
	private CatalogMetadataEvaluator catalogMetadataEvaluator;
	@Mock
	private CatalogDataUploader catalogDataUploader;
	
	@Mock
	private ExcelDao excelDao;
	
	@BeforeClass
	public static void setUp() {
		if (AppConfig.isInitialized()) {
			AppConfig.destroy();
		}
		AppConfig.initialize("test", "tahoe", new String[]{"--root=.", "--domain=test", "--realm=USAmazon"});
		AppConfig.insertString("A4KElasticSearch.Catalog.Index", INDEX);
		AppConfig.insertString("A4KElasticSearch.Catalog.EndPoint", END_POINT);
	}
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		searchBackfillRunnable = new SearchBackfillRunnable(queryDao, supplier, MARKET_PLACE_ID, SUBSCRIPTION_ID,
															itemIdUtil, catalogMetadataEvaluator, catalogDataUploader,
															excelDao);
		
		catalog = getCatalog();
		when(supplier.get()).thenReturn(dynamoDBScanExpression);
		when(queryDao.query(dynamoDBScanExpression)).thenReturn(catalog.stream());
		when(catalogDataUploader.uploadCatalogRecordIntoElasticSearch(any())).thenReturn(new ArrayList<>());
	}
	
	@Test
	public void happyCase() {
		catalog.forEach(content -> {
			if (content.getProductType().equals("VIDEO")) {
				when(itemIdUtil.isValidGUID(content.getContentId())).thenReturn(false);
			}
		});
		
		final ErrorResult errorResult = ErrorResult.of(Collections.emptySet(), "");
		final List<ElasticSearchDocument> documentList = getElasticSearchDocument();
		final List<Either<ErrorResult, List<ElasticSearchDocument>>> evaluatorResponse =  Lists.newArrayList(
																											 Either.left(errorResult),
																											 Either.right(documentList)
																											 );
		
		when(catalogMetadataEvaluator.evaluateMetadataForCatalog(any())).thenReturn(evaluatorResponse);
		
		searchBackfillRunnable.run();
		final ArgumentCaptor<List<ElasticSearchDocument>> docCaptor = new ArgumentCaptor<>();
		verify(catalogDataUploader).uploadCatalogRecordIntoElasticSearch(docCaptor.capture());
		assertTrue(documentList.containsAll(docCaptor.getValue()));
		assertEquals(documentList.size(), docCaptor.getValue().size());
	}
	
	@Test
	public void invalidGuidForVideo() {
		final List<String> videoContent = new ArrayList<>();
		catalog.forEach(content -> {
			if (content.getProductType().equals("VIDEO")) {
				when(itemIdUtil.isValidGUID(content.getContentId())).thenReturn(true);
				videoContent.add(content.getContentId());
			}
		});
		
		final ErrorResult errorResult = ErrorResult.of(Collections.emptySet(), "");
		final List<ElasticSearchDocument> documentList = getElasticSearchDocument().stream()
		.filter(doc -> !doc.getProductType().equals("VIDEO")).collect(Collectors.toList());
		final List<Either<ErrorResult, List<ElasticSearchDocument>>> evaluatorResponse =  Lists.newArrayList(
																											 Either.left(errorResult),
																											 Either.right(documentList)
																											 );
		
		when(catalogMetadataEvaluator.evaluateMetadataForCatalog(any())).thenReturn(evaluatorResponse);
		
		searchBackfillRunnable.run();
		final ArgumentCaptor<List<ElasticSearchDocument>> docCaptor = new ArgumentCaptor<>();
		verify(catalogDataUploader).uploadCatalogRecordIntoElasticSearch(docCaptor.capture());
		assertTrue(documentList.containsAll(docCaptor.getValue()));
		assertEquals(documentList.size(), docCaptor.getValue().size());
	}
	
	@Test
	public void incorrectSubscriptionId() {
		catalog.forEach(content -> {
			if (content.getProductType().equals("VIDEO")) {
				when(itemIdUtil.isValidGUID(content.getContentId())).thenReturn(false);
			}
		});
		
		final ErrorResult errorResult = ErrorResult.of(Collections.emptySet(), "");
		final List<ElasticSearchDocument> documentList = new ArrayList(getElasticSearchDocument());
		final ElasticSearchDocument docWithIncorrectSubs = ElasticSearchDocument.builder().subscriptionId("IncorrectSubs").build();
		documentList.add(docWithIncorrectSubs);
		
		final List<Either<ErrorResult, List<ElasticSearchDocument>>> evaluatorResponse =  Lists.newArrayList(
																											 Either.left(errorResult),
																											 Either.right(documentList)
																											 );
		
		when(catalogMetadataEvaluator.evaluateMetadataForCatalog(any())).thenReturn(evaluatorResponse);
		
		searchBackfillRunnable.run();
		final ArgumentCaptor<List<ElasticSearchDocument>> docCaptor = new ArgumentCaptor<>();
		verify(catalogDataUploader).uploadCatalogRecordIntoElasticSearch(docCaptor.capture());
		assertFalse(docCaptor.getValue().contains(docWithIncorrectSubs));
		assertTrue(documentList.containsAll(docCaptor.getValue()));
	}
	
	private List<ElasticSearchDocument> getElasticSearchDocument() {
		final ElasticSearchDocument doc1 = ElasticSearchDocument.builder().subscriptionId(SUBSCRIPTION_ID)
		.productType("VIDEO").build();
		final ElasticSearchDocument doc2 = ElasticSearchDocument.builder().subscriptionId(SUBSCRIPTION_ID)
		.productType("EBOOK").build();
		final ElasticSearchDocument doc3 = ElasticSearchDocument.builder().subscriptionId(SUBSCRIPTION_ID)
		.productType("APP").build();
		return Arrays.asList(doc1, doc2, doc3);
	}
	
	private List<A4KCatalogModel> getCatalog() {
		final A4KCatalogModel model1 = new A4KCatalogModel();
		model1.setContentId("ContentId1");
		model1.setProductType("VIDEO");
		final A4KCatalogModel model2 = new A4KCatalogModel();
		model2.setContentId("ContentId2");
		model2.setProductType("EBOOK");
		final A4KCatalogModel model3 = new A4KCatalogModel();
		model3.setContentId("ContentId3");
		model3.setProductType("APP");
		
		return Stream.of(model1, model2, model3).collect(Collectors.toList());
	}
}
