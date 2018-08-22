package uk.ac.ebi.atlas.profiles.stream;

import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.SequenceObjectInputStream;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class CreatesProfilesFromTsvFiles<D extends DescribesDataColumns,
                                                  E extends Expression,
                                                  T extends Experiment<D>,
                                                  O extends ProfileStreamOptions<D>,
                                                  P extends Profile<D, E, P>>
        extends ProfileStreamFactory<D, E, T, O, P> {

    protected DataFileHub dataFileHub;

    protected CreatesProfilesFromTsvFiles(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    protected abstract Predicate<E> filterExpressions(O options);

    /*
    Assumption: all Atlas data files have ids in the first column.
    If this is no longer true or you have other means of filtering unparsed lines,
    push Predicate<String[]> higher into the interface.
     */
    protected final Predicate<String[]> keepLine(Collection<String> keepGeneIds) {
        return keepGeneIds.isEmpty() ?
                x -> true :
                line -> keepGeneIds.contains(line[0]);
    }

    protected final Pair<Predicate<String[]>, Predicate<E>> filter(O options, Collection<String> keepGeneIds) {
        return Pair.of(keepLine(keepGeneIds), filterExpressions(options));
    }

    public ObjectInputStream<P> create(T experiment, O options, Collection<String> keepGeneIds) {

        Vector<ObjectInputStream<P>> outputs =
                getDataFiles(experiment, options)
                        .stream()
                        .map(dataFile -> readNextLineStream(
                                howToReadLine(experiment, filterExpressions(options)),
                                keepLine(keepGeneIds),
                                dataFile))
                        .collect(Collectors.toCollection(Vector::new));

        return new SequenceObjectInputStream<>(outputs.elements());

    }

    abstract class GoThroughTsvLineAndPickUpExpressionsByIndex implements Function<String[], P> {
        protected final Map<Integer, D> lookUpIndices;
        private final Predicate<E> expressionFilter;

        protected GoThroughTsvLineAndPickUpExpressionsByIndex(Map<Integer, D> lookUpIndices,
                                                              Predicate<E> expressionFilter) {
            this.lookUpIndices = lookUpIndices;
            this.expressionFilter = expressionFilter;
        }

        @Nullable
        protected abstract E nextExpression(Integer index, D correspondingColumn, String[] currentLine);

        protected abstract P newProfile(String[] currentLine);

        @Override
        public P apply(String[] currentLine) {
            P profile = newProfile(currentLine);
            for (Integer index : lookUpIndices.keySet()) {
                D correspondingColumn = lookUpIndices.get(index);
                E nextExpression = nextExpression(index, correspondingColumn, currentLine);
                if (nextExpression != null &&
                        nextExpression.getLevel() != 0.0 && expressionFilter.test(nextExpression)) {
                    profile.add(correspondingColumn, nextExpression);
                }
            }
            return profile;
        }
    }

    protected abstract Function<String[], Function<String[], P>> howToReadLine(T experiment,
                                                                               Predicate<E> expressionFilter);

    protected abstract Collection<ObjectInputStream<String[]>> getDataFiles(T experiment, O options);

    private ObjectInputStream<P> readNextLineStream(Function<String[], Function<String[], P>> howToReadLine,
                                                    final Predicate<String[]> keepLines,
                                                    final ObjectInputStream<String[]> lines) {

        final Function<String[], P> readLine = howToReadLine.apply(lines.readNext());
        return new ObjectInputStream<P>() {
            @Override
            public P readNext() {
                String[] next;
                while ((next = lines.readNext()) != null) {
                    if (keepLines.test(next)) {
                        return readLine.apply(next);
                    }
                }
                return null;
            }

            @Override
            public void close() throws IOException {
                lines.close();
            }
        };

    }

}
