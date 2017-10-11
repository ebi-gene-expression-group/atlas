package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.apache.solr.common.SolrInputDocument;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.baseline.IsBaselineExpressionAboveCutoff;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;

import javax.inject.Named;
import java.util.function.Predicate;

@Named
public class AnalyticsIndexDocumentValidator {

    private final double RNA_SEQ_BASELINE_EXPRESSION_CUTOFF = 0.5;
    private final double PROTEOMICS_BASELINE_EXPRESSION_CUTOFF = 0.0;
    private final double DIFFERENTIAL_FOLD_CHANGE_CUTOFF = 1.0;
    private final double DIFFERENTIAL_P_VALUE_CUTOFF = 0.1;

    private final Predicate<BaselineExpression> rnaSeqBaselineExpressionAboveDefaultCutoff =
            new IsBaselineExpressionAboveCutoff()
                        .setCutoff(RNA_SEQ_BASELINE_EXPRESSION_CUTOFF);

    private final Predicate<BaselineExpression> proteomicsBaselineExpressionAboveDefaultCutoff =
            new IsBaselineExpressionAboveCutoff()
                        .setCutoff(PROTEOMICS_BASELINE_EXPRESSION_CUTOFF);

    private final Predicate<DifferentialExpression> differentialExpressionAboveDefaultCutoff =
            new IsDifferentialExpressionAboveCutOff<>()
                        .setFoldChangeCutOff(DIFFERENTIAL_FOLD_CHANGE_CUTOFF)
                        .setPValueCutoff(DIFFERENTIAL_P_VALUE_CUTOFF);

    public boolean validate(SolrInputDocument analyticsInputDocument) {
        ExperimentType experimentType = ExperimentType.valueOf((String) analyticsInputDocument.getFieldValue("experiment_type"));

        return (experimentType.isRnaSeqBaseline() &&
                rnaSeqBaselineExpressionAboveDefaultCutoff.test(new BaselineExpression(
                        (Double) analyticsInputDocument.getFieldValue("expression_level"))) ||

                experimentType.isProteomicsBaseline() &&
                proteomicsBaselineExpressionAboveDefaultCutoff.test(new BaselineExpression(
                        (Double) analyticsInputDocument.getFieldValue("expression_level"))) ||

                experimentType.isDifferential() &&
                differentialExpressionAboveDefaultCutoff.test(new DifferentialExpression(
                        (Double) analyticsInputDocument.getFieldValue("p_value"),
                        (Double) analyticsInputDocument.getFieldValue("fold_change")
                )));
    }
}
