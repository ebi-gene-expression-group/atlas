package uk.ac.ebi.atlas.expdesign;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Named;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@Scope("prototype")
public class MicroarrayExperimentDesignMageTabParser extends ExperimentDesignMageTabParser<HybridizationNode> {


    @Override
    protected Map<String, HybridizationNode> getAssayNameToNode() {
        Map<String, HybridizationNode> assayNameToNode = new HashMap<>();

        Collection<HybridizationNode> nodes = getInvestigation().SDRF.getNodes(HybridizationNode.class);
        for (HybridizationNode node : nodes) {
            assayNameToNode.put(node.getNodeName(), node);
        }
        return assayNameToNode;
    }


    @Override
    protected Collection<SourceNode> getUpstreamSourceNodes(String assayName, HybridizationNode assayNode) {
        return GraphUtils.findUpstreamNodes(assayNode, SourceNode.class);
    }

    @Override
    protected List<FactorValueAttribute> getFactorAttributes(HybridizationNode node) {
        return node.factorValues;
    }

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

}
