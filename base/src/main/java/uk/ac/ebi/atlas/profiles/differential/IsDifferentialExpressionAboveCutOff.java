package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;

import javax.inject.Named;

@Named
@Scope("prototype")
public class IsDifferentialExpressionAboveCutOff<Expr extends DifferentialExpression> implements Predicate<Expr> {

    private Regulation regulation;
    private double pValueCutOff;
    private double foldChangeCutOff;

    @Override
    public boolean apply(Expr differentialExpression) {

        if (Regulation.UP == regulation){
            return isOverExpressed(differentialExpression);
        }
        if (Regulation.DOWN == regulation){
            return isUnderExpressed(differentialExpression);
        }
        return isUnderExpressed(differentialExpression) || isOverExpressed(differentialExpression);
    }

    private boolean isOverExpressed(Expr differentialExpression) {
        return differentialExpression.getPValue() < pValueCutOff && differentialExpression.isOverExpressed()
                && differentialExpression.getAbsoluteFoldChange() > foldChangeCutOff;
    }

    private boolean isUnderExpressed(Expr differentialExpression){
        return differentialExpression.getPValue() < pValueCutOff && differentialExpression.isUnderExpressed()
                && differentialExpression.getAbsoluteFoldChange() > foldChangeCutOff;
    }


    public IsDifferentialExpressionAboveCutOff setPValueCutoff(double pValueCutOff){
        this.pValueCutOff = pValueCutOff;
        return this;
    }

    public IsDifferentialExpressionAboveCutOff setFoldChangeCutOff(double foldChangeCutOff) {
        this.foldChangeCutOff = foldChangeCutOff;
        return this;
    }

    public IsDifferentialExpressionAboveCutOff setRegulation(Regulation regulation){
        this.regulation = regulation;
        return this;
    }

}
