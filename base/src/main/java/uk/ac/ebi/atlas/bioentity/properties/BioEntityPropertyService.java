package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.bioentity.go.GoPoTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named
public class BioEntityPropertyService {

    private SpeciesInferrer speciesInferrer;
    private BioEntityPropertyDao bioEntityPropertyDao;
    private ReactomeClient reactomeClient;
    private GoPoTrader goPoTermTrader;
    private InterProTrader interProTermTrader;

    @Inject
    public BioEntityPropertyService(SpeciesInferrer speciesInferrer,
                                    BioEntityPropertyDao bioEntityPropertyDao,
                                    ReactomeClient reactomeClient,
                                    GoPoTrader goPoTermTrader,
                                    InterProTrader interProTermTrader) {

        this.speciesInferrer = speciesInferrer;
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.reactomeClient = reactomeClient;
        this.goPoTermTrader = goPoTermTrader;
        this.interProTermTrader = interProTermTrader;

    }

    Map<String, String> mapToLinkText(BioentityPropertyName propertyName, Collection<String> propertyValues) {
        switch (propertyName) {
            case ORTHOLOG:
                return propertyValues.stream()
                        .collect(Collectors.toMap(Function.identity(), this::fetchSymbolAndSpeciesForOrtholog));
            case PATHWAYID:
                return reactomeClient.getPathwayNames(propertyValues);
            case GO: case PO:
                return propertyValues.stream()
                        .collect(
                                Collectors.toMap(
                                        Function.identity(),
                                        p -> goPoTermTrader.get(p).map(OntologyTerm::name).orElse(p)));
            case INTERPRO:
                return propertyValues.stream()
                        .collect(
                                Collectors.toMap(
                                        Function.identity(),
                                        p -> interProTermTrader.get(p).map(OntologyTerm::name).orElse(p)));
            default:
                return propertyValues.stream().collect(Collectors.toMap(Function.identity(), Function.identity()));
        }
    }

    private List<BioentityPropertyName> propertiesWeWillDisplay(
            List<BioentityPropertyName> desiredOrderOfPropertyNames,
            final Map<BioentityPropertyName, Set<String>> propertyValuesByType) {

        return desiredOrderOfPropertyNames.stream()
                .filter(propertyName ->
                        !DISPLAYED_PROPERTY_LIST.contains(propertyName) &&
                                propertyValuesByType.containsKey(propertyName))
                .collect(Collectors.toList());
    }

    JsonArray bioentityProperties(String identifier, Species species,
                                  List<BioentityPropertyName> desiredOrderOfPropertyNames,
                                  Map<BioentityPropertyName, Set<String>> propertyValuesByType) {

        JsonArray result = new JsonArray();

        for(BioentityPropertyName bioentityPropertyName :
                propertiesWeWillDisplay(desiredOrderOfPropertyNames, propertyValuesByType)) {
            JsonArray values = new JsonArray();

            for (PropertyLink propertyLink :
                    fetchPropertyLinks(
                            identifier, species, bioentityPropertyName,
                            propertyValuesByType.get(bioentityPropertyName))) {
                values.add(propertyLink.toJson());
            }

            if (values.size() > 0) {
                JsonObject o = new JsonObject();
                o.addProperty("type", bioentityPropertyName.name);
                o.addProperty("name", bioentityPropertyName.label);
                o.add("values", values);
                result.add(o);
            }
        }
        return result;

    }

    public Map<String, Object> addAtributesToModel(String identifier, Species species,
                                                   List<BioentityPropertyName> orderedPropertyNames, String entityName,
                                                   Map<BioentityPropertyName, Set<String>> propertyValuesByType) {
        Map<String, Object> result = new HashMap<>();

        addDesignElements(identifier, propertyValuesByType);

        result.put("entityBriefName",
                StringUtils.isEmpty(entityName)
                        ? identifier
                        : entityName);

        result.put("entityFullName",
                StringUtils.isEmpty(entityName)
                        ? identifier
                        : MessageFormat.format("{0} ({1})", identifier, entityName));

        result.put("bioEntityDescription",getBioEntityDescription(propertyValuesByType));

        result.put("propertyNames", propertiesWeWillDisplay(orderedPropertyNames, propertyValuesByType));

        result.put("bioentityProperties", addBioentityProperties(identifier, species, orderedPropertyNames, propertyValuesByType));

        return result;
    }

    private Map<BioentityPropertyName, List<PropertyLink>> addBioentityProperties (String identifier, Species species,
                                                               List<BioentityPropertyName> desiredOrderOfPropertyNames,
                                                               Map<BioentityPropertyName, Set<String>> propertyValuesByType) {
        Map<BioentityPropertyName, List<PropertyLink>> result = new HashMap<>();

        List<BioentityPropertyName> propertiesToDisplay = propertiesWeWillDisplay(desiredOrderOfPropertyNames, propertyValuesByType);
        for(BioentityPropertyName bioentityPropertyName : propertiesToDisplay) {

            List<PropertyLink> propertyLinkList = fetchPropertyLinks(identifier, species, bioentityPropertyName,
                    propertyValuesByType.get(bioentityPropertyName));

            result.put(bioentityPropertyName, propertyLinkList);

        }

        return result;
    }

    private List<PropertyLink> fetchPropertyLinks(String identifier, Species species,
                                                  BioentityPropertyName bioentityPropertyName,
                                                  Set<String> propertyValues) {
        return propertyValues.stream().map(propertyValue -> createLink(
                identifier, bioentityPropertyName, propertyValue, species,
                assessRelevance(bioentityPropertyName, propertyValue))).collect(Collectors.toList());
    }


    int assessRelevance(BioentityPropertyName bioentityPropertyName, String propertyValue) {
        if (ImmutableList.of(BioentityPropertyName.GO, BioentityPropertyName.PO).contains(bioentityPropertyName)) {
            return goPoTermTrader.get(propertyValue).map(OntologyTerm::depth).orElse(0);
        } else {
            return 0;
        }
    }

    private String fetchSymbolAndSpeciesForOrtholog(String identifier) {

        Species species = speciesInferrer.inferSpeciesForGeneQuery(SemanticQuery.create(identifier));

        if (species.isUnknown()) {
            return identifier;
        }

        String speciesName = species.getName();
        String speciesToken = " (" + Character.toUpperCase(speciesName.charAt(0)) + speciesName.substring(1) + ")";

        Set<String> identifierSymbols =
                bioEntityPropertyDao.fetchPropertyValuesForGeneId(identifier, BioentityPropertyName.SYMBOL);

        if (!identifierSymbols.isEmpty()) {
            return identifierSymbols.iterator().next() + speciesToken;
        }

        return identifier + speciesToken;

    }

}
