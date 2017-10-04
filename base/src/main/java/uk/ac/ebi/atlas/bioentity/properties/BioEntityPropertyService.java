package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.bioentity.go.GoPoTrader;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class BioEntityPropertyService {

    private final static ImmutableList<BioentityPropertyName> DISPLAYED_PROPERTY_LIST =
            ImmutableList.of(BioentityPropertyName.DESCRIPTION, BioentityPropertyName.SYMBOL);

    private final ArrayDesignDAO arrayDesignDAO;
    private final BioEntityPropertyLinkTextService linkBuilder;
    private final GoPoTrader goPoTermTrader;
    private final Gson gson = new Gson();

    @Inject
    public BioEntityPropertyService(BioEntityPropertyLinkTextService linkBuilder, ArrayDesignDAO arrayDesignDAO,
                                    GoPoTrader goPoTermTrader) {

        this.arrayDesignDAO = arrayDesignDAO;
        this.linkBuilder = linkBuilder;
        this.goPoTermTrader = goPoTermTrader;

    }

    public Map<String, Object> modelAttributes(String identifier, Species species,
                                               List<BioentityPropertyName> orderedPropertyNames, String entityName,
                                               Map<BioentityPropertyName, Set<String>> propertyValuesByType) {

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

    private List<PropertyLink> fetchPropertyLinks(String identifier, Species species,
                                                  BioentityPropertyName bioentityPropertyName,
                                                  Set<String> propertyValues) {
        return propertyValues.stream().map(propertyValue -> createLink(
                identifier, bioentityPropertyName, propertyValue, species,
                assessRelevance(bioentityPropertyName, propertyValue))).collect(Collectors.toList());
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

    public PropertyLink createLink(String identifier, BioentityPropertyName propertyName,
                                   String propertyValue, Species species, int relevance) {
        return new PropertyLink(
                Optional.ofNullable(linkBuilder.getLinkTextOrNull(propertyName, propertyValue)).orElse(propertyValue),
                Optional.ofNullable(BioEntityCardProperties.linkTemplates.get(propertyName)).map(
                        linkTemplate -> MessageFormat.format(
                                linkTemplate,
                                getEncodedString(propertyName, propertyValue),
                                species.getEnsemblName(),
                                identifier
                        )
                ).orElse(""),
                relevance
        );
    }

    private String getEncodedString(BioentityPropertyName propertyName, String value) {

        try {
            if (propertyName == BioentityPropertyName.GO || propertyName == BioentityPropertyName.PO) {
                return URLEncoder.encode(value.replaceAll(":", "_"), "UTF-8");
            } else {
                return URLEncoder.encode(value, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Cannot create URL from " + value, e);
        }

    }

}

