package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collection;

public abstract class StaticFilesDownload<E extends Experiment> extends ExternallyAvailableContent.Supplier<E> {
    private DataFileHub dataFileHub;

    //bizarre and I don't remember why I've put experimentAccession twice - Wojtek
    private static final String URL_BASE = "experiments-content/{experimentAccession}/static/{experimentAccession}";
    private static final String R_DATA_URL = URL_BASE + "-atlasExperimentSummary.Rdata";
    private static final String HEATMAP_URL = URL_BASE + "-heatmap.pdf";

    public StaticFilesDownload(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    @Override
    public ExternallyAvailableContent.ContentType contentType() {
        return ExternallyAvailableContent.ContentType.DATA;
    }

    @Override
    public Collection<ExternallyAvailableContent> get(E experiment) {
        ImmutableList.Builder<ExternallyAvailableContent> b = ImmutableList.builder();

        Path rData = dataFileHub.getExperimentMageTabDirLocation()
                                .resolve(experiment.getAccession())
                                .resolve(experiment.getAccession() + "-atlasExperimentSummary.Rdata");
        if (rData.toFile().exists()) {
            b.add(new ExternallyAvailableContent(
                    R_DATA_URL.replaceAll("\\{experimentAccession\\}", experiment.getAccession()),
                    ExternallyAvailableContent.Description.create(
                            "icon-Rdata",
                            "Summary of the expression results for this experiment ready to view in R")));
        }

        Path heatmap = dataFileHub.getExperimentMageTabDirLocation()
                                  .resolve(experiment.getAccession())
                                  .resolve(experiment.getAccession() + "-heatmap.pdf");
        if (heatmap.toFile().exists()) {
            b.add(new ExternallyAvailableContent(
                    HEATMAP_URL.replaceAll("\\{experimentAccession\\}", experiment.getAccession()),
                    ExternallyAvailableContent.Description.create(
                            "icon-pdf",
                            "Heatmap of aggregated expression data")));
        }

        return b.build();
    }

    @Controller
    public static class Forwarder {
        @RequestMapping(value = R_DATA_URL)
        public String downloadRdataURL(@PathVariable String experimentAccession) {
            String path = MessageFormat.format("/expdata/{0}/{0}-atlasExperimentSummary.Rdata", experimentAccession);
            return "forward:" + path;
        }

        @RequestMapping(value = HEATMAP_URL)
        public String downloadPdf(@PathVariable String experimentAccession) {
            String path = MessageFormat.format("/expdata/{0}/{0}-heatmap.pdf", experimentAccession);
            return "forward:" + path;
        }
    }

    @Component
    public static class Baseline extends StaticFilesDownload<BaselineExperiment> {
        public Baseline(DataFileHub dataFileHub) {
            super(dataFileHub);
        }
    }

    @Component
    public static class RnaSeq extends StaticFilesDownload<DifferentialExperiment> {
        public RnaSeq(DataFileHub dataFileHub) {
            super(dataFileHub);
        }
    }

    @Component
    public static class Microarray extends StaticFilesDownload<MicroarrayExperiment> {
        public Microarray(DataFileHub dataFileHub) {
            super(dataFileHub);
        }
    }
}
