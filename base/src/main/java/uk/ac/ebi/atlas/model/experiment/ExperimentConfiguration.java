package uk.ac.ebi.atlas.model.experiment;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.ac.ebi.atlas.commons.readers.XmlReader;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class ExperimentConfiguration {

    private static final String EXPERIMENT_TYPE = "experimentType";
    private static final String RDATA = "r_data";

    private XmlReader xmlReader;
    private XPath xpath = XPathFactory.newInstance().newXPath();

    public ExperimentConfiguration(XmlReader xmlReader) {
        this.xmlReader = xmlReader;
    }

    public Set<Contrast> getContrasts() {

        Set<Contrast> contrasts = Sets.newLinkedHashSet();

        NodeList arrayDesigns = xmlReader.getDocument().getElementsByTagName("array_design");
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
        String[] ids = xmlReader.getStringArray(query);
        for (String id : ids) {
            Contrast contrast = getContrast(id, arrayDesignAccession);
            contrasts.add(contrast);
        }
    }

    private Contrast getContrast(String id, String arrayDesignAccession) {
        Configuration configuration = xmlReader.configurationAt("analytics/contrasts/contrast[@id=\'" + id + "\']");
        String name = configuration.getString("name");
        String reference = configuration.getString("reference_assay_group");
        String test = configuration.getString("test_assay_group");
        return new Contrast(id, arrayDesignAccession, getAssayGroup(reference), getAssayGroup(test), name);
    }

    private AssayGroup getAssayGroup(String id) {
        try {
            XPathExpression expr = xpath.compile("/configuration/analytics/assay_groups/assay_group[@id='" + id + "']/assay");

            NodeList nl = (NodeList) expr.evaluate(xmlReader.getDocument(), XPathConstants.NODESET);
            String[] assayAccessions = new String[nl.getLength()];

            ImmutableSet.Builder<String> technicalReplicatesBuilder = ImmutableSet.builder();
            int soloAssayCount = 0;
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                String technicalReplicateId = getAttribute(node, "technical_replicate_id");
                if (technicalReplicateId != null) {
                    technicalReplicatesBuilder.add(technicalReplicateId);
                } else {
                    soloAssayCount++;
                }
                assayAccessions[i] = node.getTextContent();
            }

            int technicalReplicates = technicalReplicatesBuilder.build().size();
            int biologicalReplicates = technicalReplicates + soloAssayCount;
            return new AssayGroup(id, biologicalReplicates, assayAccessions);

        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Problem parsing configuration file.", e);
        }
    }

    private String getAttribute(Node node, String name) {
        Node attribute = node.getAttributes().getNamedItem(name);
        return (attribute == null) ? null : attribute.getNodeValue();
    }

    public Set<String> getAssayAccessions() {
        try {

            XPathExpression expr = xpath.compile("/configuration/analytics/assay_groups/assay_group/assay");

            Set<String> assayAccessions = Sets.newHashSet();

            NodeList nl = (NodeList) expr.evaluate(xmlReader.getDocument(), XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                assayAccessions.add(node.getTextContent());
            }

            return assayAccessions;

        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Problem parsing configuration file.", e);
        }
    }

    public AssayGroups getAssayGroups() {
        List<AssayGroup> assayGroups = Lists.newArrayList();

        for (String assayGroupId : xmlReader.getStringArray("/analytics/assay_groups/assay_group/@id")) {
            assayGroups.add(getAssayGroup(assayGroupId));
        }

        return new AssayGroups(assayGroups);
    }

    public ExperimentType getExperimentType() {
        Element configuration = xmlReader.getDocument().getDocumentElement();
        String type = configuration.getAttribute(EXPERIMENT_TYPE);

        if (StringUtils.isEmpty(type)) {
            throw new IllegalStateException(String.format("Missing %s attribute on root element", EXPERIMENT_TYPE));
        }

        ExperimentType experimentType = ExperimentType.get(type);
        if (experimentType == null) {
            throw new IllegalStateException(String.format("Unknown %s attribute: \"%s\". Must be one of: [%s]", EXPERIMENT_TYPE, type, Joiner.on(", ").join(EnumSet.allOf(ExperimentType.class))));
        }

        return experimentType;
    }

    public boolean hasRData() {
        return "1".equals(xmlReader.getDocument().getDocumentElement().getAttribute(RDATA));
    }
}
