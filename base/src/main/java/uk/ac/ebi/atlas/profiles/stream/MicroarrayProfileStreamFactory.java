package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Vector;

@Named
public class MicroarrayProfileStreamFactory
        extends ProfileStreamKryoLayer<Contrast, MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestContext, MicroarrayProfile> {

    @Inject
    public MicroarrayProfileStreamFactory(DataFileHub dataFileHub) {

        super(new Impl(dataFileHub));
    }

    static class Impl extends
            DifferentialProfileStreamFactory<MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestContext, MicroarrayProfile> {

        @Inject
        public Impl(DataFileHub dataFileHub) {
            super(dataFileHub);
        }

        @Override
        protected Function<String[], ProfileFromTsvLine> howToReadLineStream(final MicroarrayExperiment experiment, final Predicate<MicroarrayExpression> expressionFilter) {
            return new Function<String[], ProfileFromTsvLine>() {
                @Nullable
                @Override
                public ProfileFromTsvLine apply(@Nullable String[] strings) {
                    return new DifferentialProfileFromTsvLine(strings, experiment, expressionFilter) {
                        @Nullable
                        @Override
                        protected MicroarrayExpression nextExpression(Integer index, Contrast correspondingColumn, String[] currentLine) {
                            Preconditions.checkState(currentLine.length > index + 2, "Expecting row of the format ... <pvalue_i> <tstat_i> <foldchange_i> ...");
                            String pValueString = currentLine[index];
                            String tStatisticString = currentLine[index + 1];
                            String foldChangeString = currentLine[index + 2];
                            if (notAllDoubles(pValueString, tStatisticString, foldChangeString)) {
                                return null;
                            } else {
                                return new MicroarrayExpression(
                                        parseDouble(pValueString),
                                        parseDouble(foldChangeString),
                                        parseDouble(tStatisticString)
                                );
                            }
                        }

                        @Override
                        protected MicroarrayProfile newProfile(String[] currentLine) {
                            return new MicroarrayProfile(currentLine[0], currentLine[1], currentLine[2]);
                        }
                    };
                }
            };
        }

        @Override
        protected Collection<ObjectInputStream<String[]>> getDataFiles(MicroarrayExperiment experiment, MicroarrayRequestContext options) {
            Vector<ObjectInputStream<String[]>> inputStreams = new Vector<>();
            for (String arrayDesignAccession : options.getArrayDesignAccessions()) {
                ObjectInputStream<String[]> stream = dataFileHub.getMicroarrayExperimentFiles(experiment.getAccession(), arrayDesignAccession).analytics.get();
                inputStreams.add(stream);
            }
            return inputStreams;

        }
    }
}
