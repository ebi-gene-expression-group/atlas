package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
@Named
public class SingleCellContentService {

    private final ExperimentTrader experimentTrader;
    private final ExternallyAvailableContentService<BaselineExperiment>
            rnaSeqBaselineExperimentExternallyAvailableContentService;

    @Inject
    public SingleCellContentService(
            ExperimentDownloadSupplier.RnaSeqBaseline rnaSeqBaselineExperimentDownloadSupplier,
            StaticFilesDownload.Baseline baselineStaticFilesDownload,
            ExperimentDesignFile.Baseline baselineExperimentDesignFile,
            LinkToArrayExpress.RnaSeqBaseline rnaSeqBaselineLinkToArrayExpress,
            ExperimentTrader experimentTrader) {

        this.rnaSeqBaselineExperimentExternallyAvailableContentService =
                new ExternallyAvailableContentService<>(
                        ImmutableList.of(
                                rnaSeqBaselineExperimentDownloadSupplier,
                                baselineStaticFilesDownload,
                                baselineExperimentDesignFile,
                                rnaSeqBaselineLinkToArrayExpress)
                );

        this.experimentTrader = experimentTrader;
    }

    public List<ExternallyAvailableContent> list(String experimentAccession,
                                                 String accessKey,
                                                 ExternallyAvailableContent.ContentType contentType) {
        Experiment<?> experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        return rnaSeqBaselineExperimentExternallyAvailableContentService.list(
                (BaselineExperiment) experiment, contentType);

    }
}
