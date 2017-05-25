package uk.ac.ebi.atlas.profiles.tsv;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;

import java.io.IOException;

public class TsvInputStream<DataColumnDescriptor extends DescribesDataColumns,
        Expr extends Expression, Prof extends Profile<DataColumnDescriptor, Expr, Prof>> implements ObjectInputStream<Prof> {

    private final ObjectInputStream<String[]> dataLines;
    private final ExpressionsRowDeserializer<Expr> expressionsRowDeserializer;
    private final Predicate<Expr> expressionFilter;
    private final Experiment<DataColumnDescriptor> experiment;
    private final Integer howManyColumnsOnTheLeftAreForIdentifyingDataRow;
    private final Function<String[], Prof> initProfileFromIdentifiers;

    public TsvInputStream(Pair<String[], ObjectInputStream<String[]>> headerAndBody, ExpressionsRowDeserializerBuilder<Expr>
            expressionsRowDeserializerBuilder, Predicate<Expr> expressionFilter,
                          Experiment<DataColumnDescriptor> experiment,
                          int howManyColumnsOnTheLeftAreForIdentifyingDataRow,
                          Function<String[], Prof> initProfileFromIdentifiers) {
        this.expressionFilter = expressionFilter;
        this.experiment = experiment;
        this.howManyColumnsOnTheLeftAreForIdentifyingDataRow = howManyColumnsOnTheLeftAreForIdentifyingDataRow;
        this.initProfileFromIdentifiers = initProfileFromIdentifiers;
        this.expressionsRowDeserializer = expressionsRowDeserializerBuilder.build(headerAndBody.getLeft());
        this.dataLines = headerAndBody.getRight();
    }

    @Override
    public Prof readNext() {
        Prof geneProfile;

        do {
            String[] values = dataLines.readNext();

            if (values == null) {
                return null;
            }
            geneProfile = profileFromLineOfData(values);

        } while (geneProfile == null);

        return geneProfile;
    }

    protected Prof profileFromLineOfData(String[] values) {
        Prof profile = initProfileFromIdentifiers.apply(getIdentifierColumns(values));
        for (Expr expression : expressionsRowDeserializer.deserializeRow(getDataColumns(values))) {
            if (expressionFilter.apply(expression)) {
                profile.add(experiment.getDataColumnDescriptor(expression.getDataColumnDescriptorId()), expression);
            }
        }
        return profile;
    }

    @Override
    public void close() throws IOException {
        dataLines.close();
    }

    protected String[] getIdentifierColumns(String[] columns) {
        return ArrayUtils.subarray(columns, 0, howManyColumnsOnTheLeftAreForIdentifyingDataRow);
    }

    protected String[] getDataColumns(String[] columns) {
        return ArrayUtils.subarray(columns, howManyColumnsOnTheLeftAreForIdentifyingDataRow, columns.length);
    }

}
