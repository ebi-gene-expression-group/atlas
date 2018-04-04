package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;

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

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.DESIGN_ELEMENT;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.DESCRIPTION;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.GO;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.PO;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.SYMBOL;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@Named
public class BioEntityCardModelFactory {
    // These are displayed in the header, so we don’t show them in the card table
    private final static ImmutableList<BioentityPropertyName> SKIP_PROPERTIES = ImmutableList.of(DESCRIPTION, SYMBOL);

    private final ArrayDesignDAO arrayDesignDao;
    private final BioEntityPropertyService bioEntityPropertyService;

    @Inject
    public BioEntityCardModelFactory(BioEntityPropertyService bioEntityPropertyService,
                                     ArrayDesignDAO arrayDesignDao) {
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
                        !SKIP_PROPERTIES.contains(propertyName) &&
                        propertyValuesByType.containsKey(propertyName))
                .collect(toList());
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
                    createLinks(
                            identifier,
                            bioentityPropertyName,
                            propertyValuesByType.get(bioentityPropertyName),
                            species)) {
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

    private void addDesignElements(String identifier,Map<BioentityPropertyName, Set<String>> propertyValuesByType) {
        Set<String> designElements = ImmutableSet.copyOf(arrayDesignDao.getDesignElements(identifier));

        if (!designElements.isEmpty()) {
            propertyValuesByType.put(DESIGN_ELEMENT, designElements);
        }
    }

    private String getBioEntityDescription( Map<BioentityPropertyName, Set<String>> propertyValuesByType) {
        String firstValueOfDescription =
                propertyValuesByType.getOrDefault(DESCRIPTION, ImmutableSet.of("")).iterator().next();
        return StringUtils.substringBefore(firstValueOfDescription, "[").trim();
    }

    private List<PropertyLink> createLinks(String identifier,
                                           BioentityPropertyName propertyName,
                                           Collection<String> propertyValues,
                                           Species species) {

        return bioEntityPropertyService
                .mapToLinkText(propertyName, propertyValues, species.isPlant()).entrySet().stream().map(
                        linkWithText ->
                                createLink(
                                        linkWithText.getValue(),
                                        identifier,
                                        propertyName,
                                        linkWithText.getKey(),
                                        species,
                                        bioEntityPropertyService.assessRelevance(propertyName, linkWithText.getKey())))
                .sorted(comparing(PropertyLink::getRelevance).reversed())
                .collect(toList());
    }

    private PropertyLink createLink(String text,
                                    String identifier,
                                    BioentityPropertyName propertyName,
                                    String propertyValue,
                                    Species species,
                                    int relevance) {
        return new PropertyLink(
                text,
                // identifier is only used in ENSFAMILY_DESCRIPTION as parameter {1}
                MessageFormat.format(
                        BioEntityCardProperties.getUrlTemplate(propertyName, species),
                        getEncodedString(propertyName, propertyValue),
                        identifier),
                relevance);
    }

    private String getEncodedString(BioentityPropertyName propertyName, String propertyValue) {
        try {
            return
                    URLEncoder.encode(
                            propertyName == GO || propertyName == PO ?
                                    propertyValue.replaceAll(":", "_") :
                                    propertyValue,
                            "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // I think it’s better to have a broken link than the tab/page not showing because an encoding error :/
            // throw new UncheckedIOException("Cannot create URL from " + propertyValue, e);
            return propertyValue;
        }
    }
}

