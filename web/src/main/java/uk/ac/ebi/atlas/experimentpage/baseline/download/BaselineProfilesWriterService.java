package uk.ac.ebi.atlas.experimentpage.baseline.download;

import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

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
                      Map<String, Integer> coexpressionsRequested) throws GenesNotFoundException {
        int totalCoexpressionsRequested = 0;
        for (Map.Entry<String, Integer> e : coexpressionsRequested.entrySet()) {
            totalCoexpressionsRequested += e.getValue();
        }
        final BaselineRequestContext requestContext;
        final GeneQueryResponse geneQueryResponse;

        if (totalCoexpressionsRequested == 0) {
            requestContext = BaselineRequestContext.createFor(experiment, preferences);
            geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext(requestContext, requestContext.getFilteredBySpecies());
        } else {

            GeneQueryResponse originalResponse = solrQueryService.fetchResponseBasedOnRequestContext(preferences
                    .getGeneQuery(), preferences.isExactMatch(), BaselineRequestContext.createFor(experiment, preferences).getFilteredBySpecies());

            geneQueryResponse = coexpressedGenesService
                    .extendGeneQueryResponseWithCoexpressions(experiment, originalResponse, coexpressionsRequested);

            requestContext = BaselineRequestContext.createWithCustomGeneQueryDescription(experiment,
                    preferences, describe(preferences.getGeneQuery(), geneQueryResponse.getAllGeneIds().size() - originalResponse.getAllGeneIds().size()));

        }
        return profilesWriter.write(writer, inputStreamFactory.create(requestContext), requestContext, requestContext.getAllQueryFactors(), geneQueryResponse);
    }

    private String describe(GeneQuery gq, int coexpressedGenes) {
        return gq.description() + " with " + coexpressedGenes + " similarly expressed genes";
    }
}
