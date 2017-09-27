package uk.ac.ebi.atlas.experimentpage;

import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.differential.download.CanStreamSupplier;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.profiles.ProfileStreamFilter;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.ProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.ProteomicsBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesWriterFactory;
import uk.ac.ebi.atlas.profiles.writer.MicroarrayProfilesWriterFactory;
import uk.ac.ebi.atlas.profiles.writer.RnaSeqDifferentialProfilesWriterFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ExperimentDownloadSupplier<E extends Experiment, Prefs extends ExperimentPageRequestPreferences> extends CanStreamSupplier<E> {


    @Override
    public ExternallyAvailableContent.ContentType contentType() {
        return ExternallyAvailableContent.ContentType.DATA;
    }

    protected abstract void write(HttpServletResponse response, Prefs preferences, E experiment, final String id) throws IOException;

    public static abstract class ExperimentDownloadFileSupplier<E extends Experiment, Prefs extends ExperimentPageRequestPreferences> extends ExperimentDownloadSupplier<E, Prefs> {

        protected void write(HttpServletResponse response, Prefs preferences, E experiment, final String id) throws IOException {
            response.setHeader(
                    "Content-Disposition",
                    MessageFormat.format("attachment; filename=\"{0}-query-results.{1}\"", experiment.getAccession(), id));
            response.setContentType("text/plain; charset=utf-8");
            write(response.getWriter(), preferences, experiment);
        }

        protected abstract void write(Writer writer, Prefs preferences, E experiment);

        protected ExternallyAvailableContent getOne(final E experiment, final Prefs preferences, final String id, String description) {
            return new ExternallyAvailableContent(makeUri(id),
                    ExternallyAvailableContent.Description.create("icon-tsv", description), response -> {
                try {
                    response.setHeader(
                            "Content-Disposition",
                            MessageFormat.format("attachment; filename=\"{0}-query-results.{1}\"", experiment.getAccession(), id));
                    response.setContentType("text/plain; charset=utf-8");

                    write(response.getWriter(), preferences, experiment);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return null;
            });
        }
    }

    public static abstract class Baseline<Unit extends ExpressionUnit.Absolute, Prefs extends BaselineRequestPreferences<Unit>>
            extends ExperimentDownloadFileSupplier<BaselineExperiment, Prefs> {


        private final BaselineProfilesWriterFactory baselineProfilesWriterFactory;
        private final SolrQueryService solrQueryService;
        private final ProfileStreamFactory<AssayGroup, BaselineExpression, BaselineExperiment, BaselineProfileStreamOptions<Unit>, BaselineProfile> baselineProfileStreamFactory;

        protected Baseline(BaselineProfilesWriterFactory baselineProfilesWriterFactory, SolrQueryService solrQueryService,
                           ProfileStreamFactory<AssayGroup, BaselineExpression, BaselineExperiment, BaselineProfileStreamOptions<Unit>, BaselineProfile> baselineProfileStreamFactory) {
            this.baselineProfilesWriterFactory = baselineProfilesWriterFactory;
            this.solrQueryService = solrQueryService;
            this.baselineProfileStreamFactory = baselineProfileStreamFactory;
        }

        protected void write(Writer writer, Prefs preferences, BaselineExperiment experiment) {

            BaselineRequestContext<Unit> requestContext = new BaselineRequestContext<>(preferences, experiment);
            GeneQueryResponse geneQueryResponse =
                    solrQueryService.fetchResponse(requestContext.getGeneQuery(), requestContext.getSpecies());
            baselineProfileStreamFactory.write(experiment, requestContext,
                    geneQueryResponse.asGeneIdsToKeep(), ProfileStreamFilter.create(requestContext),
                    baselineProfilesWriterFactory.create(writer, requestContext));
        }
    }

    @Named
    public static class Proteomics extends Baseline<ExpressionUnit.Absolute.Protein, BaselineRequestPreferences<ExpressionUnit.Absolute.Protein>> {

        @Inject
        public Proteomics(BaselineProfilesWriterFactory baselineProfilesWriterFactory, SolrQueryService solrQueryService,
                          ProteomicsBaselineProfileStreamFactory baselineProfileStreamFactory) {
            super(baselineProfilesWriterFactory, solrQueryService, baselineProfileStreamFactory);
        }


        @Override
        public Collection<ExternallyAvailableContent> get(final BaselineExperiment experiment) {
            return Collections.singleton(getOne(experiment, ProteomicsBaselineRequestPreferences.requestAllData(), "tsv", "Expression values across all genes"));
        }
    }

    @Named
    public static class RnaSeqBaseline extends Baseline<ExpressionUnit.Absolute.Rna, RnaSeqBaselineRequestPreferences> {
        private final DataFileHub dataFileHub;

        @Inject
        public RnaSeqBaseline(BaselineProfilesWriterFactory baselineProfilesWriterFactory, SolrQueryService solrQueryService,
                              RnaSeqBaselineProfileStreamFactory baselineProfileStreamFactory,
                              DataFileHub dataFileHub) {
            super(baselineProfilesWriterFactory, solrQueryService, baselineProfileStreamFactory);
            this.dataFileHub = dataFileHub;
        }

        @Override
        public Collection<ExternallyAvailableContent> get(final BaselineExperiment experiment) {
            return dataFileHub.getRnaSeqBaselineExperimentFiles(experiment.getAccession()).dataFiles().stream().map(unit -> getOne(experiment, RnaSeqBaselineRequestPreferences.requestAllData(unit),
                    MessageFormat.format("{0}s.tsv", unit.toString().toLowerCase()),
                    MessageFormat.format("Expression values across all genes ({0})", unit))).collect(Collectors.toList());
        }
    }

    @Named
    public static class Microarray extends ExperimentDownloadSupplier<MicroarrayExperiment, MicroarrayRequestPreferences> {

        private final SolrQueryService solrQueryService;
        private final MicroarrayProfileStreamFactory microarrayProfileStreamFactory;
        private final MicroarrayProfilesWriterFactory microarrayProfilesWriterFactory;

        @Inject
        public Microarray(SolrQueryService solrQueryService, MicroarrayProfileStreamFactory microarrayProfileStreamFactory,
                          MicroarrayProfilesWriterFactory microarrayProfilesWriterFactory) {
            this.solrQueryService = solrQueryService;
            this.microarrayProfileStreamFactory = microarrayProfileStreamFactory;

            this.microarrayProfilesWriterFactory = microarrayProfilesWriterFactory;
        }

        private Function<Writer, Void> fetchAndWriteGeneProfiles(final MicroarrayExperiment experiment,
                                                                 final MicroarrayRequestPreferences preferences,
                                                                 final GeneQueryResponse geneQueryResponse) {
            final MicroarrayRequestContext context =
                    new DifferentialRequestContextFactory.Microarray().create(experiment, preferences);

            return writer -> {
                microarrayProfileStreamFactory.write(
                        experiment,
                        context,
                        geneQueryResponse.asGeneIdsToKeep(), ProfileStreamFilter.create(context),
                        microarrayProfilesWriterFactory.create(writer, context));
                return null;
            };
        }

        private java.util.function.Function<HttpServletResponse, Void> stream(MicroarrayExperiment experiment,
                                                                              MicroarrayRequestPreferences preferences) {
            GeneQueryResponse geneQueryResponse =
                    solrQueryService.fetchResponse(preferences.getGeneQuery(), experiment.getSpecies());

            List<Pair<String, Function<Writer, Void>>> documents = new ArrayList<>();
            for (String arrayDesign : experiment.getArrayDesignAccessions()) {
                documents.add(Pair.of(
                        MessageFormat.format("{0}-{1}-query-results.tsv", experiment.getAccession(), arrayDesign),
                        fetchAndWriteGeneProfiles(experiment, preferences, geneQueryResponse)
                ));
            }
            return documents.size() == 1 ? streamFile(documents.get(0)) : streamFolder(experiment.getAccession() + "-query-results", documents);
        }

        @Override
        protected void write(HttpServletResponse response, MicroarrayRequestPreferences preferences, MicroarrayExperiment experiment, String id) throws IOException {
            stream(experiment, preferences).apply(response);
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

    @Named
    public static class RnaSeqDifferential extends ExperimentDownloadFileSupplier<DifferentialExperiment, DifferentialRequestPreferences> {


        private final RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory;
        private final SolrQueryService solrQueryService;
        private final RnaSeqDifferentialProfilesWriterFactory rnaSeqDifferentialProfilesWriterFactory;

        @Inject
        public RnaSeqDifferential(RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory, SolrQueryService solrQueryService,
                                  RnaSeqDifferentialProfilesWriterFactory rnaSeqDifferentialProfilesWriterFactory) {
            this.rnaSeqProfileStreamFactory = rnaSeqProfileStreamFactory;
            this.solrQueryService = solrQueryService;
            this.rnaSeqDifferentialProfilesWriterFactory = rnaSeqDifferentialProfilesWriterFactory;
        }

        @Override
        protected void write(Writer responseWriter, DifferentialRequestPreferences differentialRequestPreferences, DifferentialExperiment experiment) {
            RnaSeqRequestContext context =
                    new DifferentialRequestContextFactory.RnaSeq().create(experiment, differentialRequestPreferences);
            GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponse(context.getGeneQuery(), experiment.getSpecies());
            rnaSeqProfileStreamFactory.write(
                    experiment,
                    context,
                    geneQueryResponse.asGeneIdsToKeep(),
                    ProfileStreamFilter.create(context),
                    rnaSeqDifferentialProfilesWriterFactory.create(responseWriter, context));
        }

        @Override
        public Collection<ExternallyAvailableContent> get(DifferentialExperiment experiment) {
            DifferentialRequestPreferences preferences = new DifferentialRequestPreferences();
            preferences.setFoldChangeCutoff(0.0);
            preferences.setCutoff(1.0);
            return Collections.singleton(getOne(experiment, preferences, "tsv", "All expression results in the experiment"));
        }
    }

}
