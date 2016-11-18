package uk.ac.ebi.atlas.commons.readers;

import com.google.common.collect.Lists;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

public class XmlReaderFactoryTest {

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static MockDataFileHub dataFileHub;

    private XmlReaderFactory subject = new XmlReaderFactory();

    @BeforeClass
    public static void setUpClass() throws Exception {
        dataFileHub = new MockDataFileHub();
        dataFileHub.addFactorsFile(E_MTAB_2812, E_MTAB_2812_FACTORS_XML);
    }

    @Test
    public void createReaderOfExistingFile() {
        assertThat(
                subject.create(Paths.get(MessageFormat.format(dataFileHub.getFactorsFilePathTemplate(), E_MTAB_2812)), true),
                is(instanceOf(XmlReader.class)));
    }

    @Test
    public void createReaderOfMissingFile() {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(isA(ConfigurationException.class));

        subject.create(Paths.get("foobar.xml"), true);
    }
}