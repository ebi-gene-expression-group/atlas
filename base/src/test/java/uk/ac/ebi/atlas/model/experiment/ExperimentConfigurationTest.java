package uk.ac.ebi.atlas.model.experiment;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.XmlReaderMock;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExperimentConfigurationTest {

    private static Path tmpFilePath;

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

    @BeforeClass
    public static void setUpClass() throws Exception {
        // In Commons Configuration 2, XMLConfiguration needs at least a well-formed XML file:
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
        InputStream inputStream = new ByteArrayInputStream(RNASEQ_BASELINE_CONFIGURATION_XML.getBytes(StandardCharsets.UTF_8));

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> fileBuilder =
                new FileBasedConfigurationBuilder<>(XMLConfiguration.class)
                        .configure(params.xml()
                                .setPath(tmpFilePath.toString())
                                .setExpressionEngine(new XPathExpressionEngine()));

        XMLConfiguration xmlConfiguration = fileBuilder.getConfiguration();
        xmlConfiguration.read(inputStream);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        inputStream.reset();
        Document document = builder.parse(inputStream);

        XmlReaderMock xmlReaderMock = new XmlReaderMock(xmlConfiguration);
        xmlReaderMock.setDocument(document);

        subject = new ExperimentConfiguration(xmlReaderMock);
    }

    @Test
    public void testGetAssayGroups()  {
        List<AssayGroup> assayGroups = subject.getAssayGroups();
        assertThat(assayGroups, hasSize(5));
    }

    @Test
    public void replicatesIsSumOfUniqueTechnicalReplicatesAndUnqualifiedAssays() {
        List<AssayGroup> assayGroups = subject.getAssayGroups();
        assertThat(assayGroups.get(0).getId(), is("g1"));
        assertThat(assayGroups.get(1).getId(), is("g2"));
        assertThat(assayGroups.get(2).getId(), is("g3"));
        assertThat(assayGroups.get(3).getId(), is("g4"));
        assertThat(assayGroups.get(4).getId(), is("g5"));

        assertThat(assayGroups.get(0).getReplicates(), is(1));
        assertThat(assayGroups.get(1).getReplicates(), is(1));
        assertThat(assayGroups.get(2).getReplicates(), is(2));
        assertThat(assayGroups.get(3).getReplicates(), is(3));
        assertThat(assayGroups.get(4).getReplicates(), is(5));
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