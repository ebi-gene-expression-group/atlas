package uk.ac.ebi.atlas.expdesign;

import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RnaSeqMageTabParser extends MageTabParser<ScanNode> {

    private static final String ENA_RUN = "ENA_RUN";

    @Override
    protected Map<String, ScanNode> getAssayNameToNode() {

        Map<String, ScanNode> nameToNode = new HashMap<>();
        for (ScanNode scanNode : getInvestigation().SDRF.getNodes(ScanNode.class)) {
            nameToNode.put(scanNode.comments.get(ENA_RUN), scanNode);
        }

        return nameToNode;
    }

    @Override
    protected Collection<SourceNode> getUpstreamSourceNodes(String assayName, ScanNode assayNode) {
        return GraphUtils.findUpstreamNodes(assayNode, SourceNode.class);
    }

    @Override
    protected List<FactorValueAttribute> getFactorAttributes(ScanNode node) {
        Collection<AssayNode> assayNodes = GraphUtils.findUpstreamNodes(node, AssayNode.class);
        if (assayNodes.size() != 1) {
            throw new IllegalStateException("No assay corresponds to ENA run " + node.comments.get(ENA_RUN));
        }

        AssayNode assayNode = assayNodes.iterator().next();

        return assayNode.factorValues;
    }

    @Override
    protected void addArrays(ExperimentDesign experimentDesign) {

    }
}
