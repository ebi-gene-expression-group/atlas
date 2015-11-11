package uk.ac.ebi.atlas.search.analyticsindex;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 11/11/2015.
 */

@Controller
@Scope("request")
public class FacetsController {

    @RequestMapping(value = "/facets")
    public String searchBaseline(@Valid GeneQuerySearchRequestParameters requestParameters, Model model) throws IOException, SolrServerException {
//
//        GeneQuery geneQuery = requestParameters.getGeneQuery();
//
//        if (!geneQuery.isEmpty()) {
//            addSearchHeader(requestParameters, model);
//            model.addAttribute("jsonFacets", baselineAnalyticsSearchService.findFacetsForTreeSearch(geneQuery));
//        }
//
//        return "search-results-baseline";
        return "";
    }

}
