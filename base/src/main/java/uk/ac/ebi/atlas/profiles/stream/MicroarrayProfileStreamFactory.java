package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Preconditions;
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
import java.util.function.Function;
import java.util.function.Predicate;

@Named
public class MicroarrayProfileStreamFactory
        extends ProfileStreamFactory<Contrast,
                                       MicroarrayExpression,
                                       MicroarrayExperiment,
                                       MicroarrayRequestContext,
                                       MicroarrayProfile> {

    private final CreatesProfilesFromTsvFiles<Contrast,
            MicroarrayExpression,
            MicroarrayExperiment,
            MicroarrayRequestContext,
            MicroarrayProfile> profileStreamFactory;

    @Inject
    public MicroarrayProfileStreamFactory(DataFileHub dataFileHub) {
        profileStreamFactory = new Impl(dataFileHub);
    }

    @Override
    public ObjectInputStream<MicroarrayProfile> create(MicroarrayExperiment experiment,
                                                       MicroarrayRequestContext options,
                                                       Collection<String> keepGeneIds) {
        return profileStreamFactory.create(experiment, options, keepGeneIds);
    }

    static class Impl
                 extends DifferentialProfileStreamFactory<
                            MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestContext, MicroarrayProfile> {

        @Inject
        Impl(DataFileHub dataFileHub) {
            super(dataFileHub);
        }

        @Override
        protected Function<String[], Function<String[], MicroarrayProfile>>
                  howToReadLine(final MicroarrayExperiment experiment,
                                final Predicate<MicroarrayExpression> expressionFilter) {
            return strings ->
                    new DifferentialGoThroughTsvLineAndPickUpExpressionsByIndex(
                            strings, experiment, expressionFilter) {
                @Nullable
                @Override
                protected MicroarrayExpression nextExpression(Integer index,
                                                              Contrast correspondingColumn,
                                                              String[] currentLine) {
                    Preconditions.checkState(
                            currentLine.length > index + 2,
                            "Expecting row of the format ... <pvalue_i> <tstat_i> <foldchange_i> ...");
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

        @Override
        protected Collection<ObjectInputStream<String[]>> getDataFiles(MicroarrayExperiment experiment,
                                                                       MicroarrayRequestContext options) {
            Vector<ObjectInputStream<String[]>> inputStreams = new Vector<>();
            for (String arrayDesignAccession : options.getArrayDesignAccessions()) {
                ObjectInputStream<String[]> stream =
                        dataFileHub.getMicroarrayExperimentFiles(
                                experiment.getAccession(), arrayDesignAccession).analytics.get();
                inputStreams.add(stream);
            }
            return inputStreams;

        }
    }
}
