package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.ProfileStreamFilter;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.ProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.ProteomicsBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesWriterFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.search.SearchDescription;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.wrap;

public abstract class BaselineProfilesWriterService<Unit extends ExpressionUnit.Absolute> extends ExternallyAvailableContent.Supplier<BaselineExperiment> {

    @Override
    public ExternallyAvailableContent.ContentType contentType() {
        return ExternallyAvailableContent.ContentType.DATA;
    }

    @Named
    public static class RnaSeq extends BaselineProfilesWriterService<ExpressionUnit.Absolute.Rna> {
        private final DataFileHub dataFileHub;

        @Inject
        RnaSeq(RnaSeqBaselineProfileStreamFactory inputStreamFactory,
               BaselineProfilesWriterFactory baselineProfilesWriterFactory,
               SolrQueryService solrQueryService,
               CoexpressedGenesService coexpressedGenesService, DataFileHub dataFileHub) {
            super(inputStreamFactory, baselineProfilesWriterFactory, solrQueryService, coexpressedGenesService);
            this.dataFileHub = dataFileHub;
        }

        @Override
        public Collection<ExternallyAvailableContent> get(final BaselineExperiment experiment) {
            return FluentIterable.from(dataFileHub.getRnaSeqBaselineExperimentFiles(experiment.getAccession()).dataFiles())
                    .transform(unit -> getOne(experiment, RnaSeqBaselineRequestPreferences.requestAllData(unit),
                            MessageFormat.format("{0}s.tsv", unit.toString().toLowerCase()),
                            MessageFormat.format("Expression values across all genes ({0})", unit))).toList();
        }
    }

    @Named
    public static class Proteomics extends BaselineProfilesWriterService<ExpressionUnit.Absolute.Protein> {
        @Inject
        Proteomics(ProteomicsBaselineProfileStreamFactory inputStreamFactory,
                   BaselineProfilesWriterFactory baselineProfilesWriterFactory,
                   SolrQueryService solrQueryService,
                   CoexpressedGenesService coexpressedGenesService) {
            super(inputStreamFactory, baselineProfilesWriterFactory, solrQueryService, coexpressedGenesService);
        }

        @Override
        public Collection<ExternallyAvailableContent> get(final BaselineExperiment experiment) {
            return Collections.singleton(getOne(experiment, ProteomicsBaselineRequestPreferences.requestAllData(), "tsv", "Expression values across all genes"));
        }
    }

    private ProfileStreamFactory<AssayGroup, BaselineExpression,
            BaselineExperiment, BaselineProfileStreamOptions<Unit>, BaselineProfile> inputStreamFactory;

    private BaselineProfilesWriterFactory baselineProfilesWriterFactory;

    private SolrQueryService solrQueryService;

    private CoexpressedGenesService coexpressedGenesService;

    BaselineProfilesWriterService(ProfileStreamFactory<AssayGroup, BaselineExpression,
            BaselineExperiment, BaselineProfileStreamOptions<Unit>, BaselineProfile> inputStreamFactory,
                                  BaselineProfilesWriterFactory baselineProfilesWriterFactory,
                                  SolrQueryService solrQueryService,
                                  CoexpressedGenesService coexpressedGenesService) {
        this.inputStreamFactory = inputStreamFactory;
        this.baselineProfilesWriterFactory = baselineProfilesWriterFactory;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;

    }

    public long write(Writer writer, BaselineRequestPreferences<Unit> preferences, BaselineExperiment experiment,
                      Map<String, Integer> coexpressionsRequested) {
        int totalCoexpressionsRequested = 0;
        for (Map.Entry<String, Integer> e : coexpressionsRequested.entrySet()) {
            totalCoexpressionsRequested += e.getValue();
        }

        final BaselineRequestContext<Unit> requestContext = new BaselineRequestContext<>(preferences, experiment);
        GeneQueryResponse geneQueryResponse =
                solrQueryService.fetchResponse(requestContext.getGeneQuery(), requestContext.getSpecies());
        if (totalCoexpressionsRequested > 0) {
            geneQueryResponse =
                    coexpressedGenesService.extendGeneQueryResponseWithCoexpressions(
                            experiment, geneQueryResponse, coexpressionsRequested);
        }

        return inputStreamFactory.write(experiment, requestContext,
                new ProfileStreamFilter<AssayGroup, BaselineProfileStreamOptions<Unit>, BaselineProfile>(requestContext, geneQueryResponse),
                baselineProfilesWriterFactory.create(writer, requestContext,
                        describe(requestContext.getGeneQuery(), totalCoexpressionsRequested)
                ));
    }


    private String describe(SemanticQuery geneQuery, int coexpressedGenes) {
        return coexpressedGenes == 0 ? wrap(SearchDescription.get(geneQuery), "'") :
                geneQuery.description() + " with " + coexpressedGenes + " similarly expressed genes";
    }

    ExternallyAvailableContent getOne(final BaselineExperiment experiment, final BaselineRequestPreferences<Unit> preferences, final String id, String description) {
        return new ExternallyAvailableContent(makeUri(id),
                ExternallyAvailableContent.Description.create("icon-tsv", description), response -> {
            try {
                response.setHeader(
                        "Content-Disposition",
                        MessageFormat.format("attachment; filename=\"{0}-query-results.{1}\"", experiment.getAccession(), id));
                response.setContentType("text/plain; charset=utf-8");

                write(response.getWriter(), preferences, experiment, ImmutableMap.<String, Integer>of());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }

}
