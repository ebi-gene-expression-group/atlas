package uk.ac.ebi.atlas.commands;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class GeneProfilesFilter {

    private SolrQueryService solrQueryService;

    @Inject
    public GeneProfilesFilter(SolrQueryService solrQueryService) {
        this.solrQueryService = solrQueryService;
    }

    public <F,P extends ExperimentPageRequestPreferences, K extends Profile> ObjectInputStream<K> filterInputStream(ObjectInputStream<K> inputStream, RequestContext<F, P> requestContext) throws GenesNotFoundException {
        return filterInputStream(inputStream, requestContext, requestContext.getFilteredBySpecies());
    }


    public <F,P extends ExperimentPageRequestPreferences, K extends Profile> ObjectInputStream<K> filterInputStreamAnySpecies(ObjectInputStream<K> inputStream, RequestContext<F, P> requestContext) throws GenesNotFoundException {
        // search for gene IDs across any species
        String species = "";
        return filterInputStream(inputStream, requestContext, species);
    }

    private <F,P extends ExperimentPageRequestPreferences, K extends Profile> ObjectInputStream<K> filterInputStream(ObjectInputStream<K> inputStream, RequestContext<F, P> requestContext, String species) throws GenesNotFoundException {

        if (StringUtils.isBlank(requestContext.getGeneQuery())) {
            return new GeneProfileInputStreamFilter<>(inputStream, requestContext.getSelectedQueryFactors());
        }

        GeneQueryResponse geneQueryResponse = solrQueryService.findGeneIdsOrSets(requestContext.getGeneQuery(),
                requestContext.isExactMatch(),
                species,
                requestContext.isGeneSetMatch());

        if (geneQueryResponse.isEmpty()) {
            throw new GenesNotFoundException("No genes found for searchText = " + requestContext.getGeneQuery() + ", species = " + requestContext.getFilteredBySpecies());
        }

        //ToDo: this initialization is unrelated to this method, but I haven't find yet a better place for it
        requestContext.setGeneQueryResponse(geneQueryResponse);

        return new GeneProfileInputStreamFilter<>(inputStream, geneQueryResponse.getAllGeneIds(), requestContext.getSelectedQueryFactors());
    }

}
