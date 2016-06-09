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
                                         CoexpressedGenesService coexpressedGenesService){
        this.inputStreamFactory = inputStreamFactory;
        this.profilesWriter = profilesWriter;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;

    }

    public long write(Writer writer, BaselineRequestPreferences preferences, BaselineExperiment experiment,
                     Map<String, Integer> coexpressionsRequested) throws GenesNotFoundException {
        int totalCoexpressionsRequested = 0;
        for(Map.Entry<String, Integer> e: coexpressionsRequested.entrySet()){
            totalCoexpressionsRequested+=e.getValue();
        }
        if(totalCoexpressionsRequested==0){
            BaselineRequestContext requestContext = BaselineRequestContext.createFor(experiment,preferences);
            GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext(requestContext, requestContext.getFilteredBySpecies());
            return profilesWriter
                    .write(writer, inputStreamFactory.create(requestContext), requestContext, requestContext.getAllQueryFactors(),geneQueryResponse);
        } else {
            GeneQuery originalGeneQuery = preferences.getGeneQuery();
            GeneQuery extendedGeneQuery = coexpressedGenesService.extendGeneQueryWithCoexpressions(experiment,
                    originalGeneQuery,coexpressionsRequested);

            preferences.setGeneQuery(extendedGeneQuery);
            BaselineRequestContext requestContext = BaselineRequestContext.createWithCustomGeneQueryDescription(experiment,preferences,
                    describe(originalGeneQuery, extendedGeneQuery.size()-originalGeneQuery.size()));
            GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext(requestContext, requestContext.getFilteredBySpecies());
            long count = profilesWriter.write(writer, inputStreamFactory.create(requestContext), requestContext,
                    requestContext.getAllQueryFactors(),geneQueryResponse);
            preferences.setGeneQuery(originalGeneQuery);

            return count;
        }
    }

    private String describe(GeneQuery gq, int coexpressedGenes){
        return gq.description() +" with "+coexpressedGenes+" similarly expressed genes";
    }
}
