package uk.ac.ebi.atlas.search;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardModelFactory;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.bioentity.properties.PropertyLink;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class JsonBioentityInformationController extends JsonExceptionHandlingController {

    private final Gson gson;
    private BioEntityPropertyDao bioEntityPropertyDao;
    private BioEntityCardModelFactory bioEntityCardModelFactory;
    private SpeciesInferrer speciesInferrer;

    @Inject
    public JsonBioentityInformationController(BioEntityPropertyDao bioEntityPropertyDao, BioEntityCardModelFactory bioEntityCardModelFactory, SpeciesInferrer speciesInferrer) {
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.bioEntityCardModelFactory = bioEntityCardModelFactory;
        this.speciesInferrer = speciesInferrer;

        gson = new Gson();
        // Inject BioEntityCardModelFactory?
    }

    @RequestMapping(
            value = "/json/bioentity_information/{geneId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getBioentityInformation(@PathVariable String geneId) {
        Species species = speciesInferrer.inferSpeciesForGeneQuery(SemanticQuery.create(geneId));
        Map<BioentityPropertyName, Set<String>> propertyValues = bioEntityPropertyDao.fetchGenePageProperties(geneId);
        String geneName =
                bioEntityPropertyDao.fetchPropertyValuesForGeneId(geneId, BioentityPropertyName.SYMBOL).stream()
                        .collect(Collectors.joining("/"));


        Map<String, Object> model = bioEntityCardModelFactory.modelAttributes(geneId, species, BioEntityCardProperties.bioentityPropertyNames, geneName, propertyValues);

        return model.get("bioentityProperties").toString();


//        return gson.toJson(this.bioEntityPropertyDao.fetchGenePageProperties(geneId));
//        return gson.toJson(this.bioEntityPropertyDao.fetchTooltipProperties(geneId));
//        return gson.toJson(bioEntityPropertyDao.fetchPropertyValuesForGeneId(geneId, BioentityPropertyName.SYMBOL));

    }
}
