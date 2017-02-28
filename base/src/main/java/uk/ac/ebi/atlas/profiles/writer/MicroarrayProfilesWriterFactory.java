package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;

import javax.inject.Named;

@Named
public class MicroarrayProfilesWriterFactory extends DifferentialProfilesWriterFactory<MicroarrayExpression,
        MicroarrayProfile,
        MicroarrayRequestContext> {

    @Override
    protected String[] getProfileIdColumnHeaders(MicroarrayRequestContext requestContext, DifferentialDownLoadOptions profileDownloadOption) {
        return new String[]{"Gene ID", "Gene Name", "Array design ID"};
    }

    @Override
    protected Iterable<String> labelsForColumn(MicroarrayRequestContext requestContext, DifferentialDownLoadOptions profileDownloadOptions,
                                               Contrast dataColumnDescriptor){
        String name = requestContext.displayNameForColumn(dataColumnDescriptor);
        return ImmutableList.of(
                name+".foldChange",
                name+".pValue",
                name+".tStat");
    }

    @Override
    protected Iterable<String> valuesFromColumn(MicroarrayRequestContext requestContext, DifferentialDownLoadOptions profileDownloadOptions, MicroarrayExpression expression) {
        return ImmutableList.of(
                Double.toString(expression.getFoldChange()),
                Double.toString(expression.getPValue()),
                Double.toString(expression.getTstatistic()));
    }
}
