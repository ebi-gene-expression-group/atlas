//package uk.ac.ebi.atlas.solr.analytics.fullanalytics;
//
//import com.google.common.collect.ImmutableList;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.inject.Inject;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:applicationContext.xml")
//public class FullAnalyticsSolrCloudDaoIT {
//
//    @Inject
//    FullAnalyticsSolrCloudDao subject;
//
//    @Test
//    public void blah() {
//        subject.geneIdsWithAvgExpressionSortedByCountAscending("E-MTAB-5423", ImmutableList.of(), 0.5, 2000);
//    }
//
//}