//package uk.ac.ebi.atlas.experimentimport.analyticsindex;
//
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import javax.inject.Inject;
//
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
//public class AnalyticsIndexerManagerIT {
//    @Inject
//    private AnalyticsIndexerManager subject;
//
//    // TODO Rethink tests, they incur in a massive slowdown with a full analytics index (maybe trim the experiment to
//    // TODO 100 genes or so?)
//    @Ignore
//    public void indexProteomicsExperiment() {
//        subject.addToAnalyticsIndex("E-PROT-1");
//    }
//}
