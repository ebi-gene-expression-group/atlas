package uk.ac.ebi.atlas.expdesign;

import com.google.common.collect.Lists;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.List;

//ToDo: (N) to be tested

public class RnaSeqExperimentDesignWriter extends ExperimentDesignWriter {

    private RnaSeqExperimentDesignMageTabParser parser;

    public RnaSeqExperimentDesignWriter(RnaSeqExperimentDesignMageTabParser parser) {
        this.parser = parser;
    }

    @Override
    protected String[] composeHeader(ExperimentDesign experimentDesign) {
        List<String> result = Lists.newArrayList("Run");
                for (String characteristic : experimentDesign.getSampleHeaders()) {
                    result.add("Sample Characteristics[" + characteristic + "]");
                }
                for (String factor : experimentDesign.getFactorHeaders()) {
                    result.add("Factor Values[" + factor + "]");
                }
                return result.toArray(new String[result.size()]);
    }

    @Override
    protected ExperimentDesignMageTabParser getMageTabParser() {
        return parser;
    }
}
