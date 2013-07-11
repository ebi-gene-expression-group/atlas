package uk.ac.ebi.atlas.expdesign;

import com.google.common.collect.Lists;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Inject;
import java.util.List;

public class RnaSeqExperimentDesignWriter extends ExperimentDesignWriter {

    private RnaSeqMageTabParser parser;

    @Inject
    public RnaSeqExperimentDesignWriter(RnaSeqMageTabParser parser) {
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
    protected MageTabParser getMageTabParser() {
        return null;
    }
}
