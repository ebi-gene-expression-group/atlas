package uk.ac.ebi.atlas.profiles.differential.microarray;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.SequenceObjectInputStream;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.ProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Vector;

@Named
@Scope("prototype")
public class MicroarrayProfileStreamFactory
implements ProfileStreamFactory<MicroarrayExperiment, MicroarrayProfileStreamOptions, MicroarrayProfile, Contrast> {

    private final DataFileHub dataFileHub;

    @Inject
    public MicroarrayProfileStreamFactory(
            DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    @Override
    public ObjectInputStream<MicroarrayProfile> create(MicroarrayExperiment experiment, MicroarrayProfileStreamOptions
            options)
    throws IOException {
        return create(experiment,
                options.getPValueCutOff(),
                options.getFoldChangeCutOff(),
                options.getRegulation(),
                options.getArrayDesignAccessions());
    }


    public MicroarrayProfilesTsvInputStream create(MicroarrayExperiment experiment, MicroarrayProfileStreamOptions
            options, String arrayDesign)
    throws IOException {
        return create(experiment,
                options.getPValueCutOff(),
                options.getFoldChangeCutOff(),
                options.getRegulation(),
                arrayDesign);
    }

    public ObjectInputStream<MicroarrayProfile> create(MicroarrayExperiment experiment,
                                                       double pValueCutOff,
                                                       double foldChangeCutOff,
                                                       Regulation regulation,
                                                       Iterable<String> arrayDesignAccessions) throws IOException {
        Vector<ObjectInputStream<MicroarrayProfile>> inputStreams = new Vector<>();
        for (String arrayDesignAccession : arrayDesignAccessions) {
            ObjectInputStream<MicroarrayProfile> stream =
                    create(experiment, pValueCutOff, foldChangeCutOff, regulation, arrayDesignAccession);
            inputStreams.add(stream);
        }

        return new SequenceObjectInputStream<>(inputStreams.elements());
    }

    public MicroarrayProfilesTsvInputStream create(MicroarrayExperiment experiment,
                                                   double pValueCutOff,
                                                   double foldChangeCutOff,
                                                   Regulation regulation,
                                                   String arrayDesignAccession) throws IOException {
        MicroarrayProfileReusableBuilder profileBuilder = createProfileBuilder(pValueCutOff, foldChangeCutOff, regulation);

        return new MicroarrayProfilesTsvInputStream(
                dataFileHub.getMicroarrayExperimentFiles(experiment.getAccession(), arrayDesignAccession).analytics.getReader(),
                new ExpressionsRowDeserializerMicroarrayBuilder(experiment),
                profileBuilder);
    }

    private MicroarrayProfileReusableBuilder createProfileBuilder(double pValueCutOff, double foldChangeCutOff, Regulation regulation) {
        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(pValueCutOff);
        expressionFilter.setFoldChangeCutOff(foldChangeCutOff);
        expressionFilter.setRegulation(regulation);

        return new MicroarrayProfileReusableBuilder(expressionFilter);
    }

}
