package uk.ac.ebi.atlas.model.baseline;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;

public class BaselineExperimentConfigurationTest {

    String FACTORS_DEFINITION_E_MTAB_4925 =
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

    @Before
    public void setUp() throws Exception {
        InputStream inputStream = new ByteArrayInputStream(FACTORS_DEFINITION_E_MTAB_4925.getBytes(StandardCharsets.UTF_8));

        XMLConfiguration xmlConfiguration = new XMLConfiguration();
        xmlConfiguration.load(inputStream);
        xmlConfiguration.setExpressionEngine(new XPathExpressionEngine());

        subject = new BaselineExperimentConfiguration(xmlConfiguration);
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