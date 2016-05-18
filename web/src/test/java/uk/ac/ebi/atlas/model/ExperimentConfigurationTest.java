
package uk.ac.ebi.atlas.model;

import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import uk.ac.ebi.atlas.model.differential.Contrast;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentConfigurationTest {

    private static final String CONTRAST_ID = "contrastId";
    private static final String REFERENCE_ASSAY_GROUP = "reference_assay_group";
    private static final String TEST_ASSAY_GROUP = "test_assay_group";
    private static final String NAME = "name";
    private static final String XML_CONTENT =
            "<configuration experimentType=\"microarray_1colour_mrna_differential\">" +
                    "    <analytics>" +
                    "        <assay_groups>" +
                    "            <assay_group id=\"" + REFERENCE_ASSAY_GROUP + "\">" +
                    "<assay technical_replicate_id=\"t29\">ERR315431</assay>\n" +
                    "<assay technical_replicate_id=\"t28\">ERR315343</assay>\n" +
                    "<assay technical_replicate_id=\"t28\">ERR315342</assay>\n" +
                    "<assay technical_replicate_id=\"t27\">ERR315332</assay>\n" +
                    "<assay technical_replicate_id=\"t29\">ERR315378</assay>\n" +
                    "            </assay_group>" +
                    "            <assay_group id=\"" + TEST_ASSAY_GROUP + "\">" +
                    "<assay>SRR057599</assay>\n" +
                    "<assay>SRR057600</assay>\n" +
                    "<assay>SRR057601</assay>\n" +
                    "<assay>SRR057602</assay>\n" +
                    "            </assay_group>" +
                    "        </assay_groups>" +
                    "        <contrasts>" +
                    "            <contrast id=\"" + CONTRAST_ID + "\">" +
                    "                <name>" + NAME + "</name>" +
                    "                <reference_assay_group>" + REFERENCE_ASSAY_GROUP + "</reference_assay_group>" +
                    "                <test_assay_group>" + TEST_ASSAY_GROUP + "</test_assay_group>" +
                    "            </contrast>" +
                    "        </contrasts>" +
                    "    </analytics>" +
                    "</configuration>";

    @Mock
    XMLConfiguration xmlConfigurationMock;

    @Mock
    SubnodeConfiguration configurationMock;

    ExperimentConfiguration subject;

    @Before
    public void setUp() throws Exception {
        when(xmlConfigurationMock.getStringArray("analytics/contrasts/contrast/@id")).thenReturn(new String[]{CONTRAST_ID});
        when(xmlConfigurationMock.getStringArray("/analytics/assay_groups/assay_group/@id")).thenReturn(new String[]{REFERENCE_ASSAY_GROUP, TEST_ASSAY_GROUP});
        when(xmlConfigurationMock.getStringArray("analytics/assay_groups/assay_group[@id=\'" + REFERENCE_ASSAY_GROUP + "\']/assay")).thenReturn(new String[]{REFERENCE_ASSAY_GROUP});
        when(xmlConfigurationMock.getStringArray("analytics/assay_groups/assay_group[@id=\'" + TEST_ASSAY_GROUP + "\']/assay")).thenReturn(new String[]{TEST_ASSAY_GROUP});
        when(xmlConfigurationMock.configurationAt("analytics/contrasts/contrast[@id=\'" + CONTRAST_ID + "\']")).thenReturn(configurationMock);
        when(configurationMock.getString(NAME)).thenReturn(NAME);
        when(configurationMock.getString(REFERENCE_ASSAY_GROUP)).thenReturn(REFERENCE_ASSAY_GROUP);
        when(configurationMock.getString(TEST_ASSAY_GROUP)).thenReturn(TEST_ASSAY_GROUP);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(XML_CONTENT.getBytes()));

        subject = new ExperimentConfiguration(xmlConfigurationMock, doc);
    }

    @Test
    public void testGetContrasts()  {
        Set<Contrast> contrasts = subject.getContrasts();
        assertThat(contrasts.size(), is(1));
        Contrast contrast = contrasts.iterator().next();
        assertThat(contrast.getId(), is(CONTRAST_ID));
        assertThat(contrast.getDisplayName(), is(NAME));
        assertThat(contrast.getReferenceAssayGroup(), contains("ERR315378", "ERR315332", "ERR315342", "ERR315343", "ERR315431"));
        assertThat(contrast.getTestAssayGroup(), contains("SRR057601", "SRR057602", "SRR057600", "SRR057599"));
    }

    @Test
    public void testGetAssayGroups()  {
        AssayGroups assayGroups = subject.getAssayGroups();
        assertThat(assayGroups.getAssayGroupIds(), hasSize(2));
    }

    @Test
    public void replicatesIsNumberOfUniqueTechnicalReplicates() {
        AssayGroups assayGroups = subject.getAssayGroups();
        assertThat(assayGroups.getAssayGroup(REFERENCE_ASSAY_GROUP).getReplicates(), is(3));
    }

    @Test
    public void replicatesIsNumberOfAssays() {
        AssayGroups assayGroups = subject.getAssayGroups();
        assertThat(assayGroups.getAssayGroup(TEST_ASSAY_GROUP).getReplicates(), is(4));
    }

    @Test
    public void testGetExperimentType() {
        assertThat(subject.getExperimentType(), is(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL));
    }
}