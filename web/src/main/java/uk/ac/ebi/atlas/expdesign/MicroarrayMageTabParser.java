package uk.ac.ebi.atlas.expdesign;

import com.google.common.collect.Sets;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.Set;

public class MicroarrayMageTabParser extends MageTabParser {

    @Override
    protected Set<String> extractAssays() {
        Set<String> assays = Sets.newHashSet();

        for (HybridizationNode hybridizationNode : getInvestigation().SDRF.getNodes(HybridizationNode.class)) {
            assays.add(hybridizationNode.getNodeName());
        }

        return assays;
    }

    @Override
    protected HybridizationNode getNode(String assay) {
        HybridizationNode node = getInvestigation().SDRF.getNode(assay, HybridizationNode.class);
        if (node == null) {
            new IllegalStateException("Assay has not been found in SDRF: " + assay);
        }
        return node;
    }

    @Override
    protected void addArrays(ExperimentDesign experimentDesign) {
        for (HybridizationNode hybridizationNode : getInvestigation().SDRF.getNodes(HybridizationNode.class)) {

            if (hybridizationNode.arrayDesigns.size() != 1) {
                throw new IllegalStateException("Assays with multiple array designs not supported.");
            }
            experimentDesign.putArrayDesign(hybridizationNode.getNodeName(), hybridizationNode.arrayDesigns.get(0).getAttributeValue());
        }
    }
}
