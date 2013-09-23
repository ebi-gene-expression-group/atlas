package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.ac.ebi.atlas.model.differential.AssayGroup;
import uk.ac.ebi.atlas.model.differential.Contrast;

import javax.xml.xpath.*;
import java.util.Set;

public class ExperimentConfiguration {

    private XMLConfiguration xmlConfiguration;

    private Document document;

    private XPath xpath = XPathFactory.newInstance().newXPath();

    public ExperimentConfiguration(XMLConfiguration xmlConfiguration, Document document) {
        this.xmlConfiguration = xmlConfiguration;
        this.document = document;
    }

    public Set<Contrast> getContrasts() {

        Set<Contrast> contrasts = Sets.newLinkedHashSet();

        NodeList arrayDesigns = document.getElementsByTagName("array_design");
        for (int i = 0; i < arrayDesigns.getLength(); i++) {
            Node currentArrayDesign = arrayDesigns.item(i);
            String arrayDesignAccession = currentArrayDesign.getFirstChild().getTextContent().trim();
            parseContrastConfiguration("analytics[" + (i + 1) + "]/contrasts/contrast/@id", arrayDesignAccession, contrasts);
        }

        // in case no array designs (case of RNA-seq)
        if (arrayDesigns.getLength() == 0) {
            parseContrastConfiguration("analytics/contrasts/contrast/@id", null, contrasts);
        }

        return contrasts;
    }

    private void parseContrastConfiguration(String query, String arrayDesignAccession, Set<Contrast> contrasts) {
        String[] ids = xmlConfiguration.getStringArray(query);
        for (String id : ids) {
            Contrast contrast = getContrast(id, arrayDesignAccession);
            contrasts.add(contrast);
        }
    }

    Contrast getContrast(String id, String arrayDesignAccession) {
        Configuration configuration = xmlConfiguration.configurationAt("analytics/contrasts/contrast[@id=\'" + id + "\']");
        String name = configuration.getString("name");
        String reference = configuration.getString("reference_assay_group");
        String test = configuration.getString("test_assay_group");
        return new Contrast(id, arrayDesignAccession, getAssayGroup(reference), getAssayGroup(test), name);
    }

    AssayGroup getAssayGroup(String id) {
        try {

            XPathExpression expr = xpath.compile("/configuration/analytics/assay_groups/assay_group[@id='" + id + "']/assay");

            NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            String[] assayAccessions = new String[nl.getLength()];
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                assayAccessions[i] = node.getTextContent();
            }

            return new AssayGroup(id, assayAccessions);

        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Problem parsing configuration file.", e);
        }
    }

    public Set<AssayGroup> getAssayGroups() {
        Set<AssayGroup> assayGroups = Sets.newHashSet();

        String[] assayGoupIds = xmlConfiguration.getStringArray("/configuration/analytics/assay_groups/assay_group/@id");

        for (String assayGoupId : assayGoupIds) {
            assayGroups.add(getAssayGroup(assayGoupId));
        }

        return assayGroups;
    }

}
