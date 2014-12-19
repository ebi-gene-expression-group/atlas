package uk.ac.ebi.atlas.solr.admin.index.analytics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class AnalyticsIndexerIT {

    @Inject
    AnalyticsIndexer analyticsIndexer;

    @Test
    public void addBaseline() {
        AnalyticsDocument analyticsDocument = AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .species("bar")
                .experimentAccession("E-TEST-BASELINE")
                .experimentType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .defaultQueryFactorType("ORGANISM_PART")
                .identifierSearch("foo")
                .conditionsSearch("bar")
                .assayGroupId("g1")
                .expressionLevel(1)
                .build();
        analyticsIndexer.addDocument(analyticsDocument);
    }

    @Test
    public void addDiff() {
        AnalyticsDocument analyticsDocument = AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .species("bar")
                .experimentAccession("E-TEST-DIFF")
                .experimentType(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)
                .defaultQueryFactorType("genotype")
                .identifierSearch("foo")
                .conditionsSearch("bar")
                .contrastId("g1_g2")
                .contrastType("genotype")
                .numReplicates(1)
                .foldChange(0.02)
                .build();
        analyticsIndexer.addDocument(analyticsDocument);
    }

}