package uk.ac.ebi.atlas.experimentpage.baseline.download;

import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.io.Writer;
import java.util.Map;

public class BaselineProfilesWriterService {


    private BaselineProfileInputStreamFactory inputStreamFactory;

    private ProfilesWriter<BaselineProfile, Factor, BaselineRequestContext> profilesWriter;

    private SolrQueryService solrQueryService;

    private CoexpressedGenesService coexpressedGenesService;

    public BaselineProfilesWriterService(BaselineProfileInputStreamFactory inputStreamFactory,
                                         ProfilesWriter<BaselineProfile, Factor, BaselineRequestContext> profilesWriter,
                                         SolrQueryService solrQueryService,
                                         CoexpressedGenesService coexpressedGenesService) {
        this.inputStreamFactory = inputStreamFactory;
        this.profilesWriter = profilesWriter;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;

    }

    public long write(Writer writer, BaselineRequestPreferences preferences, BaselineExperiment experiment,
                      Map<String, Integer> coexpressionsRequested)  {
        int totalCoexpressionsRequested = 0;
        for (Map.Entry<String, Integer> e : coexpressionsRequested.entrySet()) {
            totalCoexpressionsRequested += e.getValue();
        }
        final BaselineRequestContext requestContext;
        final GeneQueryResponse geneQueryResponse;

        if (totalCoexpressionsRequested == 0) {
            requestContext = BaselineRequestContext.createFor(experiment, preferences);
            geneQueryResponse = solrQueryService.fetchResponse(requestContext.getGeneQuery(), requestContext.getFilteredBySpecies());
        } else {

            GeneQueryResponse originalResponse = solrQueryService.fetchResponse(preferences
                    .getGeneQuery(), BaselineRequestContext.createFor(experiment, preferences).getFilteredBySpecies());

            geneQueryResponse = coexpressedGenesService
                    .extendGeneQueryResponseWithCoexpressions(experiment, originalResponse, coexpressionsRequested);

            requestContext =
                    BaselineRequestContext.createWithCustomGeneQueryDescription(
                            experiment, preferences,
                            describe(
                                    preferences.getGeneQuery(),
                                    geneQueryResponse.getAllGeneIds().size() - originalResponse.getAllGeneIds().size()
                            )
                    );

        }
        return profilesWriter.write(writer, inputStreamFactory.create(requestContext), requestContext, requestContext.getAllQueryFactors(), geneQueryResponse);
    }

    private String describe(SemanticQuery geneQuery, int coexpressedGenes) {
        return geneQuery.asAnalyticsIndexQueryClause() + " with " + coexpressedGenes + " similarly expressed genes";
    }
}
