package uk.ac.ebi.atlas.search;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class JsonGeneSearchController extends JsonExceptionHandlingController {

    private GeneSearchServiceDao geneSearchServiceDao;

    public JsonGeneSearchController(GeneSearchServiceDao geneSearchServiceDao) {
        this.geneSearchServiceDao = geneSearchServiceDao;
    }

    @RequestMapping(
            value = "/json/search/{geneId}",
            method = RequestMethod.GET)
    public String search(@PathVariable String geneId) {
        return GSON.toJson(geneSearchServiceDao.fetchCellIds(geneId, 0.05));
    }
}
