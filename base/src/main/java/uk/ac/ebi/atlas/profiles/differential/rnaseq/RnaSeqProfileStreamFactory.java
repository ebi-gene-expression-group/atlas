package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.profiles.ProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;
import uk.ac.ebi.atlas.resource.DataFileHub;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("prototype")
public class RnaSeqProfileStreamFactory
implements ProfileStreamFactory<DifferentialProfileStreamOptions, RnaSeqProfile, Contrast> {

    private final DataFileHub dataFileHub;

    private ExpressionsRowDeserializerRnaSeqBuilder expressionsRowDeserializerRnaSeqBuilder;

    @Inject
    public RnaSeqProfileStreamFactory(DataFileHub dataFileHub,
                                      ExpressionsRowDeserializerRnaSeqBuilder expressionsRowDeserializerRnaSeqBuilder) {
        this.dataFileHub = dataFileHub;
        this.expressionsRowDeserializerRnaSeqBuilder = expressionsRowDeserializerRnaSeqBuilder;
    }

    public ObjectInputStream<RnaSeqProfile> create(DifferentialProfileStreamOptions options) throws IOException {
        String experimentAccession = options.getExperimentAccession();
        double pValueCutOff = options.getPValueCutOff();
        double foldChangeCutOff = options.getFoldChangeCutOff();
        Regulation regulation = options.getRegulation();

        return create(experimentAccession, pValueCutOff, foldChangeCutOff, regulation);
    }

    public RnaSeqProfilesTsvInputStream create(String experimentAccession, double pValueCutOff, double foldChangeCutOff, Regulation regulation) throws IOException {

        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(pValueCutOff);
        expressionFilter.setFoldChangeCutOff(foldChangeCutOff);
        expressionFilter.setRegulation(regulation);

        RnaSeqProfileReusableBuilder rnaSeqProfileReusableBuilder = new RnaSeqProfileReusableBuilder(expressionFilter);

        return new RnaSeqProfilesTsvInputStream(
                dataFileHub.getDifferentialExperimentFiles(experimentAccession).analytics.getReader(),
                expressionsRowDeserializerRnaSeqBuilder, rnaSeqProfileReusableBuilder);
    }

}
