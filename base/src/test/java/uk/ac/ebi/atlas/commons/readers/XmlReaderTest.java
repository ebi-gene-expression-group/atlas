package uk.ac.ebi.atlas.commons.readers;

import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class XmlReaderTest {

    private static String E_MTAB_2812 = "E-MTAB-2812";
    private static List<String> E_MTAB_2812_FACTORS_XML = Lists.newArrayList(
            "<factors-definition>\n",
            "    <defaultFilterFactors>\n",
            "        <filterFactor>\n",
            "            <type>SEX</type>\n",
            "            <value>hermaphrodite</value>\n",
            "        </filterFactor>\n",
            "        <filterFactor>\n",
            "            <type>ORGANISM_PART</type>\n",
            "            <value>organism</value>\n",
            "        </filterFactor>\n",
            "    </defaultFilterFactors>\n",
            "    <defaultQueryFactorType>DEVELOPMENTAL_STAGE</defaultQueryFactorType>\n",
            "    <menuFilterFactorTypes>SEX,ORGANISM_PART,DEVELOPMENTAL_STAGE</menuFilterFactorTypes>\n",
            "    <landingPageDisplayName>Developmental Stages - modENCODE</landingPageDisplayName>\n",
            "    <speciesMapping/>\n",
            "</factors-definition>"
    );

    private static String E_PROT_3 = "E-PROT-3";
    private static List<String> E_PROT_3_CONFIGURATION_XML = Lists.newArrayList(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
            "",
            "<configuration experimentType=\"proteomics_baseline\" r_data=\"0\">",
            "    <analytics>",
            "        <assay_groups>",
            "            <assay_group id=\"g8\" label=\"cervix, uterine\">",
            "                <assay>squamous epithelial cells from cervix, uterine</assay>",
            "                <assay>glandular cells from cervix, uterine</assay>",
            "        </assay_groups>",
            "    </analytics>",
            "</configuration>"
    );

    private static MockDataFileHub dataFileHub;

    private XmlReader subject;

    @BeforeClass
    public static void setUpClass() throws Exception {
        dataFileHub = new MockDataFileHub();
        dataFileHub.addFactorsFile(E_MTAB_2812, E_MTAB_2812_FACTORS_XML);
//        dataFileHub.addConfigurationFile(E_MTAB_2812, E_MTAB_2812_FACTORS_XML);
    }

    @Test
    public void readFactorTypes() {
        subject = dataFileHub.getBaselineExperimentFiles(E_MTAB_2812).factors.get();
        assertThat(subject.read().get("menuFilterFactorTypes").size(), is(3));
    }

    @Test
    public void readConfiguration() {
//        subject = dataFileHub.getBaselineExperimentFiles(E_MTAB_2812).factors.get();
//        assertThat(subject.read().get("menuFilterFactorTypes").size(), is(3));
    }


//
//    @Test
//    public void readWholeDocument() throws Exception {
//        assertThat(subject.read(), is("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\\\"no\\\"?>" + BLAH));
//    }

}