package uk.ac.ebi.atlas.profiles.differential;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;

import javax.inject.Named;
import java.util.function.Predicate;

@Named
@Scope("prototype")
public class IsDifferentialExpressionAboveCutOff<X extends DifferentialExpression> implements Predicate<X> {
    private Regulation regulation;
    private double pValueCutOff;
    private double foldChangeCutOff;

    @Override
    public boolean test(X differentialExpression) {
        if (Regulation.UP == regulation) {
            return isOverExpressed(differentialExpression);
        }
        if (Regulation.DOWN == regulation) {
            return isUnderExpressed(differentialExpression);
        }
        return isUnderExpressed(differentialExpression) || isOverExpressed(differentialExpression);
    }

    private boolean isOverExpressed(X differentialExpression) {
        return differentialExpression.getPValue() < pValueCutOff && differentialExpression.isOverExpressed()
                && differentialExpression.getAbsoluteFoldChange() > foldChangeCutOff;
    }

    private boolean isUnderExpressed(X differentialExpression) {
        return differentialExpression.getPValue() < pValueCutOff && differentialExpression.isUnderExpressed()
                && differentialExpression.getAbsoluteFoldChange() > foldChangeCutOff;
    }


    public IsDifferentialExpressionAboveCutOff<X> setPValueCutoff(double pValueCutOff) {
        this.pValueCutOff = pValueCutOff;
        return this;
    }

    public IsDifferentialExpressionAboveCutOff<X> setFoldChangeCutOff(double foldChangeCutOff) {
        this.foldChangeCutOff = foldChangeCutOff;
        return this;
    }

    public IsDifferentialExpressionAboveCutOff<X> setRegulation(Regulation regulation) {
        this.regulation = regulation;
        return this;
    }

}
