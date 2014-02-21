package uk.ac.ebi.atlas.commands;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class LoadGeneIdsIntoRequestContext {

    private SolrQueryService solrQueryService;

    @Inject
    public LoadGeneIdsIntoRequestContext(SolrQueryService solrQueryService) {
        this.solrQueryService = solrQueryService;
    }

    public void load(RequestContext requestContext, String species) throws GenesNotFoundException {
        String geneQuery = requestContext.getGeneQuery();

        if (StringUtils.isBlank(geneQuery)) {
            return;
        }

        GeneQueryResponse geneQueryResponse = solrQueryService.findGeneIdsOrSets(geneQuery,
                requestContext.isExactMatch(),
                species,
                requestContext.isGeneSetMatch());

        if (geneQueryResponse.isEmpty()) {
            throw new GenesNotFoundException("No genes found for searchText = " + geneQuery + ", species = " + species);
        }

        requestContext.setGeneQueryResponse(geneQueryResponse);
    }

}
