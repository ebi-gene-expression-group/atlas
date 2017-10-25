package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;

import javax.inject.Named;
import java.util.function.Predicate;

@Named
@Scope("prototype")
public class IsDifferentialExpressionAboveCutOff<Expr extends DifferentialExpression> implements Predicate<Expr> {

    private Regulation regulation;
    private double pValueCutOff;
    private double foldChangeCutOff;

    @Override
    public boolean test(Expr differentialExpression) {

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


    public IsDifferentialExpressionAboveCutOff<Expr> setPValueCutoff(double pValueCutOff){
        this.pValueCutOff = pValueCutOff;
        return this;
    }

    public IsDifferentialExpressionAboveCutOff<Expr> setFoldChangeCutOff(double foldChangeCutOff) {
        this.foldChangeCutOff = foldChangeCutOff;
        return this;
    }

    public IsDifferentialExpressionAboveCutOff<Expr> setRegulation(Regulation regulation){
        this.regulation = regulation;
        return this;
    }

}
