package uk.ac.ebi.atlas.experimentimport.analytics.index;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Collections;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnalyticsDocumentStreamTest {

    private static final String EXPERIMENT_ACCESSION = "EXP1";
    private static final ExperimentType EXPERIMENT_TYPE = ExperimentType.RNASEQ_MRNA_BASELINE;
    private static final String ENSEMBL_SPECIES = "homo sapiens";
    private static final String DEFAULT_QUERY_FACTOR_TYPE = "ORGANISM_PART";

    private static final String GENEID1 = "GENE1";
    private static final String ASSAYGROUPID1 = "G1";
    private static final BaselineAnalytics BASELINE_ANALYTICS1 = new BaselineAnalytics(GENEID1, ASSAYGROUPID1, 1.1);

    private static final String GENEID2 = "GENE2";
    private static final String ASSAYGROUPID2 = "G2";
    private static final BaselineAnalytics BASELINE_ANALYTICS2 = new BaselineAnalytics(GENEID2, ASSAYGROUPID2, 2.2);
    public static final String G1_SEARCH_TERM_1 = "g1_1";

    private static final String UNKNOWN_GENEID = "GENE3";
    private static final BaselineAnalytics BASELINE_ANALYTICS3 = new BaselineAnalytics(UNKNOWN_GENEID, ASSAYGROUPID2, 3.3);

    private static final String G2_SEARCH_TERM_1 = "g2_1";
    private static final String G2_SEARCH_TERM_2 = "g2_2";
    public static final String GENE_1_SEARCHTERM_1 = "gene1_searchterm_1";

    public static final String GENE_2_SEARCHTERM_1 = "gene2_searchterm_1";
    public static final String GENE_2_SEARCHTERM_2 = "gene2_searchterm_2";

    @Mock
    private IdentifierSearchTermsDao identifierSearchTermsDao;

    @Test
    public void test() {

        when(identifierSearchTermsDao.fetchSearchTerms(GENEID1)).thenReturn(ImmutableSet.of(GENE_1_SEARCHTERM_1));
        when(identifierSearchTermsDao.fetchSearchTerms(GENEID2)).thenReturn(ImmutableSet.of(GENE_2_SEARCHTERM_1, GENE_2_SEARCHTERM_2));
        when(identifierSearchTermsDao.fetchSearchTerms(UNKNOWN_GENEID)).thenReturn(Collections.<String>emptySet());

        ImmutableSetMultimap.Builder<String, String> conditionSearchBuilder = ImmutableSetMultimap.builder();

        conditionSearchBuilder.putAll(ASSAYGROUPID1, G1_SEARCH_TERM_1);
        conditionSearchBuilder.putAll(ASSAYGROUPID2, G2_SEARCH_TERM_1, G2_SEARCH_TERM_2);

        ImmutableSetMultimap<String, String> conditionSearchTermByAssayAccessionId = conditionSearchBuilder.build();

        AnalyticsDocumentStream stream = new AnalyticsDocumentStreamFactory(identifierSearchTermsDao).create(EXPERIMENT_ACCESSION, EXPERIMENT_TYPE, ENSEMBL_SPECIES, DEFAULT_QUERY_FACTOR_TYPE,
                ImmutableSet.of(BASELINE_ANALYTICS1, BASELINE_ANALYTICS2, BASELINE_ANALYTICS3), conditionSearchTermByAssayAccessionId);

        Iterator<AnalyticsDocument> analyticsDocumentIterator = stream.iterator();

        AnalyticsDocument analyticsDocument1 = analyticsDocumentIterator.next();
        AnalyticsDocument analyticsDocument2 = analyticsDocumentIterator.next();
        AnalyticsDocument analyticsDocument3 = analyticsDocumentIterator.next();

        assertThat(analyticsDocumentIterator.hasNext(), is(false));

        assertThat(analyticsDocument1.bioentityIdentifier, is(GENEID1));
        assertThat(analyticsDocument1.species, is(ENSEMBL_SPECIES));
        assertThat(analyticsDocument1.experimentAccession, is(EXPERIMENT_ACCESSION));
        assertThat(analyticsDocument1.experimentType, is(EXPERIMENT_TYPE));
        assertThat(analyticsDocument1.defaultQueryFactorType, is(DEFAULT_QUERY_FACTOR_TYPE));
        assertThat(analyticsDocument1.identifierSearch, is(GENEID1 + " " + GENE_1_SEARCHTERM_1));
        assertThat(analyticsDocument1.conditionsSearch, is(G1_SEARCH_TERM_1));
        assertThat(analyticsDocument1.assayGroupId, is(ASSAYGROUPID1));
        assertThat(analyticsDocument1.expressionLevel, is(1.1));

        assertThat(analyticsDocument2.bioentityIdentifier, is(GENEID2));
        assertThat(analyticsDocument2.species, is(ENSEMBL_SPECIES));
        assertThat(analyticsDocument2.experimentAccession, is(EXPERIMENT_ACCESSION));
        assertThat(analyticsDocument2.experimentType, is(EXPERIMENT_TYPE));
        assertThat(analyticsDocument2.defaultQueryFactorType, is(DEFAULT_QUERY_FACTOR_TYPE));
        assertThat(analyticsDocument2.identifierSearch, is(GENEID2 + " " + GENE_2_SEARCHTERM_1 + " " + GENE_2_SEARCHTERM_2));
        assertThat(analyticsDocument2.conditionsSearch, is(G2_SEARCH_TERM_1 + " " + G2_SEARCH_TERM_2));
        assertThat(analyticsDocument2.assayGroupId, is(ASSAYGROUPID2));
        assertThat(analyticsDocument2.expressionLevel, is(2.2));

        assertThat(analyticsDocument3.bioentityIdentifier, is(UNKNOWN_GENEID));
        assertThat(analyticsDocument3.species, is(ENSEMBL_SPECIES));
        assertThat(analyticsDocument3.experimentAccession, is(EXPERIMENT_ACCESSION));
        assertThat(analyticsDocument3.experimentType, is(EXPERIMENT_TYPE));
        assertThat(analyticsDocument3.defaultQueryFactorType, is(DEFAULT_QUERY_FACTOR_TYPE));
        assertThat(analyticsDocument3.identifierSearch, is(UNKNOWN_GENEID + " "));
        assertThat(analyticsDocument3.conditionsSearch, is(G2_SEARCH_TERM_1 + " " + G2_SEARCH_TERM_2));
        assertThat(analyticsDocument3.assayGroupId, is(ASSAYGROUPID2));
        assertThat(analyticsDocument3.expressionLevel, is(3.3));


    }



}