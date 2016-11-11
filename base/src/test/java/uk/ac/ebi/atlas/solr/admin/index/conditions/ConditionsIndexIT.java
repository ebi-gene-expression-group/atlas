//
//package uk.ac.ebi.atlas.solr.admin.index.conditions;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import uk.ac.ebi.atlas.trader.cache.loader.DifferentialExperimentsCacheLoader;
//import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsIndex;
//
//import javax.inject.Inject;
//import javax.sql.DataSource;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//@WebAppConfiguration
//@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
//public class ConditionsIndexIT {
//
//    private static final String DIFFERENTIAL_ACCESION = "E-GEOD-22351";
//    @Inject
//    private BaselineConditionsIndex subject;
//
//    @Inject
//    private DifferentialExperimentsCacheLoader cacheLoader;
//
//
//    @Inject
//    @Qualifier("dataSourceOracle")
//    private DataSource dataSource;
//
//
//
//    //TODO: Use embedded (in memory) Solr!
//    @Test
//    public void testIndex() throws Exception {
//
////        DifferentialExperiment experiment = cacheLoader.load(DIFFERENTIAL_ACCESION);
////
////        subject.updateConditions(experiment);
//    }
//}
