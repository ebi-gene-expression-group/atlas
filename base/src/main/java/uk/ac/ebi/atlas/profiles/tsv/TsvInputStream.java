package uk.ac.ebi.atlas.profiles.tsv;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;

import java.io.IOException;
import java.io.Reader;

public class TsvInputStream<DataColumnDescriptor extends DescribesDataColumns,
        Expr extends Expression, Prof extends Profile<DataColumnDescriptor, Expr>> implements ObjectInputStream<Prof> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TsvInputStream.class);

    private final CSVReader csvReader;
    private final ExpressionsRowDeserializer<Expr> expressionsRowDeserializer;
    private final Predicate<Expr> expressionFilter;
    private final Experiment<DataColumnDescriptor> experiment;
    private final Integer howManyColumnsOnTheLeftAreForIdentifyingDataRow;
    private final Function<String[], Prof> initProfileFromIdentifiers;

    public TsvInputStream(Reader reader, ExpressionsRowDeserializerBuilder<Expr>
            expressionsRowDeserializerBuilder, Predicate<Expr> expressionFilter,
                             Experiment<DataColumnDescriptor> experiment,
                             int howManyColumnsOnTheLeftAreForIdentifyingDataRow,
                             Function<String[], Prof> initProfileFromIdentifiers) {
        this.expressionFilter = expressionFilter;
        this.experiment = experiment;
        this.howManyColumnsOnTheLeftAreForIdentifyingDataRow = howManyColumnsOnTheLeftAreForIdentifyingDataRow;
        this.initProfileFromIdentifiers = initProfileFromIdentifiers;
        this.csvReader = new CSVReader(reader, '\t');
        this.expressionsRowDeserializer = expressionsRowDeserializerBuilder.build(readHeaders());
    }

    private String[] readHeaders(){
        return getDataColumns(readCsvLine());
    }

    @Override
    public Prof readNext() {
        Prof geneProfile;

        do {
            String[] values = readCsvLine();

            if (values == null) {
                return null;
            }
            geneProfile = profileFromLineOfData(values);

        } while (geneProfile == null);

        return geneProfile;
    }

    private String[] readCsvLine() {
        try {
            return csvReader.readNext();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Exception thrown while reading next csv line.", e);
        }
    }

    protected Prof profileFromLineOfData(String[] values){
        Prof profile = initProfileFromIdentifiers.apply(getIdentifierColumns(values));
        for(Expr expression: expressionsRowDeserializer.deserializeRow(getDataColumns(values))){
            if(expressionFilter.apply(expression)){
                profile.add(experiment.getDataColumnDescriptor(expression.getDataColumnDescriptorId()), expression);
            }
        }
        return profile;
    }


    @Override
    public void close() throws IOException {
        csvReader.close();
    }

    protected String[] getIdentifierColumns(String[] columns){
        return ArrayUtils.subarray(columns, 0, howManyColumnsOnTheLeftAreForIdentifyingDataRow);
    }

    protected String[] getDataColumns(String[] columns) {
        return ArrayUtils.subarray(columns, howManyColumnsOnTheLeftAreForIdentifyingDataRow, columns.length);
    }

}
