package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.base.Predicate;
import org.apache.solr.common.SolrInputDocument;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.baseline.IsBaselineExpressionAboveCutoffAndForFilterFactors;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;

import javax.inject.Named;

@Named
public class AnalyticsIndexDocumentValidator {

    private final double RNA_SEQ_BASELINE_EXPRESSION_CUTOFF = 0.5;
    private final double PROTEOMICS_BASELINE_EXPRESSION_CUTOFF = 0.0;
    private final double DIFFERENTIAL_FOLD_CHANGE_CUTOFF = 1.0;
    private final double DIFFERENTIAL_P_VALUE_CUTOFF = 0.1;

    private final Predicate<BaselineExpression> rnaSeqBaselineExpressionAboveDefaultCutoff =
            new IsBaselineExpressionAboveCutoffAndForFilterFactors()
                        .setCutoff(RNA_SEQ_BASELINE_EXPRESSION_CUTOFF);

    private final Predicate<BaselineExpression> proteomicsBaselineExpressionAboveDefaultCutoff =
            new IsBaselineExpressionAboveCutoffAndForFilterFactors()
                        .setCutoff(PROTEOMICS_BASELINE_EXPRESSION_CUTOFF);

    private final Predicate<DifferentialExpression> differentialExpressionAboveDefaultCutoff =
            new IsDifferentialExpressionAboveCutOff()
                        .setFoldChangeCutOff(DIFFERENTIAL_FOLD_CHANGE_CUTOFF)
                        .setPValueCutoff(DIFFERENTIAL_P_VALUE_CUTOFF);

    public boolean validate(SolrInputDocument analyticsInputDocument) {
        ExperimentType experimentType = ExperimentType.valueOf((String) analyticsInputDocument.getFieldValue("experimentType"));

        return (experimentType.isRnaSeqBaseline() &&
                rnaSeqBaselineExpressionAboveDefaultCutoff.apply(new BaselineExpression(
                        (Double) analyticsInputDocument.getFieldValue("expressionLevel"))) ||

                experimentType.isProteomicsBaseline() &&
                proteomicsBaselineExpressionAboveDefaultCutoff.apply(new BaselineExpression(
                        (Double) analyticsInputDocument.getFieldValue("expressionLevel"))) ||

                experimentType.isDifferential() &&
                differentialExpressionAboveDefaultCutoff.apply(new DifferentialExpression(
                        (Double) analyticsInputDocument.getFieldValue("pValue"),
                        (Double) analyticsInputDocument.getFieldValue("foldChange"))));
    }
}

