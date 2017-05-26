package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Named
public class RnaSeqProfileStreamFactory extends DifferentialProfileStreamFactory<DifferentialExpression,
        DifferentialExperiment, RnaSeqRequestContext, RnaSeqProfile> {

    @Inject
    public RnaSeqProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }


    @Override
    protected Function<String[], ProfileFromTsvLine> howToReadLineStream(final DifferentialExperiment experiment, final Predicate<DifferentialExpression> expressionFilter) {
        return new Function<String[], ProfileFromTsvLine>() {
            @Nullable
            @Override
            public ProfileFromTsvLine apply(@Nullable String[] strings) {
                return new DifferentialProfileFromTsvLine(strings, experiment, expressionFilter) {
                    @Nullable
                    @Override
                    protected DifferentialExpression nextExpression(Integer index, Contrast correspondingColumn, String[] currentLine) {
                        Preconditions.checkState(currentLine.length > index+1, "Expecting row of the format ... <pvalue_i> <foldchange_i> ...");
                        String pValueString = currentLine[index];
                        String foldChangeString = currentLine[index+1];
                        if (notAllDoubles(pValueString, foldChangeString)) {
                            return null;
                        } else {
                            return new DifferentialExpression(parseDouble(pValueString), parseDouble(foldChangeString), correspondingColumn);
                        }
                    }

                    @Override
                    protected RnaSeqProfile newProfile(String[] currentLine) {
                        return new RnaSeqProfile(currentLine[0], currentLine[1]);
                    }
                };
            }
        };
    }

    @Override
    protected Collection<ObjectInputStream<String[]>> getDataFiles(DifferentialExperiment experiment, RnaSeqRequestContext options) {
        return ImmutableList.of(dataFileHub.getRnaSeqDifferentialExperimentFiles(experiment.getAccession()).analytics.get());
    }

}
