
package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("differentialProfileWriter")
@Scope("prototype")
public class RnaSeqProfilesTSVWriter extends DifferentialProfilesTSVWriter<RnaSeqProfile, DifferentialExpression> {

    private RnaSeqRequestContext requestContext;

    @Inject
    public RnaSeqProfilesTSVWriter(CsvWriterFactory csvWriterFactory) {
        super(csvWriterFactory);
    }

    @Inject
    public void setRequestContext(RnaSeqRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    public RnaSeqRequestContext getRequestContext() {
        return requestContext;
    }

    @Override
    protected List<String> getExpressionColumnsHeaders() {
        return Lists.newArrayList("p-value", "log2foldchange");
    }

    @Override
    protected List<Double> getExpressionLevelData(DifferentialExpression expression) {
        return Lists.newArrayList(expression.getPValue(), expression.getFoldChange());
    }

    @Override
    protected String getSecondaryRowHeader(RnaSeqProfile geneProfile) {
        return "";
    }

}
