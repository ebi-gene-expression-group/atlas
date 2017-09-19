package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.bioentity.go.GoPoTrader;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.utils.UniProtClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Named
public class BioEntityPropertyService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BioEntityPropertyService.class);

    private final static ImmutableList<BioentityPropertyName> DISPLAYED_PROPERTY_LIST =
            ImmutableList.of(BioentityPropertyName.DESCRIPTION, BioentityPropertyName.SYMBOL);

    private final UniProtClient uniProtClient;
    private final ArrayDesignDAO arrayDesignDAO;
    private final BioEntityPropertyLinkBuilder linkBuilder;
    private final GoPoTrader goPoTermTrader;
    private final Gson gson = new Gson();

    @Inject
    public BioEntityPropertyService(UniProtClient uniProtClient,
                                    BioEntityPropertyLinkBuilder linkBuilder, ArrayDesignDAO arrayDesignDAO,
                                    GoPoTrader goPoTermTrader) {

        this.uniProtClient = uniProtClient;
        this.arrayDesignDAO = arrayDesignDAO;
        this.linkBuilder = linkBuilder;
        this.goPoTermTrader = goPoTermTrader;

    }

    public Map<String, Object> modelAttributes(String identifier, Species species,
                                               List<BioentityPropertyName> orderedPropertyNames, String entityName,
                                               Map<BioentityPropertyName, Set<String>> propertyValuesByType) {

//        addReactomePropertyValues(propertyValuesByType);
        addDesignElements(identifier, propertyValuesByType);

        Map<String, Object> result = new HashMap<>();

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

        result.put("bioentityProperties",
                gson.toJson(bioentityProperties(identifier, species, orderedPropertyNames,propertyValuesByType)));

        return result;

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

    private JsonArray bioentityProperties(String identifier, Species species,
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

    private List<PropertyLink> fetchPropertyLinks(String identifier, Species species,
                                                  BioentityPropertyName bioentityPropertyName,
                                                  Set<String> propertyValues) {

        List<PropertyLink> propertyLinks = Lists.newArrayList();

        for (String propertyValue : propertyValues) {
            Optional<PropertyLink> link =
                    linkBuilder.createLink(
                            identifier, bioentityPropertyName, propertyValue, species,
                            assessRelevance(bioentityPropertyName, propertyValue));

            link.ifPresent(propertyLinks::add);
        }

        return propertyLinks;

    }

    private int assessRelevance(BioentityPropertyName bioentityPropertyName, String propertyValue) {

        if (ImmutableList.of(BioentityPropertyName.GO, BioentityPropertyName.PO).contains(bioentityPropertyName)) {
            OntologyTerm o = goPoTermTrader.getTerm(propertyValue);
            return o != null ? o.depth() : 0;
        } else {
            return 0;
        }

    }

    private void addDesignElements(String identifier,Map<BioentityPropertyName, Set<String>> propertyValuesByType) {

        Set<String> designElements = ImmutableSet.copyOf(arrayDesignDAO.getDesignElements(identifier));

        if (!designElements.isEmpty()) {
            propertyValuesByType.put(BioentityPropertyName.DESIGN_ELEMENT, designElements);
        }

    }

    private String getBioEntityDescription( Map<BioentityPropertyName, Set<String>> propertyValuesByType) {

        String description = getFirstValueOfProperty(BioentityPropertyName.DESCRIPTION, propertyValuesByType);
        return StringUtils.substringBefore(description, "[");

    }

    private String getFirstValueOfProperty(BioentityPropertyName propertyType,
                                           Map<BioentityPropertyName, Set<String>> propertyValuesByType) {

        Collection<String> properties = propertyValuesByType.get(propertyType);
        return CollectionUtils.isNotEmpty(properties) ? properties.iterator().next() : "";

    }

    private void addReactomePropertyValues(Map<BioentityPropertyName, Set<String>> propertyValuesByType) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        // When we update to Java 1.8 we’ll be able to use .getOrDefault instead of having this check
        if (propertyValuesByType.get(BioentityPropertyName.UNIPROT) != null) {
            Set<String> reactomeIds = new HashSet<>();

            Collection<String> uniProtIds = propertyValuesByType.get(BioentityPropertyName.UNIPROT);

            for (String uniProtId : uniProtIds) {
                reactomeIds.addAll(uniProtClient.fetchReactomeIds(uniProtId));
            }

            if (reactomeIds.size() > 0) {
                propertyValuesByType.put(BioentityPropertyName.REACTOME, reactomeIds);
            }
        }

        LOGGER.debug("addReactomePropertyValues: {} seconds", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) / 1000F);

    }

}

