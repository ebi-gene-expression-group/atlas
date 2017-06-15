package uk.ac.ebi.atlas.experimentpage.differential.download;

import com.google.common.base.Function;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.experimentpage.ExperimentDownloadDispatcher;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.differential.DifferentialRequestPreferencesValidator;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.profiles.ProfileStreamFilter;
import uk.ac.ebi.atlas.profiles.stream.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.MicroarrayProfilesWriterFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class MicroarrayExperimentDownloadController extends CanStreamSupplier<MicroarrayExperiment> {

    @Override
    public ExternallyAvailableContent.ContentType contentType() {
        return ExternallyAvailableContent.ContentType.DATA;
    }
    @InitBinder("preferences")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new DifferentialRequestPreferencesValidator());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroarrayExperimentDownloadController.class);

    private static final String PARAMS_TYPE_MICROARRAY = "type=MICROARRAY_ANY";
    private static final String MODEL_ATTRIBUTE_PREFERENCES = "preferences";

    private final MicroarrayProfileStreamFactory microarrayProfileStreamFactory;

    private final MicroarrayProfilesWriterFactory microarrayProfilesWriterFactory;

    private final SolrQueryService solrQueryService;

    private final ExperimentTrader experimentTrader;

    @Inject
    public MicroarrayExperimentDownloadController(ExperimentTrader experimentTrader,
                                                  MicroarrayProfileStreamFactory microarrayProfileStreamFactory,
                                                  MicroarrayProfilesWriterFactory microarrayProfilesWriterFactory,
                                                  SolrQueryService solrQueryService) {
        this.experimentTrader = experimentTrader;
        this.microarrayProfileStreamFactory = microarrayProfileStreamFactory;
        this.microarrayProfilesWriterFactory = microarrayProfilesWriterFactory;
        this.solrQueryService = solrQueryService;
    }

    @RequestMapping(value = ExperimentDownloadDispatcher.url, params = PARAMS_TYPE_MICROARRAY)
    public void downloadGeneProfiles(@PathVariable String experimentAccession,
                                     @RequestParam(value = "accessKey", required = false) String accessKey,
                                     @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES)
                                         @Valid MicroarrayRequestPreferences preferences,
                                     HttpServletResponse response)
    throws IOException {

        LOGGER.info("<downloadMicroarrayGeneProfiles> received download request for requestPreferences: {}", preferences);
        MicroarrayExperiment experiment =
                (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession,accessKey);

        stream(experiment, preferences).apply(response);
    }

    // convenience method for test
    void fetchAndWriteGeneProfiles(Writer responseWriter, MicroarrayExperiment experiment, MicroarrayRequestPreferences
            preferences){
        fetchAndWriteGeneProfiles(experiment, preferences, solrQueryService.fetchResponse(preferences
                .getGeneQuery(), "")).apply(responseWriter);
    }



    Function<Writer, Void> fetchAndWriteGeneProfiles(final MicroarrayExperiment experiment,
                                                     final MicroarrayRequestPreferences preferences,
                                                     final GeneQueryResponse geneQueryResponse){
        final MicroarrayRequestContext context =
                new DifferentialRequestContextFactory.Microarray().create(experiment, preferences);

        return writer -> {
            microarrayProfileStreamFactory.write(
                    experiment,
                    context,
                    new ProfileStreamFilter<>(context, geneQueryResponse),
                    microarrayProfilesWriterFactory.create(writer, context));
            return null;
        };
    }

    Function<HttpServletResponse, Void> stream(MicroarrayExperiment experiment, MicroarrayRequestPreferences preferences){
        //TODO code is not using species for microarray experiments - I think it's wrong
        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponse(preferences.getGeneQuery(), "");

        List<Pair<String, Function<Writer, Void>>> documents = new ArrayList<>();
        for(String arrayDesign: experiment.getArrayDesignAccessions()){
            documents.add(Pair.of(
                    MessageFormat.format("{0}-{1}-query-results.tsv", experiment.getAccession(), arrayDesign),
                    fetchAndWriteGeneProfiles(experiment, preferences, geneQueryResponse)
            ));
        }
        return documents.size() == 1 ? streamFile(documents.get(0)) : streamFolder(experiment.getAccession()+"-query-results", documents);
    }

    @Override
    public Collection<ExternallyAvailableContent> get(MicroarrayExperiment experiment) {
        MicroarrayRequestPreferences preferences = new MicroarrayRequestPreferences();
        preferences.setFoldChangeCutoff(0.0);
        preferences.setCutoff(1.0);
        return Collections.singleton(new ExternallyAvailableContent(
                makeUri("query-results"),
                ExternallyAvailableContent.Description.create("icon-tsv", "All expression results in the experiment"),
                stream(experiment, preferences)
        ));
    }
}