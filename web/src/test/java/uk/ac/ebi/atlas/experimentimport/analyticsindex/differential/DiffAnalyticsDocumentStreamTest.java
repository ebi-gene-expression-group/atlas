package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsDao;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiffAnalyticsDocumentStreamTest {

    public static final String CONTRAST1 = "g1_g2";
    public static final String CONTRAST2 = "g1_g3";
    public static final String CONTRAST3 = "g1_g4";
    private static final String GENEID1 = "GENEID_1";
    public static final String SPECIES1 = "arabidopsis thaliana";
    public static final String PLANTS_KINGDOM = "plants";
    public static final String PLANTS_ENSEMBLDB = "plants";
    private static final String SPECIES3 = "species3";
    public static final String PROTEIN_CODING = "protein_coding";
    public static final String CONDITION_SEARCH_1 = "condition1";
    public static final String CONDITION_SEARCH_2 = "condition2";
    public static final String CONDITION_SEARCH_3 = "condition3";

    DiffAnalyticsDocumentStream subject;

    @Mock
    IdentifierSearchTermsDao identifierSearchTermsDao;

    @Mock
    SpeciesKingdomTrader speciesKingdomTraderMock;

    @Test
    public void test() {
        String experimentAccession = "E-GEOD-38400";
        ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_DIFFERENTIAL;
        Set<String> factors = ImmutableSet.of("genotype");
        Map<String, String> ensemblSpeciesByContrastId = ImmutableMap.of(CONTRAST1, SPECIES1, CONTRAST2, SPECIES1, CONTRAST3, SPECIES3);
        SetMultimap<String, String> conditionSearchTermsByContrastId = ImmutableSetMultimap.of(CONTRAST1, CONDITION_SEARCH_1, CONTRAST2, CONDITION_SEARCH_2, CONTRAST3, CONDITION_SEARCH_3);
        Map<String, Integer> numReplicatesByContrastId = ImmutableMap.of(CONTRAST1, 3, CONTRAST2, 3, CONTRAST3, 3);
        when(identifierSearchTermsDao.fetchSearchTerms(Matchers.<String>any())).thenReturn(ImmutableSet.of(PROTEIN_CODING));
        when(speciesKingdomTraderMock.getKingdom(anyString())).thenReturn(PLANTS_KINGDOM);
        when(speciesKingdomTraderMock.getEnsemblDB(anyString())).thenReturn(PLANTS_ENSEMBLDB);

        double pValue = 1.0;
        double foldChange = -0.0979807106778182;
        Iterable<? extends DifferentialAnalytics> inputStream = ImmutableList.of(new RnaSeqDifferentialAnalytics(GENEID1, CONTRAST3, pValue, foldChange));

        subject = new DiffAnalyticsDocumentStream(experimentAccession, experimentType, factors,
                ensemblSpeciesByContrastId, inputStream, conditionSearchTermsByContrastId,
                numReplicatesByContrastId, identifierSearchTermsDao, speciesKingdomTraderMock);

        Iterator<AnalyticsDocument> analyticsDocumentIterator = subject.iterator();

        AnalyticsDocument analyticsDocument1 = analyticsDocumentIterator.next();

        assertThat(analyticsDocumentIterator.hasNext(), is(false));

        assertThat(analyticsDocument1.getBioentityIdentifier(), is(GENEID1));
        assertThat(analyticsDocument1.getExperimentAccession(), is(experimentAccession));
        assertThat(analyticsDocument1.getExperimentType(), is(experimentType));
        assertThat(analyticsDocument1.getIdentifierSearch(), is(GENEID1 + " " + PROTEIN_CODING));
        assertThat(analyticsDocument1.getContrastId(), is(CONTRAST3));
        assertThat(analyticsDocument1.getSpecies(), is(SPECIES3));
        assertThat(analyticsDocument1.getConditionsSearch(), is(CONDITION_SEARCH_3));
        assertThat(analyticsDocument1.getFoldChange(), is(foldChange));
    }

    @Test
    public void noDocumentsProducedWhenFoldChangeIsZero() {
        String experimentAccession = "E-GEOD-38400";
        ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_DIFFERENTIAL;
        Set<String> factors = ImmutableSet.of("genotype");
        Map<String, String> ensemblSpeciesByContrastId = ImmutableMap.of(CONTRAST1, SPECIES1, CONTRAST2, SPECIES1, CONTRAST3, SPECIES3);
        SetMultimap<String, String> conditionSearchTermsByContrastId = ImmutableSetMultimap.of(CONTRAST1, CONDITION_SEARCH_1, CONTRAST2, CONDITION_SEARCH_2, CONTRAST3, CONDITION_SEARCH_3);
        Map<String, Integer> numReplicatesByContrastId = ImmutableMap.of(CONTRAST1, 3, CONTRAST2, 3, CONTRAST3, 3);
        when(identifierSearchTermsDao.fetchSearchTerms(Matchers.<String>any())).thenReturn(ImmutableSet.of(PROTEIN_CODING));

        double pValue = 1.0;
        double foldChange = 0;
        Iterable<? extends DifferentialAnalytics> inputStream = ImmutableList.of(new RnaSeqDifferentialAnalytics(GENEID1, CONTRAST3, pValue, foldChange));

        subject = new DiffAnalyticsDocumentStream(experimentAccession, experimentType, factors,
                ensemblSpeciesByContrastId, inputStream, conditionSearchTermsByContrastId,
                numReplicatesByContrastId, identifierSearchTermsDao, speciesKingdomTraderMock
        );

        Iterator<AnalyticsDocument> analyticsDocumentIterator = subject.iterator();

        assertThat(analyticsDocumentIterator.hasNext(), is(true));

    }



}