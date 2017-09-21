package uk.ac.ebi.atlas.model.experiment;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import uk.ac.ebi.atlas.commons.readers.XmlReader;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.XmlReaderMock;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExperimentConfigurationTest {

    private static Path tmpFilePath;

    private static final String MICROARRAY_CONFIGURATION_XML =
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
                    "                <assay technical_replicate_id=\"t2\">B</assay>\n" +
                    "                <assay technical_replicate_id=\"t2\">C</assay>\n" +
                    "                <assay>D</assay>\n" +
                    "                <assay technical_replicate_id=\"t2\">E</assay>\n" +
                    "            </assay_group>\n" +
                    "            <assay_group id=\"g5\">\n" +
                    "                <assay>A</assay>\n" +
                    "                <assay technical_replicate_id=\"t3\">B</assay>\n" +
                    "                <assay technical_replicate_id=\"t3\">C</assay>\n" +
                    "                <assay>D</assay>\n" +
                    "                <assay technical_replicate_id=\"t3\">E</assay>\n" +
                    "                <assay>F</assay>\n" +
                    "                <assay technical_replicate_id=\"t4\">G</assay>\n" +
                    "                <assay technical_replicate_id=\"t4\">H</assay>\n" +
                    "            </assay_group>\n" +
                    "        </assay_groups>\n" +
                    "        <contrasts>\n" +
                    "            <contrast id=\"g1_g2\" >\n" +
                    "                <name>'g1' vs 'g2'</name>\n" +
                    "                <reference_assay_group>g1</reference_assay_group>\n" +
                    "                <test_assay_group>g2</test_assay_group>\n" +
                    "            </contrast>\n" +
                    "            <contrast id=\"g1_g3\" cttv_primary=\"1\">\n" +
                    "                <name>'g1' vs 'g3'</name>\n" +
                    "                <reference_assay_group>g1</reference_assay_group>\n" +
                    "                <test_assay_group>g3</test_assay_group>\n" +
                    "            </contrast>\n" +
                    "        </contrasts>\n" +
                    "    </analytics>\n" +
                    "</configuration>\n";


    private static final String BASELINE_RNASEQ_CONFIGURATION_XML = "" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "\n" +
            "<configuration experimentType=\"rnaseq_mrna_baseline\" r_data=\"1\">\n" +
            "    <analytics>\n" +
            "        <assay_groups>\n" +
            "            <assay_group id=\"g7\" label=\"hematopoietic stem cell\">\n" +
            "                <assay technical_replicate_id=\"t15\">ERR409784</assay>\n" +
            "                <assay technical_replicate_id=\"t15\">ERR409785</assay>\n" +
            "                <assay technical_replicate_id=\"t15\">ERR409783</assay>\n" +
            "                <assay technical_replicate_id=\"t9\">ERR324395</assay>\n" +
            "                <assay technical_replicate_id=\"t9\">ERR324394</assay>\n" +
            "            </assay_group>\n" +
            "            <assay_group id=\"g6\" label=\"hematopoietic multipotent progenitor cell\">\n" +
            "                <assay>ERR168855</assay>\n" +
            "                <assay>ERR179761</assay>\n" +
            "                <assay technical_replicate_id=\"t12\">ERR324396</assay>\n" +
            "                <assay technical_replicate_id=\"t12\">ERR409751</assay>\n" +
            "            </assay_group>\n" +
            "            <assay_group id=\"g8\" label=\"megakaryocyte-erythroid progenitor cell\">\n" +
            "                <assay>ERR179758</assay>\n" +
            "                <assay>ERR168854</assay>\n" +
            "                <assay>ERR409781</assay>\n" +
            "                <assay>ERR168858</assay>\n" +
            "            </assay_group>\n" +
            "        </assay_groups>\n" +
            "    </analytics>\n" +
            "</configuration>";


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

    public ExperimentConfiguration testConfiguration(String xml) {
        class XmlReaderMock extends XmlReader {

            private Document document;

            public XmlReaderMock(XMLConfiguration xmlConfiguration) {
                super(xmlConfiguration);
            }

            public void setDocument(Document document) {
                this.document = document;
            }

            @Override
            public Document getDocument() {
                return document;
            }
        }
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> fileBuilder =
                new FileBasedConfigurationBuilder<>(XMLConfiguration.class)
                        .configure(params.xml()
                                .setPath(tmpFilePath.toString())
                                .setExpressionEngine(new XPathExpressionEngine()));

        XMLConfiguration xmlConfiguration;
        Document document ;
        try {
            xmlConfiguration = fileBuilder.getConfiguration();
            xmlConfiguration.read(inputStream);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            inputStream.reset();
            document = builder.parse(inputStream);
        } catch (ConfigurationException | IOException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }


        XmlReaderMock xmlReaderMock = new XmlReaderMock(xmlConfiguration);
        xmlReaderMock.setDocument(document);

        return new ExperimentConfiguration(xmlReaderMock);
    }

    @Test
    public void testGetAssayGroups()  {
        List<AssayGroup> assayGroups = testConfiguration(MICROARRAY_CONFIGURATION_XML).getAssayGroups();
        assertThat(assayGroups, hasSize(5));
    }

    @Test
    public void replicatesIsSumOfUniqueTechnicalReplicatesAndUnqualifiedAssays() {
        List<AssayGroup> assayGroups = testConfiguration(MICROARRAY_CONFIGURATION_XML).getAssayGroups();
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
        List<Contrast> contrasts = testConfiguration(MICROARRAY_CONFIGURATION_XML).getContrasts();
        assertThat(contrasts, hasSize(2));
        Contrast contrast = contrasts.get(0);
        assertThat(contrast.getId(), is("g1_g2"));
        assertThat(contrast.getDisplayName(), is("'g1' vs 'g2'"));
        assertThat(contrast.getReferenceAssayGroup(), contains("A"));
        assertThat(contrast.getTestAssayGroup(), contains("A", "B"));
        Contrast otherContrast = contrasts.get(1);
        assertThat(otherContrast.getId(), not(is(contrast.getId())));
    }



    @Test
    public void testGetExperimentType() {
        assertThat(testConfiguration(MICROARRAY_CONFIGURATION_XML).getExperimentType(), is(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL));
        assertThat(testConfiguration(BASELINE_RNASEQ_CONFIGURATION_XML).getExperimentType(), is(ExperimentType.RNASEQ_MRNA_BASELINE));
    }
}