package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class AnalyticsIndexDaoIT {

    @Inject
    AnalyticsIndexDAO analyticsIndexDAO;

    @Test
    @Ignore
    // TODO: fix (see http://bar:8085/artifact/ATLAS-TESTDELIVERY/shared/build-2496/failsafe-reports/uk.ac.ebi.atlas.experimentimport.analytics.index.AnalyticsIndexerIT.txt)
    public void addBaseline() {
//        AnalyticsDocument analyticsDocument = AnalyticsDocument.builder()
//                .bioentityIdentifier("delme")
//                .species("bar")
//                .experimentAccession("E-DELME")
//                .experimentType(ExperimentType.RNASEQ_MRNA_BASELINE)
//                .defaultQueryFactorType("ORGANISM_PART")
//                .identifierSearch("foo")
//                .conditionsSearch("brain")
//                .assayGroupId("g1")
//                .expressionLevel(1)
//                .build();
//        analyticsIndexDao.addDocument(analyticsDocument);
//
//        analyticsIndexDao.deleteDocumentsForExperimentAndCommit("E-DELME");
    }

    @Test
    @Ignore // TODO: fix (see http://bar:8085/artifact/ATLAS-TESTDELIVERY/shared/build-2496/failsafe-reports/uk.ac.ebi.atlas.experimentimport.analytics.index.AnalyticsIndexerIT.txt)
    public void addDiff() {
//        AnalyticsDocument analyticsDocument = AnalyticsDocument.builder()
//                .bioentityIdentifier("delme")
//                .species("bar")
//                .experimentAccession("E-DELME")
//                .experimentType(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)
//                .defaultQueryFactorType("genotype")
//                .identifierSearch("foo")
//                .conditionsSearch("wild type")
//                .contrastId("g1_g2")
//                .factors(Collections.singleton("genotype"))
//                .numReplicates(1)
//                .foldChange(0.02)
//                .build();
//        analyticsIndexDao.addDocument(analyticsDocument);

        //analyticsIndexer.deleteDocumentsForExperimentAndCommit("E-DELME");
    }

}