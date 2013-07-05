package uk.ac.ebi.atlas.model.differential;

import com.google.common.collect.Sets;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.util.Set;

public class DifferentialExperimentConfiguration {

    private XMLConfiguration xmlConfiguration;

    private Document document;

    public DifferentialExperimentConfiguration(XMLConfiguration xmlConfiguration, Document document) {
        this.xmlConfiguration = xmlConfiguration;
        this.document = document;
    }

    public Set<Contrast> getContrasts() {
        Set<Contrast> contrasts = Sets.newLinkedHashSet();
        String[] ids = xmlConfiguration.getStringArray("analytics/contrasts/contrast/@id");
        for (String id : ids) {
            Contrast contrast = getContrast(id);
            contrasts.add(contrast);
        }
        return contrasts;
    }

    Contrast getContrast(String id) {
        Configuration configuration = xmlConfiguration.configurationAt("analytics/contrasts/contrast[@id=\'" + id + "\']");
        String name = configuration.getString("name");
        String reference = configuration.getString("reference_assay_group");
        String test = configuration.getString("test_assay_group");
        return new Contrast(id, getAssayGroup(reference), getAssayGroup(test), name);
    }

    AssayGroup getAssayGroup(String id) {
        try {

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/configuration/analytics/assay_groups/assay_group[@id='" + id + "']/assay");

            NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            String[] assayAccessions = new String[nl.getLength()];
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                assayAccessions[i] = node.getTextContent();
            }

            return new AssayGroup(assayAccessions);

        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Problem parsing configuration file.", e);
        }
    }


}
