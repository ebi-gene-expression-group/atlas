package uk.ac.ebi.atlas.trader;

import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationTraderTest {

    MockDataFileHub mockDataFileHub;
    ConfigurationTrader subject;

    String experimentAccession = "E-MOCK-1";

    @Before
    public void setUp() throws Exception {
        mockDataFileHub = new MockDataFileHub();
        subject = new ConfigurationTrader(mockDataFileHub);

    }

    @Test
    public void testGetBaselineFactorsConfiguration() {


        mockDataFileHub.addFactorsFile(experimentAccession, Lists.newArrayList(
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
                "</factors-definition>"));
        BaselineExperimentConfiguration result = subject.getBaselineFactorsConfiguration(experimentAccession);

        assertThat(result.getDataProviderURL().get(0), Matchers.containsString("blueprint-epigenome.eu"));
        assertEquals(true,result.disclaimer().equals("fortLauderdale"));
        assertThat(result.getDefaultFilterFactors(), Matchers.<Factor>empty());
        assertThat(result.getDataProviderDescription().get(0), Matchers.containsString("BLUEPRINT"));
        assertThat(result.getExperimentDisplayName(), Matchers.containsString("BLUEPRINT rare"));
        assertThat(result.getSpeciesMapping().entrySet(), Matchers.<Map.Entry<String,String>>empty());
        assertEquals(true,result.orderCurated());
        assertThat(result.getAlternativeViews(), Matchers.hasSize(1));


    }

    @Test
    public void dataProviderUrlAndDescriptionCanBeCommaSeparated(){
        mockDataFileHub.addFactorsFile(experimentAccession, Lists.newArrayList(
        "<factors-definition>\n" +
                "        <defaultFilterFactors>\n" +
                "                <filterFactor>\n" +
                "                        <type>DEVELOPMENTAL_STAGE</type>\n" +
                "                        <value>adult</value>\n" +
                "                </filterFactor>\n" +
                "        </defaultFilterFactors>\n" +
                "    <defaultQueryFactorType>ORGANISM_PART</defaultQueryFactorType>\n" +
                "    <menuFilterFactorTypes>DEVELOPMENTAL_STAGE,ORGANISM_PART</menuFilterFactorTypes>\n" +
                "    <landingPageDisplayName>Proteomics - Tissues - Human Proteome Map</landingPageDisplayName>\n" +
                "    <dataProviderURL>http://www.nature.com/nature/journal/v509/n7502/full/nature13302.html,http://www.ebi.ac.uk/pride/archive/projects/PXD000561</dataProviderURL>\n" +
                "    <dataProviderDescription>A draft map of the human proteome,PRIDE Archive</dataProviderDescription>\n" +
                "    <speciesMapping/>\n" +
                "</factors-definition>"));

        BaselineExperimentConfiguration result = subject.getBaselineFactorsConfiguration(experimentAccession);

        assertThat(result.getDataProviderURL().size(), is(2));
        assertThat(result.getDataProviderDescription().size(), is(2));
    }




    void testExperimentalFactorValues(String valueForOrganismPartFactor) {
        mockDataFileHub.addFactorsFile(experimentAccession, Lists.newArrayList(
                MessageFormat.format("   <factors-definition>\n"+
                        "    <defaultFilterFactors>\n"+
                        "            <filterFactor>\n"+
                        "                <type>ORGANISM_PART</type>\n"+
                        "                <value>{0}</value>\n"+
                        "            </filterFactor>\n"+
                        "    </defaultFilterFactors>\n"+
                        "    <defaultQueryFactorType>CELL_LINE</defaultQueryFactorType>\n"+
                        "    <menuFilterFactorTypes>ORGANISM_PART, CELL_LINE</menuFilterFactorTypes>\n"+
                        "    <landingPageDisplayName>Name</landingPageDisplayName>\n"+
                        "    <speciesMapping/>\n"+
                        "</factors-definition>", valueForOrganismPartFactor)));
        BaselineExperimentConfiguration result = subject.getBaselineFactorsConfiguration(experimentAccession);

        assertThat(result.getDefaultFilterFactors().iterator().next().getValue(), is(valueForOrganismPartFactor));
    }

    @Test
    public void testSomeFactorValues(){
        testExperimentalFactorValues("brain");
        testExperimentalFactorValues("");
        testExperimentalFactorValues("skin-derived, feeder-free conditions");
    }

}
