package uk.ac.ebi.atlas.commons.readers;

import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class XmlReaderTest {

    private static final String E_MTAB_2812 = "E-MTAB-2812";
    private static final List<String> E_MTAB_2812_FACTORS_XML = Lists.newArrayList(
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
            "    <menuFilterFactorTypes>SEX, ORGANISM_PART, DEVELOPMENTAL_STAGE</menuFilterFactorTypes>\n",
            "    <landingPageDisplayName>Developmental Stages - modENCODE</landingPageDisplayName>\n",
            "    <speciesMapping/>\n",
            "</factors-definition>"
    );

    private static MockDataFileHub dataFileHub;

    private XmlReader subject;

    @BeforeClass
    public static void setUpClass() {
        dataFileHub = MockDataFileHub.create();
        dataFileHub.addFactorsFile(E_MTAB_2812, E_MTAB_2812_FACTORS_XML);
//        dataFileHub.addConfigurationFile(E_MTAB_2812, E_MTAB_2812_FACTORS_XML);
    }

    @Test
    public void readDefaultFilterFactors() {
        subject = dataFileHub.getRnaSeqBaselineExperimentFiles(E_MTAB_2812).baselineExperimentFiles.factors.get();
        assertThat(subject.getMap("defaultFilterFactors", "type", "value").size(), is(2));
    }

    @Test
    public void readConfiguration() {
//        subject = dataFileHub.getBaselineExperimentFiles(E_MTAB_2812).factors.get();
//        assertThat(subject.read().get("menuFilterFactorTypes").size(), is(3));
    }

}
