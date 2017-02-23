package uk.ac.ebi.atlas.profiles.differential.microarray;

import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.TsvInputStream;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Reader;
import java.util.List;

public class MicroarrayProfilesTsvInputStream extends TsvInputStream<MicroarrayProfile, MicroarrayExpression> {

    private MicroarrayProfileReusableBuilder microarrayProfileBuilder;

    public MicroarrayProfilesTsvInputStream(Reader reader,
                                            ExpressionsRowDeserializerMicroarrayBuilder expressionsRowDeserializerMicroarrayBuilder,
                                            MicroarrayProfileReusableBuilder microarrayProfileBuilder) {

        super(reader);
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
        return ArrayUtils.subarray(columns, 3, columns.length);
    }
}
