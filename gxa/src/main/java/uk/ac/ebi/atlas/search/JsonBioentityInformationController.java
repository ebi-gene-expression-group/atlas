package uk.ac.ebi.atlas.search;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardModelFactory;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties.BIOENTITY_PROPERTY_NAMES;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.SYMBOL;

@RestController
public class JsonBioentityInformationController extends JsonExceptionHandlingController {

    private BioEntityPropertyDao bioEntityPropertyDao;
    private BioEntityCardModelFactory bioEntityCardModelFactory;
    private SpeciesInferrer speciesInferrer;

    @Inject
    public JsonBioentityInformationController(BioEntityPropertyDao bioEntityPropertyDao,
                                              BioEntityCardModelFactory bioEntityCardModelFactory,
                                              SpeciesInferrer speciesInferrer) {
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.bioEntityCardModelFactory = bioEntityCardModelFactory;
        this.speciesInferrer = speciesInferrer;
    }

    @RequestMapping(
            value = "/json/bioentity_information/{geneId:.+}", // Don't truncate ".value" at the end of IDs
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getBioentityInformation(@PathVariable String geneId) {
        Species species = speciesInferrer.inferSpeciesForGeneQuery(SemanticQuery.create(geneId));
        Map<BioentityPropertyName, Set<String>> propertyValues = bioEntityPropertyDao.fetchGenePageProperties(geneId);
        String geneName =
                bioEntityPropertyDao.fetchPropertyValuesForGeneId(geneId, SYMBOL).stream()
                        .collect(Collectors.joining("/"));

        Map<String, Object> model =
                bioEntityCardModelFactory.modelAttributes(
                        geneId,
                        species,
                        BIOENTITY_PROPERTY_NAMES,
                        geneName,
                        propertyValues);

        return model.get("bioentityProperties").toString();
    }
}
