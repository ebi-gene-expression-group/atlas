package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiffAnalyticsDocumentStreamTest {

    public static final String CONTRAST1 = "g1_g2";
    public static final String CONTRAST2 = "g1_g3";
    public static final String CONTRAST3 = "g1_g4";
    private static final String GENEID1 = "GENEID_1";
    private static final String GENE_MICROARRAY = "ENSG00000000003";
    public static final String DESIGN_ELEMENT = "209108_at";
    public static final String PLANTS_KINGDOM = "plants";
    public static final String PLANTS_ENSEMBLDB = "plants";
    private static final String SPECIES3 = "species3";
    public static final String CONDITION_SEARCH_1 = "condition1";
    public static final String CONDITION_SEARCH_2 = "condition2";
    public static final String CONDITION_SEARCH_3 = "condition3";
    Species species =  new Species(SPECIES3,SPECIES3, PLANTS_KINGDOM,PLANTS_ENSEMBLDB);
    DifferentialExperiment rnaSeqExperiment = new DifferentialExperiment("E-GEOD-38400", new Date(), new HashSet<Contrast>(),
            "description", false, true,species, Sets
            .newHashSet("PubMedId"), mock(ExperimentDesign.class));

    DiffAnalyticsDocumentStream subject;

    @Test
    public void test() {
        ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_DIFFERENTIAL;
        SetMultimap<String, String> conditionSearchTermsByContrastId = ImmutableSetMultimap.of(CONTRAST1, CONDITION_SEARCH_1, CONTRAST2, CONDITION_SEARCH_2, CONTRAST3, CONDITION_SEARCH_3);
        Map<String, Integer> numReplicatesByContrastId = ImmutableMap.of(CONTRAST1, 3, CONTRAST2, 3, CONTRAST3, 3);

        double pValue = 1.0;
        double foldChange = -0.0979807106778182;
        Iterable<? extends DifferentialAnalytics> inputStream = ImmutableList.of(new RnaSeqDifferentialAnalytics(GENEID1, CONTRAST3, pValue, foldChange));

        subject = new DiffAnalyticsDocumentStream(
                rnaSeqExperiment, inputStream, conditionSearchTermsByContrastId,
                numReplicatesByContrastId, ImmutableMap.of(GENEID1, "foo"));

        Iterator<AnalyticsDocument> analyticsDocumentIterator = subject.iterator();

        AnalyticsDocument analyticsDocument1 = analyticsDocumentIterator.next();

        assertThat(analyticsDocumentIterator.hasNext(), is(false));

        assertThat(analyticsDocument1.getBioentityIdentifier(), is(GENEID1));
        assertThat(analyticsDocument1.getExperimentAccession(), is(rnaSeqExperiment.getAccession()));
        assertThat(analyticsDocument1.getExperimentType(), is(experimentType));
        assertThat(analyticsDocument1.getContrastId(), is(CONTRAST3));
        assertThat(analyticsDocument1.getSpecies(), is(SPECIES3));
        assertThat(analyticsDocument1.getConditionsSearch(), is(CONDITION_SEARCH_3));
        assertThat(analyticsDocument1.getFoldChange(), is(foldChange));
        assertThat(analyticsDocument1.getPValue(), is(pValue));
    }

    @Test
    public void noDocumentsProducedWhenFoldChangeIsZero() {
        SetMultimap<String, String> conditionSearchTermsByContrastId = ImmutableSetMultimap.of(CONTRAST1, CONDITION_SEARCH_1, CONTRAST2, CONDITION_SEARCH_2, CONTRAST3, CONDITION_SEARCH_3);
        Map<String, Integer> numReplicatesByContrastId = ImmutableMap.of(CONTRAST1, 3, CONTRAST2, 3, CONTRAST3, 3);

        double pValue = 1.0;
        double foldChange = 0;
        Iterable<? extends DifferentialAnalytics> inputStream = ImmutableList.of(new RnaSeqDifferentialAnalytics(GENEID1, CONTRAST3, pValue, foldChange));

        subject = new DiffAnalyticsDocumentStream(
                rnaSeqExperiment, inputStream, conditionSearchTermsByContrastId,
                numReplicatesByContrastId, ImmutableMap.of("", ""));

        Iterator<AnalyticsDocument> analyticsDocumentIterator = subject.iterator();

        assertThat(analyticsDocumentIterator.hasNext(), is(true));

    }

    @Test
    public void documentsProducedForMicroArrayDifferential() {
        String experimentAccession = "E-MEXP-3628";
        ExperimentType experimentType = ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL;
        SetMultimap<String, String> conditionSearchTermsByContrastId = ImmutableSetMultimap.of(CONTRAST1, CONDITION_SEARCH_1, CONTRAST2, CONDITION_SEARCH_2, CONTRAST3, CONDITION_SEARCH_3);
        Map<String, Integer> numReplicatesByContrastId = ImmutableMap.of(CONTRAST1, 3, CONTRAST2, 3, CONTRAST3, 3);

        double pValue = 0.0009736462611865;
        double foldChange = 2.29740315357143;
        double tStatistics = 4.50525664901083;
        Iterable<? extends DifferentialAnalytics> inputStream = ImmutableList.of(new MicroarrayDifferentialAnalytics(GENE_MICROARRAY, DESIGN_ELEMENT, CONTRAST3, pValue, foldChange, tStatistics));

        MicroarrayExperiment microarrayExperiment = new MicroarrayExperiment(ExperimentType
                .MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL,"E-MEXP-3628", new Date(), new
                HashSet<Contrast>(),
                "description", false, true, species,
                ImmutableSortedSet.<String>of(),
                ImmutableSortedSet.<String>of(),mock(ExperimentDesign.class), Sets
                .newHashSet("PubMedId"));

        subject = new DiffAnalyticsDocumentStream(microarrayExperiment, inputStream, conditionSearchTermsByContrastId,
                numReplicatesByContrastId, ImmutableMap.of(GENEID1, "foo"));

        Iterator<AnalyticsDocument> analyticsDocumentIterator = subject.iterator();

        AnalyticsDocument analyticsDocument1 = analyticsDocumentIterator.next();

        assertThat(analyticsDocumentIterator.hasNext(), is(false));

        assertThat(analyticsDocument1.getBioentityIdentifier(), is(GENE_MICROARRAY));
        assertThat(analyticsDocument1.getExperimentAccession(), is(experimentAccession));
        assertThat(analyticsDocument1.getExperimentType(), is(experimentType));
        assertThat(analyticsDocument1.getContrastId(), is(CONTRAST3));
        assertThat(analyticsDocument1.getSpecies(), is(SPECIES3));
        assertThat(analyticsDocument1.getConditionsSearch(), is(CONDITION_SEARCH_3));
        assertThat(analyticsDocument1.getFoldChange(), is(foldChange));
        assertThat(analyticsDocument1.getPValue(), is(pValue));
        assertThat(analyticsDocument1.getTStatistics(), is(tStatistics));
    }



}