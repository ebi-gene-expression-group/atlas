package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;

import javax.annotation.Nullable;
import javax.inject.Named;

@Named
public class MicroarrayProfilesWriterFactory extends DifferentialProfilesWriterFactory<MicroarrayExpression,
        MicroarrayProfile,
        MicroarrayRequestContext> {

    @Override
    protected String[] getProfileIdColumnHeaders(MicroarrayRequestContext requestContext) {
        return new String[]{"Gene ID", "Gene Name", "Design Element"};
    }

    @Override
    protected Iterable<String> labelsForColumn(MicroarrayRequestContext requestContext,
                                               Contrast dataColumnDescriptor){
        String name = requestContext.displayNameForColumn(dataColumnDescriptor);
        return ImmutableList.of(
                name+".foldChange",
                name+".pValue",
                name+".tStat");
    }

    @Override
    protected Iterable<String> valuesFromColumn(MicroarrayRequestContext requestContext, @Nullable MicroarrayExpression expression) {
        return expression == null ? ImmutableList.of("","", "") :
                ImmutableList.of(
                Double.toString(expression.getFoldChange()),
                Double.toString(expression.getPValue()),
                Double.toString(expression.getTstatistic()));
    }
}
