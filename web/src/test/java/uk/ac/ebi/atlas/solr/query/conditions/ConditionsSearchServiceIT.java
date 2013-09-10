//package uk.ac.ebi.atlas.solr.query.conditions;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import javax.inject.Inject;
//import java.util.Collection;
//
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertThat;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//@WebAppConfiguration
//@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml"})
//public class ConditionsSearchServiceIT {
//
//    @Inject
//    private ConditionsSearchService subject;
//
//    @Test
//    public void testFindContrasts() throws Exception {
//        Collection<IndexedContrast> contrasts = subject.findContrasts("dibenzazepine");
//        assertThat(contrasts.size(), is(1));
//        assertThat(contrasts.iterator().next().getContrastId(), is("g2_g1"));
//        assertThat(contrasts.iterator().next().getExperimentAccession(), is("E-MEXP-1276"));
//    }
//}
