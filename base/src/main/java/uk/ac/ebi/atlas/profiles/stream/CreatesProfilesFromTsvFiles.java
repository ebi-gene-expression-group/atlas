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

public abstract class CreatesProfilesFromTsvFiles<DataColumnDescriptor extends DescribesDataColumns, Expr extends Expression,
        E extends Experiment<DataColumnDescriptor>, StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
        Prof extends Profile<DataColumnDescriptor, Expr, Prof>> extends ProfileStreamFactory<DataColumnDescriptor, Expr, E, StreamOptions, Prof> {

    protected DataFileHub dataFileHub;

    protected CreatesProfilesFromTsvFiles(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    protected abstract Predicate<Expr> filterExpressions(E experiment, StreamOptions options);

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

    protected final Pair<Predicate<String[]>, Predicate<Expr>> filter(E experiment, StreamOptions options, Collection<String>keepGeneIds) {
        return Pair.of(keepLine(keepGeneIds), filterExpressions(experiment, options));
    }

    public ObjectInputStream<Prof> create(E experiment, StreamOptions options, Collection<String> keepGeneIds) {

        Vector<ObjectInputStream<Prof>> outputs =
                getDataFiles(experiment, options)
                        .stream()
                        .map(dataFile -> readNextLineStream(
                                howToReadLine(experiment, filterExpressions(experiment, options)),
                                keepLine(keepGeneIds),
                                dataFile
                        ))
                        .collect(Collectors.toCollection(Vector::new));

        return new SequenceObjectInputStream<>(outputs.elements());

    }

    abstract class GoThroughTsvLineAndPickUpExpressionsByIndex implements Function<String[], Prof> {
        protected final Map<Integer, DataColumnDescriptor> lookUpIndices;
        private final Predicate<Expr> expressionFilter;

        protected GoThroughTsvLineAndPickUpExpressionsByIndex(Map<Integer, DataColumnDescriptor> lookUpIndices, Predicate<Expr> expressionFilter) {
            this.lookUpIndices = lookUpIndices;
            this.expressionFilter = expressionFilter;
        }

        @Nullable
        protected abstract Expr nextExpression(Integer index, DataColumnDescriptor correspondingColumn, String[] currentLine);

        protected abstract Prof newProfile(String[] currentLine);

        @Override
        public Prof apply(String[] currentLine) {
            Prof profile = newProfile(currentLine);
            for (Integer index : lookUpIndices.keySet()) {
                DataColumnDescriptor correspondingColumn = lookUpIndices.get(index);
                Expr nextExpression = nextExpression(index, correspondingColumn, currentLine);
                if (nextExpression != null && nextExpression.getLevel() != 0.0 && expressionFilter.test(nextExpression)) {
                    profile.add(correspondingColumn, nextExpression);
                }
            }
            return profile;
        }
    }

    protected abstract Function<String[], Function<String[], Prof>> howToReadLine(E experiment, Predicate<Expr> expressionFilter);

    protected abstract Collection<ObjectInputStream<String[]>> getDataFiles(E experiment, StreamOptions options);

    private ObjectInputStream<Prof> readNextLineStream(Function<String[], Function<String[], Prof>> howToReadLine,
                                                       final Predicate<String[]> keepLines,
                                                       final ObjectInputStream<String[]> lines) {

        final Function<String[], Prof> readLine = howToReadLine.apply(lines.readNext());
        return new ObjectInputStream<Prof>() {
            @Override
            public Prof readNext() {
                String[] next;
                while ((next = lines.readNext())!= null) {
                    if (keepLines.test(next)) {
                        return readLine.apply(next);
                    }
                }
                return null;
            }

            @Override
            public void close() throws Exception {
                lines.close();
            }
        };

    }

}
