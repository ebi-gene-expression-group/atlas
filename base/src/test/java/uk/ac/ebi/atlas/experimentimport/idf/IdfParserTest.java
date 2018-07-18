package uk.ac.ebi.atlas.experimentimport.idf;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


public class IdfParserTest {

    private static final String E_MTAB_513 = "E-MTAB-513";

    private static final String E_MTAB_513_TITLE =
            "RNA-Seq of human individual tissues and mixture of 16 tissues (Illumina Body Map)";
    private static final String E_MTAB_513_AE_DISPLAY_NAME =
            "Study investigating RNA-Seq of human individual tissues and mixture of 16 tissues";
    private static final String[] E_MTAB_513_PUBMED_IDS_ARRAY = {"22496456", "22955988", "23258890"};
    private static final String[] E_MTAB_513_PUBLICATIONS_ARRAY = {"Publication 1", "Another publication", "Yet another publication"};
    private static final ImmutableSet<String> E_MTAB_513_PUBMED_IDS = ImmutableSet.copyOf(E_MTAB_513_PUBMED_IDS_ARRAY);
    private static final String E_MTAB_513_EXPECTED_CLUSTERS = "5";
    private static final String[] E_MTAB_513_ADDITIONAL_ATTRIBUTES= {"individual", "genotype", "FACS marker"};

    private static final String[][] E_MTAB_513_IDF_TXT = {
            {"Investigation Title", E_MTAB_513_TITLE},
            {"Comment[AEExperimentDisplayName]", E_MTAB_513_AE_DISPLAY_NAME},
            {"PubMed ID", E_MTAB_513_PUBMED_IDS_ARRAY[0], E_MTAB_513_PUBMED_IDS_ARRAY[1], E_MTAB_513_PUBMED_IDS_ARRAY[2]},
            {"Publication Title", E_MTAB_513_PUBLICATIONS_ARRAY[0], E_MTAB_513_PUBLICATIONS_ARRAY[1], E_MTAB_513_PUBLICATIONS_ARRAY[2]},
            {"Comment[EAExpectedClusters]", E_MTAB_513_EXPECTED_CLUSTERS},
            {"Comment[EAAdditionalAttributes]", E_MTAB_513_ADDITIONAL_ATTRIBUTES[0], E_MTAB_513_ADDITIONAL_ATTRIBUTES[1], E_MTAB_513_ADDITIONAL_ATTRIBUTES[2]}
    };

    private static final String[][] E_MTAB_513_IDF_TXT_MIXED_CASE = {
            {"INVESTIGATION TITLE   ", E_MTAB_513_TITLE},
            {" comment[AEExperimentDisplayName]", E_MTAB_513_AE_DISPLAY_NAME},
            {"PubMed id", E_MTAB_513_PUBMED_IDS_ARRAY[0], E_MTAB_513_PUBMED_IDS_ARRAY[1], E_MTAB_513_PUBMED_IDS_ARRAY[2]},
            {"publication title", E_MTAB_513_PUBLICATIONS_ARRAY[0], E_MTAB_513_PUBLICATIONS_ARRAY[1], E_MTAB_513_PUBLICATIONS_ARRAY[2]},
            {"comment[eaexpectedclusters]", E_MTAB_513_EXPECTED_CLUSTERS}
    };

    private MockDataFileHub dataFileHub;

    private IdfParser subject;

    @BeforeEach
    public void setUp() {
        dataFileHub = MockDataFileHub.create();

        subject = new IdfParser(dataFileHub);
    }

    @Test
    public void parse() {
        dataFileHub.addIdfFile(E_MTAB_513, Arrays.asList(E_MTAB_513_IDF_TXT));

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle()).isEqualTo(E_MTAB_513_AE_DISPLAY_NAME);
        assertThat(idfParserOutput.getPubmedIds()).containsOnlyElementsOf(E_MTAB_513_PUBMED_IDS);
        assertThat(idfParserOutput.getExpectedClusters()).isEqualTo(NumberUtils.toInt(E_MTAB_513_EXPECTED_CLUSTERS));
        assertThat(idfParserOutput.getMetadataFieldsOfInterest()).containsOnlyElementsOf(ImmutableSet.copyOf(E_MTAB_513_ADDITIONAL_ATTRIBUTES));
    }

    @Test
    public void parseMixedCaseKeys() {
        dataFileHub.addIdfFile(E_MTAB_513, Arrays.asList(E_MTAB_513_IDF_TXT_MIXED_CASE));

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle()).isEqualTo(E_MTAB_513_AE_DISPLAY_NAME);
        assertThat(idfParserOutput.getPubmedIds()).containsOnlyElementsOf(E_MTAB_513_PUBMED_IDS);
        assertThat(idfParserOutput.getExpectedClusters()).isEqualTo(NumberUtils.toInt(E_MTAB_513_EXPECTED_CLUSTERS));
    }

    @Test
    public void parseNoPubmedIds() {
        dataFileHub.addIdfFile(E_MTAB_513, Arrays.asList(E_MTAB_513_IDF_TXT[0], E_MTAB_513_IDF_TXT[1]));

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle()).isEqualTo(E_MTAB_513_AE_DISPLAY_NAME);
        assertThat(idfParserOutput.getPublications()).isEmpty();
    }

    @Test
    public void parseNoAeDisplayName() {
        dataFileHub.addIdfFile(E_MTAB_513, Arrays.asList(E_MTAB_513_IDF_TXT[0], E_MTAB_513_IDF_TXT[2], E_MTAB_513_IDF_TXT[3]));

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle()).isEqualTo(E_MTAB_513_TITLE);
        assertThat(idfParserOutput.getPubmedIds()).containsOnlyElementsOf(E_MTAB_513_PUBMED_IDS);
    }

    @Test
    public void parseNoAeDisplayNameNoTitle() {
        dataFileHub.addIdfFile(E_MTAB_513, Arrays.asList(E_MTAB_513_IDF_TXT[2], E_MTAB_513_IDF_TXT[3]));

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle()).isEmpty();
        assertThat(idfParserOutput.getPubmedIds()).containsOnlyElementsOf(E_MTAB_513_PUBMED_IDS);
    }

    @Test
    public void parseNothing() {
        dataFileHub.addIdfFile(E_MTAB_513, Collections.emptyList());

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle()).isEmpty();
        assertThat(idfParserOutput.getPublications()).isEmpty();
        assertThat(idfParserOutput.getExpectedClusters()).isEqualTo(0);
    }
}