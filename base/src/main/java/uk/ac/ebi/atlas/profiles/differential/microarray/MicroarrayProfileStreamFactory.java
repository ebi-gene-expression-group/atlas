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
implements ProfileStreamFactory<MicroarrayProfileStreamOptions, MicroarrayProfile, Contrast> {

    private final ExperimentTrader experimentTrader;
    private final DataFileHub dataFileHub;

    @Inject
    public MicroarrayProfileStreamFactory(
            ExperimentTrader experimentTrader,
            DataFileHub dataFileHub) {
        this.experimentTrader = experimentTrader;
        this.dataFileHub = dataFileHub;
    }


    public ObjectInputStream<MicroarrayProfile> create(MicroarrayProfileStreamOptions options)
    throws IOException {
        String experimentAccession = options.getExperimentAccession();
        double pValueCutOff = options.getPValueCutOff();
        double foldChangeCutOff = options.getFoldChangeCutOff();
        Regulation regulation = options.getRegulation();
        Iterable<String> arrayDesignAccessions = options.getArrayDesignAccessions();

        return create(experimentAccession, pValueCutOff, foldChangeCutOff, regulation, arrayDesignAccessions);
    }

    public MicroarrayProfilesTsvInputStream create(MicroarrayProfileStreamOptions options, String arrayDesign)
    throws IOException {
        String experimentAccession = options.getExperimentAccession();
        double pValueCutOff = options.getPValueCutOff();
        double foldChangeCutOff = options.getFoldChangeCutOff();
        Regulation regulation = options.getRegulation();

        return create(experimentAccession, pValueCutOff, foldChangeCutOff, regulation, arrayDesign);
    }

    public ObjectInputStream<MicroarrayProfile> create(String experimentAccession,
                                                       double pValueCutOff,
                                                       double foldChangeCutOff,
                                                       Regulation regulation,
                                                       Iterable<String> arrayDesignAccessions) throws IOException {
        Vector<ObjectInputStream<MicroarrayProfile>> inputStreams = new Vector<>();
        for (String arrayDesignAccession : arrayDesignAccessions) {
            ObjectInputStream<MicroarrayProfile> stream =
                    create(experimentAccession, pValueCutOff, foldChangeCutOff, regulation, arrayDesignAccession);
            inputStreams.add(stream);
        }

        return new SequenceObjectInputStream<>(inputStreams.elements());
    }

    public MicroarrayProfilesTsvInputStream create(String experimentAccession,
                                                   double pValueCutOff,
                                                   double foldChangeCutOff,
                                                   Regulation regulation,
                                                   String arrayDesignAccession) throws IOException {
        MicroarrayProfileReusableBuilder profileBuilder = createProfileBuilder(pValueCutOff, foldChangeCutOff, regulation);

        return new MicroarrayProfilesTsvInputStream(
                dataFileHub.getMicroarrayExperimentFiles(experimentAccession, arrayDesignAccession).analytics.getReader(),
                new ExpressionsRowDeserializerMicroarrayBuilder((MicroarrayExperiment) experimentTrader
                        .getPublicExperiment
                        (experimentAccession)),
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
