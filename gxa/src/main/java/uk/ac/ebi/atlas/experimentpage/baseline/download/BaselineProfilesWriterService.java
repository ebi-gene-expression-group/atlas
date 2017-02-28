package uk.ac.ebi.atlas.experimentpage.baseline.download;

import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamTransforms;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesWriterFactory;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class BaselineProfilesWriterService {


    private BaselineProfileStreamFactory inputStreamFactory;

    private BaselineProfilesWriterFactory baselineProfilesWriterFactory;

    private SolrQueryService solrQueryService;

    private CoexpressedGenesService coexpressedGenesService;

    public BaselineProfilesWriterService(BaselineProfileStreamFactory inputStreamFactory,
                                         BaselineProfilesWriterFactory baselineProfilesWriterFactory,
                                         SolrQueryService solrQueryService,
                                         CoexpressedGenesService coexpressedGenesService) {
        this.inputStreamFactory = inputStreamFactory;
        this.baselineProfilesWriterFactory = baselineProfilesWriterFactory;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;

    }

    public long write(Writer writer, BaselineRequestPreferences preferences, BaselineExperiment experiment,
                      Map<String, Integer> coexpressionsRequested) throws IOException {
        int totalCoexpressionsRequested = 0;
        for (Map.Entry<String, Integer> e : coexpressionsRequested.entrySet()) {
            totalCoexpressionsRequested += e.getValue();
        }
        final BaselineRequestContext requestContext;
        final GeneQueryResponse geneQueryResponse;
        final boolean asGeneSets = false;

        if (totalCoexpressionsRequested == 0) {
            requestContext = BaselineRequestContext.createFor(experiment, preferences);
            geneQueryResponse = solrQueryService.fetchResponse(requestContext.getGeneQuery(), requestContext.getFilteredBySpecies());
        } else {

            GeneQueryResponse originalResponse =
                    solrQueryService.fetchResponse(
                            preferences.getGeneQuery(),
                            BaselineRequestContext.createFor(experiment, preferences).getFilteredBySpecies());

            geneQueryResponse =
                    coexpressedGenesService.extendGeneQueryResponseWithCoexpressions(
                            experiment, originalResponse, coexpressionsRequested);

            requestContext =
                    BaselineRequestContext.createWithCustomGeneQueryDescription(
                            experiment, preferences,
                            describe(
                                    preferences.getGeneQuery(),
                                    geneQueryResponse.getAllGeneIds().size() - originalResponse.getAllGeneIds().size()
                            )
                    );

        }
        return inputStreamFactory.write(experiment, requestContext,
                new BaselineProfileStreamTransforms(requestContext, geneQueryResponse, asGeneSets),
                baselineProfilesWriterFactory.create(writer, requestContext, asGeneSets));
    }

    private String describe(SemanticQuery geneQuery, int coexpressedGenes) {
        return geneQuery.description() + " with " + coexpressedGenes + " similarly expressed genes";
    }
}
