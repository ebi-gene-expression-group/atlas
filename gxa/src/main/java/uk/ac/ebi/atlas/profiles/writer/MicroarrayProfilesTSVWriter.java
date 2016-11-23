package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("microarrayProfileWriter")
@Scope("prototype")
public class MicroarrayProfilesTSVWriter extends DifferentialProfilesTSVWriter<MicroarrayProfile, MicroarrayExpression> {

    private static final String DESIGN_ELEMENT = "Design Element";

    private MicroarrayRequestContext requestContext;

    @Inject
    public MicroarrayProfilesTSVWriter(CsvWriterFactory csvWriterFactory) {
        super(csvWriterFactory);
    }

    @Inject
    public void setRequestContext(MicroarrayRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    public MicroarrayRequestContext getRequestContext() {
        return requestContext;
    }

    @Override
    protected List<String> getExpressionColumnsHeaders(){
        return Lists.newArrayList("p-value", "log2foldchange", "t-statistic");
    }

    @Override
    protected List<Double> getExpressionLevelData(MicroarrayExpression expression){
        return Lists.newArrayList(expression.getPValue(), expression.getFoldChange(), expression.getTstatistic());
    }

    @Override
    protected String[] getProfileIdColumnHeaders(DifferentialProfileStreamOptions options, boolean isGeneSet) {
        return (String[]) ArrayUtils.addAll(super.getProfileIdColumnHeaders(options, isGeneSet), new String[]{DESIGN_ELEMENT});
    }

    @Override
    protected String getSecondaryRowHeader(MicroarrayProfile geneProfile) {
        return geneProfile.getDesignElementName();
    }
}
