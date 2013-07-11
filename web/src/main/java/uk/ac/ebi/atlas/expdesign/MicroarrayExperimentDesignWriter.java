package uk.ac.ebi.atlas.expdesign;

import com.google.common.collect.Lists;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.List;


public class MicroarrayExperimentDesignWriter extends ExperimentDesignWriter {

    private MageTabParser<HybridizationNode> parser;


    public MicroarrayExperimentDesignWriter(MageTabParser<HybridizationNode> parser) {
        this.parser = parser;
    }

    @Override
    protected String[] composeHeader(ExperimentDesign experimentDesign) {
        List<String> result = Lists.newArrayList("Assay", "Array");
        for (String characteristic : experimentDesign.getSampleHeaders()) {
            result.add("Sample Characteristics[" + characteristic + "]");
        }
        for (String factor : experimentDesign.getFactorHeaders()) {
            result.add("Factor Values[" + factor + "]");
        }
        return result.toArray(new String[result.size()]);
    }

    @Override
    protected MageTabParser getMageTabParser() {
        return parser;
    }
}
