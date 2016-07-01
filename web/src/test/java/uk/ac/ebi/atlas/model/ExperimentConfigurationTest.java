package uk.ac.ebi.atlas.model;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import uk.ac.ebi.atlas.model.differential.Contrast;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExperimentConfigurationTest {

    private static final String RNASEQ_BASELINE_CONFIGURATION_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<configuration experimentType=\"microarray_1colour_mrna_differential\" r_data=\"1\">\n" +
                    "    <analytics>\n" +
                    "        <assay_groups>\n" +
                    "            <assay_group id=\"g1\">\n" +
                    "                <assay>A</assay>\n" +
                    "            </assay_group>\n" +
                    "            <assay_group id=\"g2\">\n" +
                    "                <assay technical_replicate_id=\"t1\">A</assay>\n" +
                    "                <assay technical_replicate_id=\"t1\">B</assay>\n" +
                    "            </assay_group>\n" +
                    "            <assay_group id=\"g3\">\n" +
                    "                <assay>A</assay>\n" +
                    "                <assay>B</assay>\n" +
                    "            </assay_group>\n" +
                    "            <assay_group id=\"g4\">\n" +
                    "                <assay>A</assay>\n" +
                    "                <assay technical_replicate_id=\"t1\">B</assay>\n" +
                    "                <assay technical_replicate_id=\"t1\">C</assay>\n" +
                    "                <assay>D</assay>\n" +
                    "                <assay technical_replicate_id=\"t1\">E</assay>\n" +
                    "            </assay_group>\n" +
                    "            <assay_group id=\"g5\">\n" +
                    "                <assay>A</assay>\n" +
                    "                <assay technical_replicate_id=\"t1\">B</assay>\n" +
                    "                <assay technical_replicate_id=\"t1\">C</assay>\n" +
                    "                <assay>D</assay>\n" +
                    "                <assay technical_replicate_id=\"t1\">E</assay>\n" +
                    "                <assay>F</assay>\n" +
                    "                <assay technical_replicate_id=\"t2\">G</assay>\n" +
                    "                <assay technical_replicate_id=\"t2\">H</assay>\n" +
                    "            </assay_group>\n" +
                    "        </assay_groups>\n" +
                    "        <contrasts>\n" +
                    "            <contrast id=\"g1_g2\">\n" +
                    "                <name>'g1' vs 'g2'</name>\n" +
                    "                <reference_assay_group>g1</reference_assay_group>\n" +
                    "                <test_assay_group>g2</test_assay_group>\n" +
                    "            </contrast>\n" +
                    "        </contrasts>\n" +
                    "    </analytics>\n" +
                    "</configuration>\n";

    private ExperimentConfiguration subject;

    @Before
    public void setUp() throws Exception {
        InputStream inputStream = new ByteArrayInputStream(RNASEQ_BASELINE_CONFIGURATION_XML.getBytes(StandardCharsets.UTF_8));

        XMLConfiguration xmlConfiguration = new XMLConfiguration();
        xmlConfiguration.load(inputStream);
        xmlConfiguration.setExpressionEngine(new XPathExpressionEngine());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        inputStream.reset();
        Document document = builder.parse(inputStream);

        subject = new ExperimentConfiguration(xmlConfiguration, document);
    }

    @Test
    public void testGetAssayGroups()  {
        AssayGroups assayGroups = subject.getAssayGroups();
        assertThat(assayGroups.getAssayGroupIds(), hasSize(5));
    }

    @Test
    public void replicatesIsSumOfUniqueTechnicalReplicatesAndUnqualifiedAssays() {
        AssayGroups assayGroups = subject.getAssayGroups();
        assertThat(assayGroups.getAssayGroup("g1").getReplicates(), is(1));
        assertThat(assayGroups.getAssayGroup("g2").getReplicates(), is(1));
        assertThat(assayGroups.getAssayGroup("g3").getReplicates(), is(2));
        assertThat(assayGroups.getAssayGroup("g4").getReplicates(), is(3));
        assertThat(assayGroups.getAssayGroup("g5").getReplicates(), is(5));
    }

    @Test
    public void testGetContrasts()  {
        Set<Contrast> contrasts = subject.getContrasts();
        assertThat(contrasts, hasSize(1));
        Contrast contrast = contrasts.iterator().next();
        assertThat(contrast.getId(), is("g1_g2"));
        assertThat(contrast.getDisplayName(), is("'g1' vs 'g2'"));
        assertThat(contrast.getReferenceAssayGroup(), contains("A"));
        assertThat(contrast.getTestAssayGroup(), contains("A", "B"));
    }

    @Test
    public void testGetExperimentType() {
        assertThat(subject.getExperimentType(), is(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL));
    }
}