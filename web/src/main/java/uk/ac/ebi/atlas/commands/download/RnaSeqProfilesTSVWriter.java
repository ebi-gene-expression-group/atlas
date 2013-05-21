package uk.ac.ebi.atlas.commands.download;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
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
    public void setRequestContext(RnaSeqRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    public RnaSeqRequestContext getRequestContext() {
        return requestContext;
    }

    @Override
    protected List<String> getExpressionDataLabels() {
        return Lists.newArrayList("p-value", "log2foldchange");
    }

    @Override
    protected List<Double> getExpressionLevelData(DifferentialExpression expression) {
        return Lists.newArrayList(expression.getLevel(), expression.getFoldChange());
    }

}
