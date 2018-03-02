package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.BiologicalReplicate;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpressionPerBiologicalReplicate;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpressionPerReplicateProfile;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
This could be generified to deal with other data files that are per biological replicate. We don't want to for now.
 */
@Named
public class BaselineTranscriptProfileStreamFactory extends
        ProfileStreamKryoLayer<
                AssayGroup, BaselineExpressionPerBiologicalReplicate, BaselineExperiment,
                BaselineRequestContext<ExpressionUnit.Absolute.Rna>, BaselineExpressionPerReplicateProfile> {

    @Inject
    public BaselineTranscriptProfileStreamFactory(DataFileHub dataFileHub) {
        super(new Impl(dataFileHub));
    }

    static class Impl extends
            CreatesProfilesFromTsvFiles<
                    AssayGroup, BaselineExpressionPerBiologicalReplicate,
                    BaselineExperiment, BaselineRequestContext<ExpressionUnit.Absolute.Rna>,
                    BaselineExpressionPerReplicateProfile> {
        protected Impl(DataFileHub dataFileHub) {
            super(dataFileHub);
        }

        @Override
        protected Predicate<BaselineExpressionPerBiologicalReplicate> filterExpressions(
                BaselineExperiment experiment, BaselineRequestContext<ExpressionUnit.Absolute.Rna> options) {
            // We don't want to do that for now.
            return x -> true;
        }

        @Override
        protected Function<String[], Function<String[], BaselineExpressionPerReplicateProfile>> howToReadLine(
                BaselineExperiment experiment, Predicate<BaselineExpressionPerBiologicalReplicate> expressionFilter) {

            return header -> {
                ImmutableMap.Builder<Integer, Pair<BiologicalReplicate, AssayGroup>> b = ImmutableMap.builder();
                for (int i = 3; i < header.length; i++) {   // 3: After gene ID, gene name, transcript ID headers
                    String text = header[i];
                    Integer index = i;
                    experiment.getDataColumnDescriptors().stream().flatMap(assayGroup ->
                            assayGroup.biologicalReplicatesForThisDataColumn().stream()
                                    .filter(biologicalReplicate -> biologicalReplicate.getId().equals(text))
                                    .map(biologicalReplicate -> Pair.of(biologicalReplicate, assayGroup))
                    ).findFirst().ifPresent(p -> b.put(index, p));
                }

                final Map<Integer, Pair<BiologicalReplicate, AssayGroup>> whatIsInEachPosition = b.build();

                return line -> {
                    String transcriptId = line[2];
                    Multimap<AssayGroup, Pair<BiologicalReplicate, BaselineExpression>> expressionsPerAssayGroup =
                            LinkedListMultimap.create(experiment.getDataColumnDescriptors().size());

                    for (int i = 3; i < line.length; i++) {
                        BaselineExpression nextExpression = BaselineExpression.create(line[i]);

                        Pair<BiologicalReplicate, AssayGroup> p = whatIsInEachPosition.get(i);

                        if (p != null && nextExpression != null && nextExpression.getLevel() != 0.0) {
                            expressionsPerAssayGroup.put(p.getRight(), Pair.of(p.getLeft(), nextExpression));
                        }
                    }

                    BaselineExpressionPerReplicateProfile result =
                            new BaselineExpressionPerReplicateProfile(transcriptId, transcriptId);

                    for (Map.Entry<AssayGroup, Collection<Pair<BiologicalReplicate, BaselineExpression>>> e :
                            expressionsPerAssayGroup.asMap().entrySet()) {
                        //If needed add the call to expression filter here.
                        result.add(
                                e.getKey(),
                                new BaselineExpressionPerBiologicalReplicate(
                                        e.getValue().stream().collect(Collectors.toMap(Pair::getLeft, Pair::getRight)))
                        );
                    }

                    return result;
                };
            };

        }

        @Override
        protected Collection<ObjectInputStream<String[]>> getDataFiles(
                BaselineExperiment experiment, BaselineRequestContext<ExpressionUnit.Absolute.Rna> options) {
            return dataFileHub.getRnaSeqBaselineExperimentFiles(experiment.getAccession()).transcriptsTpms.maybeGet()
                    .map(Collections::singletonList).orElse(Collections.emptyList());
        }

    }
}