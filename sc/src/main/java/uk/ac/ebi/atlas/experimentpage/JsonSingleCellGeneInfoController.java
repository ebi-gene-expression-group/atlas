package uk.ac.ebi.atlas.experimentpage;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.bioentity.properties.PropertyLink;
import uk.ac.ebi.atlas.experimentpage.json.JsonExperimentController;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class JsonSingleCellGeneInfoController extends JsonExperimentController {

    private Gson gson = new GsonBuilder().create();
    private BioEntityPropertyService bioEntityPropertyService;
    private BioEntityPropertyDao bioentityPropertyDao;
    private SpeciesInferrer speciesInferrer;

    @Inject
    public JsonSingleCellGeneInfoController(ExperimentTrader experimentTrader,
                                            BioEntityPropertyService bioEntityPropertyService,
                                            BioEntityPropertyDao bioentityPropertyDao,
                                            SpeciesInferrer speciesInferrer) {
        super(experimentTrader);
        this.bioEntityPropertyService = bioEntityPropertyService;
        this.bioentityPropertyDao = bioentityPropertyDao;
        this.speciesInferrer = speciesInferrer;
    }

    @RequestMapping(value = "/json/bioEntityInformation/{geneId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String geneInformation(@PathVariable String geneId) throws IOException, InterruptedException {
        Species speciesReferenceName = speciesInferrer.inferSpeciesForGeneQuery(SemanticQuery.create(geneId));

        Map<BioentityPropertyName, Set<String>> propertyValuesByType = bioentityPropertyDao.fetchGenePageProperties(geneId);

        Set<String> symbols = bioentityPropertyDao.fetchPropertyValuesForGeneId(geneId, BioentityPropertyName.SYMBOL);
        String geneName = symbols == null || symbols.isEmpty() ? "" : Joiner.on("/").join(symbols);

        Map<String, Object> bioentityGeneInformation = bioEntityPropertyService.addAtributesToModel(geneId,
                speciesReferenceName,
                BioEntityCardProperties.bioentityPropertyNames,
                geneName,
                propertyValuesByType
        );

        List<Object> propertyNamesList = (bioentityGeneInformation).entrySet()
                .stream()
                .filter(map -> "propertyNames".equals(map.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        String entityBriefName = bioentityGeneInformation.entrySet().stream()
                .filter(map -> "entityBriefName".equals(map.getKey()))
                .map(Map.Entry::getValue)
                .findFirst().get().toString();

        String bioEntityDescription = bioentityGeneInformation.entrySet().stream()
                .filter(map -> "bioEntityDescription".equals(map.getKey()))
                .map(Map.Entry::getValue)
                .findFirst().get().toString();

        String entityFullName = bioentityGeneInformation.entrySet().stream()
                .filter(map -> "entityFullName".equals(map.getKey()))
                .map(Map.Entry::getValue)
                .findFirst().get().toString();

        JsonArray result = new JsonArray();

        for (Map.Entry<String, Object> bioentity : bioentityGeneInformation.entrySet()) {
            if (bioentity.getKey().equals("bioentityProperties")) {
                Map<BioentityPropertyName, List<PropertyLink>> bioentityValue = (Map<BioentityPropertyName, List<PropertyLink>>) bioentity.getValue();

                for (Map.Entry<BioentityPropertyName, List<PropertyLink>> bioentities : bioentityValue.entrySet()) {
                    List<PropertyLink> propertyLinkList = bioentities.getValue();
                    JsonArray values = new JsonArray();

                    for (PropertyLink propertyLink : propertyLinkList) {
                        values.add(propertyLink.toJson());
                    }

                    if (values.size() > 0) {
                        JsonObject o = new JsonObject();
                        o.addProperty("type", bioentities.getKey().name);
                        o.addProperty("name", bioentities.getKey().label);
                        o.add("values", values);
                        result.add(o);
                    }
                }

            }

        }

        return gson.toJson(ImmutableMap.of("bioentityProperties", result,
                                        "propertyNames", propertyNamesList,
                                        "entityBriefName", entityBriefName,
                                        "bioEntityDescription", bioEntityDescription,
                                        "entityFullName", entityFullName));
    }
}
