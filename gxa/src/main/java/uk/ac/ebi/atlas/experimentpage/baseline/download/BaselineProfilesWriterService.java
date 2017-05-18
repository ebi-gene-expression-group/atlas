package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamTransforms;
import uk.ac.ebi.atlas.profiles.baseline.ProteomicsBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesWriterFactory;
import uk.ac.ebi.atlas.search.SearchDescription;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.wrap;

public class BaselineProfilesWriterService extends ExternallyAvailableContent.Supplier<BaselineExperiment>{

    @Override
    public ExternallyAvailableContent.ContentType contentType() {
        return ExternallyAvailableContent.ContentType.DATA;
    }

    @Named
    public static class RnaSeq extends BaselineProfilesWriterService {
        @Inject
        RnaSeq(RnaSeqBaselineProfileStreamFactory inputStreamFactory,
               BaselineProfilesWriterFactory baselineProfilesWriterFactory,
               SolrQueryService solrQueryService,
               CoexpressedGenesService coexpressedGenesService) {
            super(inputStreamFactory, baselineProfilesWriterFactory, solrQueryService, coexpressedGenesService);
        }
    }

    @Named
    public static class Proteomics extends BaselineProfilesWriterService {
        @Inject
        Proteomics(ProteomicsBaselineProfileStreamFactory inputStreamFactory,
                   BaselineProfilesWriterFactory baselineProfilesWriterFactory,
                   SolrQueryService solrQueryService,
                   CoexpressedGenesService coexpressedGenesService) {
            super(inputStreamFactory, baselineProfilesWriterFactory, solrQueryService, coexpressedGenesService);
        }
    }

    private BaselineProfileStreamFactory inputStreamFactory;

    private BaselineProfilesWriterFactory baselineProfilesWriterFactory;

    private SolrQueryService solrQueryService;

    private CoexpressedGenesService coexpressedGenesService;

    BaselineProfilesWriterService(BaselineProfileStreamFactory inputStreamFactory,
                                         BaselineProfilesWriterFactory baselineProfilesWriterFactory,
                                         SolrQueryService solrQueryService,
                                         CoexpressedGenesService coexpressedGenesService) {
        this.inputStreamFactory = inputStreamFactory;
        this.baselineProfilesWriterFactory = baselineProfilesWriterFactory;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;

    }

    public long write(Writer writer, BaselineRequestPreferences preferences, BaselineExperiment experiment,
                      Map<String, Integer> coexpressionsRequested) {
        int totalCoexpressionsRequested = 0;
        for (Map.Entry<String, Integer> e : coexpressionsRequested.entrySet()) {
            totalCoexpressionsRequested += e.getValue();
        }

        final BaselineRequestContext requestContext = new BaselineRequestContext(preferences, experiment);
        GeneQueryResponse geneQueryResponse =
                solrQueryService.fetchResponse(requestContext.getGeneQuery(), requestContext.getSpecies().getReferenceName());
        if(totalCoexpressionsRequested>0){
            geneQueryResponse =
                    coexpressedGenesService.extendGeneQueryResponseWithCoexpressions(
                            experiment, geneQueryResponse, coexpressionsRequested);
        }
        final boolean asGeneSets = false;

        return inputStreamFactory.write(experiment, requestContext,
                new BaselineProfileStreamTransforms(requestContext, geneQueryResponse, asGeneSets),
                baselineProfilesWriterFactory.create(writer, requestContext,
                        describe(requestContext.getGeneQuery(), totalCoexpressionsRequested),
                        asGeneSets));
    }


    private String describe(SemanticQuery geneQuery, int coexpressedGenes) {
        return coexpressedGenes == 0 ? wrap(SearchDescription.get(geneQuery), "'") :
                geneQuery.description() + " with "+ coexpressedGenes + " similarly expressed genes";
    }

    @Override
    public Collection<ExternallyAvailableContent> get(final BaselineExperiment experiment) {
        final Map<String, Integer> coexpressionsRequested = ImmutableMap.of();

        return Collections.singleton(new ExternallyAvailableContent(makeUri("tsv"),
                ExternallyAvailableContent.Description.create("icon-tsv", "Expression values across all genes"), new Function<HttpServletResponse, Void>() {
            @Override
            public Void apply(HttpServletResponse response) {
                try {
                    response.setHeader(
                            "Content-Disposition", "attachment; filename=\"" + experiment.getAccession() + "-query-results.tsv\"");
                    response.setContentType("text/plain; charset=utf-8");

                    write(response.getWriter(), experiment.getType().isProteomicsBaseline() ? ProteomicsBaselineRequestPreferences.requestAllData() : RnaSeqBaselineRequestPreferences.requestAllData(), experiment, coexpressionsRequested);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        }));

    }

}
