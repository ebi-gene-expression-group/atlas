package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.commons.readers.XmlReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BaselineExperimentConfigurationTest {

    private static Path tmpFilePath;

    private String FACTORS_DEFINITION_E_MTAB_4925 =
            "<factors-definition>\n" +
            "    <defaultFilterFactors />\n" +
            "    <defaultQueryFactorType>CELL_TYPE</defaultQueryFactorType>\n" +
            "    <menuFilterFactorTypes />\n" +
            "    <landingPageDisplayName>Cell Types - BLUEPRINT rare hematopoietic cells</landingPageDisplayName>\n" +
            "    <speciesMapping />\n" +
            "    <orderFactor>curated</orderFactor>\n" +
            "    <dataProviderURL>http://dcc.blueprint-epigenome.eu/</dataProviderURL>\n" +
            "    <dataProviderDescription>The BLUEPRINT Epigenome project</dataProviderDescription>\n" +
            "    <fortLauderdale>true</fortLauderdale>\n" +
            "    <alternativeView>E-MTAB-3819</alternativeView>\n" +
            "</factors-definition>";


    private BaselineExperimentConfiguration subject;

    @BeforeClass
    public static void setUpClass() throws Exception {
        // In Commons Configuration 2 XMLConfiguration needs at least a well-formed XML file:
        // http://stackoverflow.com/questions/39573880/apache-commons-configuration2-how-to-read-data-from-inputstream
        tmpFilePath = Files.createTempFile("dummy", ".xml");
        Files.write(tmpFilePath, ImmutableList.of("<_/>"), Charset.forName("UTF-8"));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Files.delete(tmpFilePath);
    }

    @Before
    public void setUp() throws Exception {
        InputStream inputStream = new ByteArrayInputStream(FACTORS_DEFINITION_E_MTAB_4925.getBytes(StandardCharsets.UTF_8));

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> fileBuilder =
                new FileBasedConfigurationBuilder<>(XMLConfiguration.class)
                        .configure(params.xml()
                                .setPath(tmpFilePath.toString())
                                .setExpressionEngine(new XPathExpressionEngine()));

        XMLConfiguration xmlConfiguration = fileBuilder.getConfiguration();
        xmlConfiguration.read(inputStream);

        subject = new BaselineExperimentConfiguration(new XmlReader(xmlConfiguration));
    }

    @Test
    public void testSomeProperties() throws Exception {
        assertThat(subject.getDataProviderURL().get(0), Matchers.containsString("blueprint-epigenome.eu"));
        assertEquals(true,subject.isFortLauderdale());
        assertThat(subject.getDefaultFilterFactors(), Matchers.<Factor>empty());
        assertThat(subject.getDataProviderDescription().get(0), Matchers.containsString("BLUEPRINT"));
        assertThat(subject.getExperimentDisplayName(), Matchers.containsString("BLUEPRINT rare"));
        assertThat(subject.getSpeciesMapping().entrySet(), Matchers.<Map.Entry<String,String>>empty());
        assertEquals(true,subject.orderCurated());
        assertThat(subject.getAlternativeViews(), Matchers.hasSize(1));

    }


}