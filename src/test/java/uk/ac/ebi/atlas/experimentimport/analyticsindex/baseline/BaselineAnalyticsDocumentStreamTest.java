package uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.model.AssayGroupsFake;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentBuilder;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineAnalyticsDocumentStreamTest {

    private static final String DEFAULT_QUERY_FACTOR_TYPE = "ORGANISM_PART";

    private static final String GENEID1 = "GENE1";
    private static final String ASSAY_GROUP_ID1 = "G1";
    private static final BaselineAnalytics BASELINE_ANALYTICS1 = new BaselineAnalytics(GENEID1, ASSAY_GROUP_ID1, 1.1);

    private static final String GENEID2 = "GENE2";
    private static final String ASSAY_GROUP_ID2 = "G2";
    private static final BaselineAnalytics BASELINE_ANALYTICS2 = new BaselineAnalytics(GENEID2, ASSAY_GROUP_ID2, 2.2);
    public static final String G1_SEARCH_TERM_1 = "g1_1";

    private static final String UNKNOWN_GENEID = "GENE3";
    private static final BaselineAnalytics BASELINE_ANALYTICS3 = new BaselineAnalytics(UNKNOWN_GENEID, ASSAY_GROUP_ID2, 3.3);

    private static final String G2_SEARCH_TERM_1 = "g2_1";
    private static final String G2_SEARCH_TERM_2 = "g2_2";
    public static final String GENE_1_SEARCHTERM_1 = "gene1_searchterm_1";

    public static final String GENE_2_SEARCHTERM_1 = "gene2_searchterm_1";
    public static final String GENE_2_SEARCHTERM_2 = "gene2_searchterm_2";

    BaselineExperiment experiment ;

    @Mock
    ExperimentalFactors experimentalFactors;

    @Before
    public void setUp() throws Exception {
        when(experimentalFactors.getDefaultQueryFactorType()).thenReturn(DEFAULT_QUERY_FACTOR_TYPE);

        experiment = new BaselineExperimentBuilder()
                .forSpecies(new Species("homo sapiens", "homo sapiens", "ensembl", "animals"))
                .ofType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .withAccession("EXP1")
                .withDescription("")
                .withDisplayName("")
                .withPubMedIds(new HashSet<String>())
                .withExperimentDesign(mock(ExperimentDesign.class))
                .withExperimentalFactors(experimentalFactors)
                .withAssayGroups(AssayGroupsFake.get())
                .create();
    }

    @Mock
    private SpeciesKingdomTrader speciesKingdomTraderMock;

    private Map<String, String> bioentityIdToIdentifierSearch =
            ImmutableMap.of(
                    GENEID1, GENE_1_SEARCHTERM_1,
                    GENEID2, GENE_2_SEARCHTERM_1 + " " + GENE_2_SEARCHTERM_2,
                    UNKNOWN_GENEID, "");

    @Test
    public void test() {

        ImmutableSetMultimap.Builder<String, String> conditionSearchBuilder = ImmutableSetMultimap.builder();

        conditionSearchBuilder.putAll(ASSAY_GROUP_ID1, G1_SEARCH_TERM_1);
        conditionSearchBuilder.putAll(ASSAY_GROUP_ID2, G2_SEARCH_TERM_1, G2_SEARCH_TERM_2);

        ImmutableSetMultimap<String, String> conditionSearchTermByAssayAccessionId = conditionSearchBuilder.build();

        BaselineAnalyticsDocumentStream stream =
                new BaselineAnalyticsDocumentStream(experiment, ImmutableSet.of(BASELINE_ANALYTICS1,
                        BASELINE_ANALYTICS2, BASELINE_ANALYTICS3),conditionSearchTermByAssayAccessionId,
                        bioentityIdToIdentifierSearch);

        Iterator<AnalyticsDocument> analyticsDocumentIterator = stream.iterator();

        AnalyticsDocument analyticsDocument1 = analyticsDocumentIterator.next();
        AnalyticsDocument analyticsDocument2 = analyticsDocumentIterator.next();
        AnalyticsDocument analyticsDocument3 = analyticsDocumentIterator.next();

        assertThat(analyticsDocumentIterator.hasNext(), is(false));

        assertThat(analyticsDocument1.getBioentityIdentifier(), is(GENEID1));
        assertDataFromExperiment(analyticsDocument1);
        assertThat(analyticsDocument1.getDefaultQueryFactorType(), is(DEFAULT_QUERY_FACTOR_TYPE));
        assertThat(analyticsDocument1.getIdentifierSearch(), is(GENEID1 + " " + GENE_1_SEARCHTERM_1));
        assertThat(analyticsDocument1.getConditionsSearch(), is(G1_SEARCH_TERM_1));
        assertThat(analyticsDocument1.getAssayGroupId(), is(ASSAY_GROUP_ID1));
        assertThat(analyticsDocument1.getExpressionLevel(), is(1.1));

        assertThat(analyticsDocument2.getBioentityIdentifier(), is(GENEID2));
        assertDataFromExperiment(analyticsDocument2);
        assertThat(analyticsDocument2.getDefaultQueryFactorType(), is(DEFAULT_QUERY_FACTOR_TYPE));
        assertThat(analyticsDocument2.getIdentifierSearch(), is(GENEID2 + " " + GENE_2_SEARCHTERM_1 + " " + GENE_2_SEARCHTERM_2));
        assertThat(analyticsDocument2.getConditionsSearch(), is(G2_SEARCH_TERM_1 + " " + G2_SEARCH_TERM_2));
        assertThat(analyticsDocument2.getAssayGroupId(), is(ASSAY_GROUP_ID2));
        assertThat(analyticsDocument2.getExpressionLevel(), is(2.2));

        assertThat(analyticsDocument3.getBioentityIdentifier(), is(UNKNOWN_GENEID));
        assertDataFromExperiment(analyticsDocument3);
        assertThat(analyticsDocument3.getDefaultQueryFactorType(), is(DEFAULT_QUERY_FACTOR_TYPE));
        assertThat(analyticsDocument3.getIdentifierSearch(), is(UNKNOWN_GENEID));
        assertThat(analyticsDocument3.getConditionsSearch(), is(G2_SEARCH_TERM_1 + " " + G2_SEARCH_TERM_2));
        assertThat(analyticsDocument3.getAssayGroupId(), is(ASSAY_GROUP_ID2));
        assertThat(analyticsDocument3.getExpressionLevel(), is(3.3));

    }

    private void assertDataFromExperiment(AnalyticsDocument analyticsDocument){
        assertThat(analyticsDocument.getSpecies(), is(experiment.getSpecies().mappedName));
        assertThat(analyticsDocument.getKingdom(), is(experiment.getSpecies().kingdom));
        assertThat(analyticsDocument.getExperimentAccession(), is(experiment.getAccession()));
        assertThat(analyticsDocument.getExperimentType(), is(experiment.getType()));
    }

}