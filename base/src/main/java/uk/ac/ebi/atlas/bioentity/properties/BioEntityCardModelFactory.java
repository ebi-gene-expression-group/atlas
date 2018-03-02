package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class BioEntityCardModelFactory {

    // These are displayed in the header, so we don’t show them in the card table
    private final static ImmutableList<BioentityPropertyName> SKIP_PROPERTY_LIST =
            ImmutableList.of(BioentityPropertyName.DESCRIPTION, BioentityPropertyName.SYMBOL);
    private final static Gson GSON = new Gson();

    private final ArrayDesignDAO arrayDesignDao;
    private final BioEntityPropertyService bioEntityPropertyService;

    @Inject
    public BioEntityCardModelFactory(BioEntityPropertyService bioEntityPropertyService, ArrayDesignDAO arrayDesignDao) {
        this.arrayDesignDao = arrayDesignDao;
        this.bioEntityPropertyService = bioEntityPropertyService;
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

        result.put("bioEntityDescription", getBioEntityDescription(propertyValuesByType));

        result.put("propertyNames", propertiesWeWillDisplay(orderedPropertyNames, propertyValuesByType));

        result.put("bioentityProperties",
                GSON.toJson(bioentityProperties(identifier, species, orderedPropertyNames, propertyValuesByType)));

        return result;

    }

    private List<BioentityPropertyName> propertiesWeWillDisplay(
            List<BioentityPropertyName> desiredOrderOfPropertyNames,
            final Map<BioentityPropertyName, Set<String>> propertyValuesByType) {

        return desiredOrderOfPropertyNames.stream()
                .filter(propertyName ->
                        !SKIP_PROPERTY_LIST.contains(propertyName) &&
                        propertyValuesByType.containsKey(propertyName))
                .collect(Collectors.toList());
    }

    JsonArray bioentityProperties(String identifier,
                                  Species species,
                                  List<BioentityPropertyName> desiredOrderOfPropertyNames,
                                  Map<BioentityPropertyName, Set<String>> propertyValuesByType) {
        JsonArray result = new JsonArray();

        for (BioentityPropertyName bioentityPropertyName :
                propertiesWeWillDisplay(desiredOrderOfPropertyNames, propertyValuesByType)) {
            JsonArray values = new JsonArray();

            for (PropertyLink propertyLink :
                    fetchPropertyLinks(
                            identifier,
                            species,
                            bioentityPropertyName,
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
        return createLinks(identifier, bioentityPropertyName, propertyValues, species);
    }

    private void addDesignElements(String identifier,Map<BioentityPropertyName, Set<String>> propertyValuesByType) {
        Set<String> designElements = ImmutableSet.copyOf(arrayDesignDao.getDesignElements(identifier));

        if (!designElements.isEmpty()) {
            propertyValuesByType.put(BioentityPropertyName.DESIGN_ELEMENT, designElements);
        }
    }

    private String getBioEntityDescription( Map<BioentityPropertyName, Set<String>> propertyValuesByType) {
        String description = getFirstValueOfProperty(BioentityPropertyName.DESCRIPTION, propertyValuesByType);
        return StringUtils.substringBefore(description, "[").trim();
    }

    private String getFirstValueOfProperty(BioentityPropertyName propertyType,
                                           Map<BioentityPropertyName, Set<String>> propertyValuesByType) {

        Collection<String> properties = propertyValuesByType.get(propertyType);
        return CollectionUtils.isNotEmpty(properties) ? properties.iterator().next() : "";

    }

    private List<PropertyLink> createLinks(String identifier,
                                           BioentityPropertyName propertyName,
                                           Collection<String> propertyValues,
                                           Species species) {

        return bioEntityPropertyService.mapToLinkText(propertyName, propertyValues).entrySet().stream()
                .map(
                        linkWithText ->
                                createLink(
                                        linkWithText.getValue(),
                                        identifier,
                                        propertyName,
                                        linkWithText.getKey(),
                                        species,
                                        bioEntityPropertyService.assessRelevance(propertyName, linkWithText.getKey())
                                )
                ).collect(Collectors.toList());

    }

    private PropertyLink createLink(String text, String identifier, BioentityPropertyName propertyName,
                            String propertyValue, Species species, int relevance) {
        return new PropertyLink(
                text,
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
            throw new UncheckedIOException("Cannot create URL from " + value, e);
        }
    }

}

