package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
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

public abstract class CreatesProfilesFromTsvFiles<DataColumnDescriptor extends DescribesDataColumns, Expr extends Expression,
        E extends Experiment<DataColumnDescriptor>, StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
        Prof extends Profile<DataColumnDescriptor, Expr, Prof>> extends ProfileStreamFactory<DataColumnDescriptor, Expr, E, StreamOptions, Prof> {

    protected final DataFileHub dataFileHub;

    protected CreatesProfilesFromTsvFiles(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    protected abstract Predicate<Expr> filterExpressions(E experiment, StreamOptions options);

    public final ObjectInputStream<Prof> create(E experiment, StreamOptions options){
        return create(experiment, options, filterExpressions(experiment, options));
    }

    ObjectInputStream<Prof> create(E experiment, StreamOptions options, Predicate<Expr> filterExpressions){

        Vector<ObjectInputStream<Prof>> outputs = new Vector<>();

        for(ObjectInputStream<String[]> dataFile : getDataFiles(experiment, options)){
            outputs.add(readNextLineStream(howToReadLineStream(experiment, filterExpressions), dataFile));
        }

        return new SequenceObjectInputStream<>(outputs.elements());

    }

    abstract class ProfileFromTsvLine implements Function<String[], Prof>{

        protected final Map<Integer,DataColumnDescriptor> lookUpIndices;
        private final Predicate<Expr> expressionFilter;

        protected ProfileFromTsvLine(Map<Integer,DataColumnDescriptor> lookUpIndices, Predicate<Expr> expressionFilter){
            this.lookUpIndices = lookUpIndices;
            this.expressionFilter = expressionFilter;
        }

        @Nullable
        protected abstract Expr nextExpression(Integer index,DataColumnDescriptor correspondingColumn, String[] currentLine);

        protected abstract Prof newProfile(String[] currentLine);

        @Override
        public Prof apply(String[] currentLine) {
            Prof profile = newProfile(currentLine);
            for(Integer index: lookUpIndices.keySet()){
                DataColumnDescriptor correspondingColumn = lookUpIndices.get(index);
                Expr nextExpression = nextExpression(index,correspondingColumn, currentLine);
                if(nextExpression!= null && expressionFilter.apply(nextExpression)){
                    profile.add(correspondingColumn, nextExpression);
                }
            }
            return profile;
        }
    }

    protected abstract Function<String[], ProfileFromTsvLine> howToReadLineStream(E experiment, Predicate<Expr> expressionFilter);

    protected abstract Collection<ObjectInputStream<String[]>> getDataFiles(E experiment, StreamOptions options);

    protected ObjectInputStream<Prof> readNextLineStream(Function<String[], ProfileFromTsvLine> howToReadLineStream, final ObjectInputStream<String[]> lines){
        final ProfileFromTsvLine readLine = howToReadLineStream.apply(lines.readNext());
        return new ObjectInputStream<Prof>() {
            @Override
            public Prof readNext() {
                String[] next = lines.readNext();
                return next == null ? null : readLine.apply(next);
            }

            @Override
            public void close() throws IOException {
                lines.close();
            }
        };
    }

}
