package uk.ac.ebi.atlas.experimentpage.differential.download;

import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamTransforms;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.RnaSeqDifferentialProfilesWriterFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;

@Controller
public class RnaSeqExperimentDownloadController extends CanStreamSupplier<DifferentialExperiment> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RnaSeqExperimentDownloadController.class);

    private static final String PARAMS_TYPE_DIFFERENTIAL = "type=RNASEQ_MRNA_DIFFERENTIAL";
    private static final String MODEL_ATTRIBUTE_PREFERENCES = "preferences";

    private final RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory;

    private final RnaSeqDifferentialProfilesWriterFactory rnaSeqDifferentialProfilesWriterFactory;

    private final SolrQueryService solrQueryService;

    private final ExperimentTrader experimentTrader;

    @Inject
    public RnaSeqExperimentDownloadController(
            RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory,
            RnaSeqDifferentialProfilesWriterFactory rnaSeqDifferentialProfilesWriterFactory,
            SolrQueryService solrQueryService,
            ExperimentTrader experimentTrader) {
        this.rnaSeqProfileStreamFactory = rnaSeqProfileStreamFactory;
        this.rnaSeqDifferentialProfilesWriterFactory = rnaSeqDifferentialProfilesWriterFactory;
        this.solrQueryService = solrQueryService;
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = PARAMS_TYPE_DIFFERENTIAL)
    public void downloadGeneProfiles(@PathVariable String experimentAccession,
                                     @RequestParam(value = "accessKey", required = false) String accessKey,
                                     @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid
                                     DifferentialRequestPreferences preferences, HttpServletResponse response) throws IOException {

        DifferentialExperiment experiment = (DifferentialExperiment)
                experimentTrader.getExperiment(experimentAccession,accessKey);

        LOGGER.info("<downloadGeneProfiles> received download request for requestPreferences: {}", preferences);

        streamFile(experiment.getAccession() + "-query-results.tsv",
                fetchAndWriteGeneProfiles(experiment, preferences)
        ).apply(response);

        LOGGER.info("<downloadGeneProfiles> streamed gene expression profiles");

    }

    void fetchAndWriteGeneProfiles(Writer responseWriter, DifferentialExperiment experiment, DifferentialRequestPreferences differentialRequestPreferences){
        RnaSeqRequestContext context =
                new DifferentialRequestContextFactory.RnaSeq().create(experiment, differentialRequestPreferences);
        rnaSeqProfileStreamFactory.write(
                experiment,
                context,
                new DifferentialProfileStreamTransforms<RnaSeqProfile>(context,
                        solrQueryService.fetchResponse(context.getGeneQuery(), experiment.getSpecies().getReferenceName())),
                rnaSeqDifferentialProfilesWriterFactory.create(responseWriter, context));
    }

    Function<Writer, Void> fetchAndWriteGeneProfiles(final DifferentialExperiment experiment, final DifferentialRequestPreferences differentialRequestPreferences){
        final RnaSeqRequestContext context =
                new DifferentialRequestContextFactory.RnaSeq().create(experiment, differentialRequestPreferences);
        return new Function<Writer, Void>(){

            @Nullable
            @Override
            public Void apply(@Nullable Writer writer) {
                rnaSeqProfileStreamFactory.write(
                        experiment,
                        context,
                        new DifferentialProfileStreamTransforms<RnaSeqProfile>(context,
                                solrQueryService.fetchResponse(context.getGeneQuery(), experiment.getSpecies().getReferenceName())),
                        rnaSeqDifferentialProfilesWriterFactory.create(writer, context));
                return null;
            }
        };
    }

    @Override
    public Collection<ExternallyAvailableContent> get(DifferentialExperiment experiment) {
        DifferentialRequestPreferences preferences = new DifferentialRequestPreferences();
        preferences.setFoldChangeCutOff(0.0);
        preferences.setCutoff(1.0);
        return Collections.singleton(new ExternallyAvailableContent(
                makeUri("query-results"),
                ExternallyAvailableContent.Description.create("Data", "icon-tsv", "All the data presented in the heatmap"),
                streamFile(experiment.getAccession() + "-query-results.tsv",
                        fetchAndWriteGeneProfiles(experiment, preferences)
                )
        ));
    }
}
