package uk.ac.ebi.atlas.profiles.differential;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.Regulation;

import javax.inject.Named;

@Named
@Scope("prototype")
public class IsDifferentialExpressionAboveCutOff implements Predicate<DifferentialExpression> {

    private Regulation regulation;
    private double pValueCutOff;
    private double foldChangeCutOff;

    @Override
    public boolean apply(DifferentialExpression differentialExpression) {

        if (Regulation.UP == regulation){
            return isOverExpressed(differentialExpression);
        }
        if (Regulation.DOWN == regulation){
            return isUnderExpressed(differentialExpression);
        }
        return isUnderExpressed(differentialExpression) || isOverExpressed(differentialExpression);
    }

    private boolean isOverExpressed(DifferentialExpression differentialExpression) {
        return differentialExpression.getPValue() < pValueCutOff && differentialExpression.isOverExpressed()
                && differentialExpression.getAbsoluteFoldChange() > foldChangeCutOff;
    }

    private boolean isUnderExpressed(DifferentialExpression differentialExpression){
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
