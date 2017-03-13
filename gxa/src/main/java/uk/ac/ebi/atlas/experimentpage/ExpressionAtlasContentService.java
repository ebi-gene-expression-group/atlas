package uk.ac.ebi.atlas.experimentpage;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.experimentpage.baseline.download.BaselineProfilesWriterService;
import uk.ac.ebi.atlas.experimentpage.qc.QCReportController;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.resource.ContrastImageSupplier;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@Named
public class ExpressionAtlasContentService {

    private final ExternallyAvailableContentService<BaselineExperiment>
            proteomicsBaselineExperimentExternallyAvailableContentService;
    private final ExternallyAvailableContentService<BaselineExperiment>
            rnaSeqBaselineExperimentExternallyAvailableContentService;
    private final ExternallyAvailableContentService<DifferentialExperiment>
            rnaSeqDifferentialExperimentExternallyAvailableContentService;
    private final ExternallyAvailableContentService<MicroarrayExperiment>
            microarrayExperimentExternallyAvailableContentService;

    private final ExperimentTrader experimentTrader;

    @Inject
    public ExpressionAtlasContentService(
            BaselineProfilesWriterService.Proteomics proteomicsBaselineProfilesWriterService,
            BaselineProfilesWriterService.RnaSeq rnaSeqBaselineProfilesWriterService,
            ContrastImageSupplier.RnaSeq rnaSeqDifferentialContrastImageSupplier,
            ContrastImageSupplier.Microarray microarrayContrastImageSupplier,
            QCReportController qcReportController,
            ExperimentTrader experimentTrader) {
        this.proteomicsBaselineExperimentExternallyAvailableContentService =
                new ExternallyAvailableContentService<>(
                        ImmutableList.<ExternallyAvailableContent.Supplier<BaselineExperiment>>of(
                                proteomicsBaselineProfilesWriterService)
                );
        this.rnaSeqBaselineExperimentExternallyAvailableContentService =
                new ExternallyAvailableContentService<>(
                        ImmutableList.<ExternallyAvailableContent.Supplier<BaselineExperiment>>of(
                                rnaSeqBaselineProfilesWriterService)
                );
        this.rnaSeqDifferentialExperimentExternallyAvailableContentService =
                new ExternallyAvailableContentService<>(
                        ImmutableList.<ExternallyAvailableContent.Supplier<DifferentialExperiment>>of(
                                rnaSeqDifferentialContrastImageSupplier
                        ));
        this.microarrayExperimentExternallyAvailableContentService =
                new ExternallyAvailableContentService<>(
                        ImmutableList.of(
                                microarrayContrastImageSupplier,
                                qcReportController
                        ));
        this.experimentTrader = experimentTrader;
    }

    public Function<HttpServletResponse, Void> stream(String experimentAccession, String accessKey, final URI uri) {
        Experiment<?> experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        if (experiment.getType().isProteomicsBaseline()) {
            return proteomicsBaselineExperimentExternallyAvailableContentService.stream((BaselineExperiment) experiment, uri);
        } else if (experiment.getType().isRnaSeqBaseline()) {
            return rnaSeqBaselineExperimentExternallyAvailableContentService.stream((BaselineExperiment) experiment, uri);
        } else if (experiment.getType().isRnaSeqDifferential()) {
            return rnaSeqDifferentialExperimentExternallyAvailableContentService.stream((DifferentialExperiment) experiment, uri);
        } else {
            return microarrayExperimentExternallyAvailableContentService.stream((MicroarrayExperiment) experiment, uri);
        }
    }

    public List<ExternallyAvailableContent> list(String experimentAccession, String accessKey) {
        Experiment<?> experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        if (experiment.getType().isProteomicsBaseline()) {
            return proteomicsBaselineExperimentExternallyAvailableContentService.list((BaselineExperiment) experiment);
        } else if (experiment.getType().isRnaSeqBaseline()) {
            return rnaSeqBaselineExperimentExternallyAvailableContentService.list((BaselineExperiment) experiment);
        } else if (experiment.getType().isRnaSeqDifferential()) {
            return rnaSeqDifferentialExperimentExternallyAvailableContentService.list((DifferentialExperiment) experiment);
        } else {
            return microarrayExperimentExternallyAvailableContentService.list((MicroarrayExperiment) experiment);
        }
    }


}
