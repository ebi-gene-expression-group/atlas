package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Preconditions;
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
import java.util.function.Function;
import java.util.function.Predicate;

@Named
public class RnaSeqProfileStreamFactory
        extends ProfileStreamFactory<Contrast,
                                     DifferentialExpression,
                                     DifferentialExperiment,
                                     RnaSeqRequestContext,
                                     RnaSeqProfile> {

    private final CreatesProfilesFromTsvFiles<Contrast,
            DifferentialExpression,
            DifferentialExperiment,
            RnaSeqRequestContext,
            RnaSeqProfile> profileStreamFactory;


    @Inject
    public RnaSeqProfileStreamFactory(DataFileHub dataFileHub) {
        profileStreamFactory = new Impl(dataFileHub);
    }

    @Override
    public ObjectInputStream<RnaSeqProfile> create(DifferentialExperiment experiment, RnaSeqRequestContext options, Collection<String> keepGeneIds) {
        return profileStreamFactory.create(experiment, options, keepGeneIds);
    }

    static class Impl extends DifferentialProfileStreamFactory<DifferentialExpression,
            DifferentialExperiment, RnaSeqRequestContext, RnaSeqProfile> {


        protected Impl(DataFileHub dataFileHub) {
            super(dataFileHub);
        }

        @Override
        protected Function<String[], Function<String[], RnaSeqProfile>> howToReadLine(final DifferentialExperiment experiment, final Predicate<DifferentialExpression> expressionFilter) {
            return strings -> new DifferentialGoThroughTsvLineAndPickUpExpressionsByIndex(strings, experiment, expressionFilter) {
                @Nullable
                @Override
                protected DifferentialExpression nextExpression(Integer index, Contrast correspondingColumn, String[] currentLine) {
                    Preconditions.checkState(currentLine.length > index + 1, "Expecting row of the format ... <pvalue_i> <foldchange_i> ...");
                    String pValueString = currentLine[index];
                    String foldChangeString = currentLine[index + 1];
                    if (notAllDoubles(pValueString, foldChangeString)) {
                        return null;
                    } else {
                        return new DifferentialExpression(parseDouble(pValueString), parseDouble(foldChangeString));
                    }
                }

                @Override
                protected RnaSeqProfile newProfile(String[] currentLine) {
                    return new RnaSeqProfile(currentLine[0], currentLine[1]);
                }
            };
        }

        @Override
        protected Collection<ObjectInputStream<String[]>> getDataFiles(DifferentialExperiment experiment, RnaSeqRequestContext options) {
            return ImmutableList.of(dataFileHub.getRnaSeqDifferentialExperimentFiles(experiment.getAccession()).analytics.get());
        }
    }

}
