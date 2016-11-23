package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.profiles.ProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;
import uk.ac.ebi.atlas.resource.DataFileHub;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class RnaSeqProfileStreamFactory
implements ProfileStreamFactory<DifferentialExperiment, DifferentialProfileStreamOptions, RnaSeqProfile, Contrast> {

    private final DataFileHub dataFileHub;

    @Inject
    public RnaSeqProfileStreamFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public ObjectInputStream<RnaSeqProfile> create(DifferentialExperiment experiment, DifferentialProfileStreamOptions
            options)
            throws IOException {
        return create(experiment,
                options.getPValueCutOff(),
                options.getFoldChangeCutOff(),
                options.getRegulation());
    }

    public RnaSeqProfilesTsvInputStream create(DifferentialExperiment experiment, double pValueCutOff, double foldChangeCutOff, Regulation regulation) throws IOException {

        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(pValueCutOff);
        expressionFilter.setFoldChangeCutOff(foldChangeCutOff);
        expressionFilter.setRegulation(regulation);

        RnaSeqProfileReusableBuilder rnaSeqProfileReusableBuilder = new RnaSeqProfileReusableBuilder(expressionFilter);

        return new RnaSeqProfilesTsvInputStream(
                dataFileHub.getDifferentialExperimentFiles(experiment.getAccession()).analytics.getReader(),
                new ExpressionsRowDeserializerRnaSeqBuilder(experiment),
                rnaSeqProfileReusableBuilder);
    }

}
