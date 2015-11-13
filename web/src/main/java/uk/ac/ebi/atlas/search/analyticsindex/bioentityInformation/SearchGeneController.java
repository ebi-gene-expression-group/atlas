package uk.ac.ebi.atlas.search.analyticsindex.bioentityInformation;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsIndexSearchDAO;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import java.io.IOException;


@Controller
@Scope("request")
public class SearchGeneController extends SearchBioentityController {

    public static final String PROPERTY_TYPE_DESCRIPTION = "description";

    private String[] bioentityPropertyNames;

    @Inject
    public SearchGeneController(AnalyticsIndexSearchDAO analyticsIndexSearchDAO) {
        super(analyticsIndexSearchDAO);
    }

    @Value("#{configuration['index.property_names.genepage']}")
    void setBioentityPropertyNames(String[] bioentityPropertyNames) {
        this.bioentityPropertyNames = bioentityPropertyNames;
    }

    @RequestMapping(value = "/search/genes/{identifier:.*}")
    public String showGeneInformation(@PathVariable String identifier, Model model, RedirectAttributes redirectAttributes) throws IOException, SolrServerException {

        GeneQuery geneQuery = GeneQuery.create(identifier);
        model.addAttribute("geneQuery", geneQuery);
        model.addAttribute("searchDescription", geneQuery.description());

        return showBioentityPage(geneQuery, model);
    }

    @Override
    public String[] getPagePropertyTypes() {
        return bioentityPropertyNames;
    }

    @Override
    public String getBioentityPropertyName() {
        return BIOENTITY_PROPERTY_NAME;
    }

}
