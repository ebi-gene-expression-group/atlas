package uk.ac.ebi.atlas.profiles.differential.microarray;

import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.TsvInputStream;
import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang.ArrayUtils;

import java.util.List;

public class MicroarrayProfilesTsvInputStream extends TsvInputStream<MicroarrayProfile, MicroarrayExpression> {

    private MicroarrayProfileReusableBuilder microarrayProfileBuilder;

    public MicroarrayProfilesTsvInputStream(CSVReader csvReader,
                                            String experimentAccession,
                                            ExpressionsRowDeserializerMicroarrayBuilder expressionsRowDeserializerMicroarrayBuilder,
                                            MicroarrayProfileReusableBuilder microarrayProfileBuilder) {

        super(csvReader, experimentAccession, expressionsRowDeserializerMicroarrayBuilder);
        this.microarrayProfileBuilder = microarrayProfileBuilder;
    }

    public List<Contrast> getOrderedContrastsPresentInStream() {
        ExpressionsRowTsvDeserializerMicroarray tsvRowBuffer = (ExpressionsRowTsvDeserializerMicroarray) this.getExpressionsRowTsvDeserializer();
        return tsvRowBuffer.getOrderedContrasts();
    }

    @Override
    public MicroarrayProfile createProfile() {
        MicroarrayProfile profile = microarrayProfileBuilder.create();
        return profile.isEmpty() ? null : profile;
    }

    @Override
    public void addExpressionToBuilder(MicroarrayExpression expression) {
        microarrayProfileBuilder.addExpression(expression);
    }

    @Override
    public void addGeneInfoValueToBuilder(String[] values) {
        microarrayProfileBuilder.beginNewInstance(values[0], values[1], values[2]);
    }

    protected String[] removeGeneIDAndNameColumns(String[] columns) {
        return (String[]) ArrayUtils.subarray(columns, 3, columns.length);
    }
}
