package uk.ac.ebi.atlas.profiles.writer;

import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.stream.Stream;

@Named
public class MicroarrayProfilesWriterFactory
        extends DifferentialProfilesWriterFactory<MicroarrayExpression, MicroarrayProfile, MicroarrayRequestContext> {

    @Override
    protected String[] getProfileIdColumnHeaders(MicroarrayRequestContext requestContext) {
        return new String[]{"Gene ID", "Gene Name", "Design Element"};
    }

    @Override
    protected Stream<String> labelsForColumn(MicroarrayRequestContext requestContext,
                                             Contrast dataColumnDescriptor){
        String name = requestContext.displayNameForColumn(dataColumnDescriptor);
        return Stream.of(name+".foldChange", name+".pValue", name+".tStat");
    }

    @Override
    protected Stream<String> valuesFromColumn(MicroarrayRequestContext requestContext, @Nullable MicroarrayExpression expression) {
        return expression == null ?
                Stream.of("","", "") :
                Stream.of(Double.toString(expression.getFoldChange()),
                          Double.toString(expression.getPValue()),
                          Double.toString(expression.getTstatistic()));
    }
}
