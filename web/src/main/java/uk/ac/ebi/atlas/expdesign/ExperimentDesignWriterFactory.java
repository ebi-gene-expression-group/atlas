package uk.ac.ebi.atlas.expdesign;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExperimentDesignWriterFactory {

    private MicroarrayExperimentDesignMageTabParser microarrayMageTabParser;
    private RnaSeqExperimentDesignMageTabParser rnaSeqMageTabParser;
    private TwoColourExperimentDesignMageTabParser twoColourMageTabParser;

    @Inject
    public ExperimentDesignWriterFactory(MicroarrayExperimentDesignMageTabParser microarrayMageTabParser, RnaSeqExperimentDesignMageTabParser rnaSeqMageTabParser, TwoColourExperimentDesignMageTabParser twoColourMageTabParser) {
        this.microarrayMageTabParser = microarrayMageTabParser;
        this.rnaSeqMageTabParser = rnaSeqMageTabParser;
        this.twoColourMageTabParser = twoColourMageTabParser;
    }

    public ExperimentDesignWriter getMicroarrayWriter() {
        return new MicroarrayExperimentDesignWriter(microarrayMageTabParser);
    }

    public ExperimentDesignWriter getRnaseqWriter() {
        return new RnaSeqExperimentDesignWriter(rnaSeqMageTabParser);
    }

    public ExperimentDesignWriter getTwoColourWriter() {
        return new MicroarrayExperimentDesignWriter(twoColourMageTabParser);
    }


}
