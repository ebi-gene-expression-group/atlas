package uk.ac.ebi.atlas.expdesign;

import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.LabeledExtractNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Named;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//ToDo: (N) to be tested

@Named
@Scope("prototype")
public class TwoColourExperimentDesignMageTabParser extends ExperimentDesignMageTabParser<HybridizationNode> {

    @Override
    protected Map<String, HybridizationNode> getAssayNameToNode() {
        Map<String, HybridizationNode> nameToNode = new HashMap<>();
        Collection<HybridizationNode> nodes = getInvestigation().SDRF.getNodes(HybridizationNode.class);
        for (HybridizationNode node : nodes) {
            // Assemble assay accession for each channel separately
            for (int channelNo = 1; channelNo <= 2; channelNo++) {
                nameToNode.put(addLabelToAssayName(node.getNodeName(), getInvestigation().SDRF.getLabelForChannel(channelNo)), node);
            }
        }
        return nameToNode;
    }

    @Override
    protected Collection<SourceNode> getUpstreamSourceNodes(String assayName, HybridizationNode assayNode) {
        Collection<SourceNode> upstreamSources = null;

        for (LabeledExtractNode labeledExtractNode : GraphUtils.findUpstreamNodes(assayNode, LabeledExtractNode.class)) {
            if (getLabelFromAssayName(assayName).equals(labeledExtractNode.label.getAttributeValue())) {
                upstreamSources =
                        GraphUtils.findUpstreamNodes(labeledExtractNode, SourceNode.class);
            }
        }
        return upstreamSources;
    }

    @Override
    protected List<FactorValueAttribute> getFactorAttributes(HybridizationNode node) {
        return node.factorValues;
    }

    //ToDo: this implementation is exactly the same for MicroarrayExperimentDesignMageTabParser, but not needed for RnaSeq
    @Override
    protected void addArrays(ExperimentDesign experimentDesign) {
        Map<String, HybridizationNode> assayNameToNode = getAssayNameToNode();
        for (String assayName : assayNameToNode.keySet()) {

            if (assayNameToNode.get(assayName).arrayDesigns.size() != 1) {
                throw new IllegalStateException("Assays with multiple array designs not supported.");
            }
            experimentDesign.putArrayDesign(assayName, assayNameToNode.get(assayName).arrayDesigns.get(0).getAttributeValue());
        }
    }

    protected String addLabelToAssayName(String assayName, String label) {
        return Joiner.on(".").join(assayName, label);
    }

    protected String getLabelFromAssayName(String assayName) {
        return StringUtils.substringAfter(assayName, ".");
    }

}
