package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTrader;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.utils.UniProtClient;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named("bioEntityPropertyService")
public class BioEntityPropertyService {

    public static final String PROPERTY_TYPE_DESCRIPTION = "description";

    private UniProtClient uniProtClient;
    private ArrayDesignDAO arrayDesignDAO;
    private final BioEntityPropertyLinkBuilder linkBuilder;
    private final GoPoTermTrader goPoTermTrader;
    private final Gson gson = new Gson();

    @Inject
    public BioEntityPropertyService(UniProtClient uniProtClient,
                                    BioEntityPropertyLinkBuilder linkBuilder, ArrayDesignDAO arrayDesignDAO,
                                    GoPoTermTrader goPoTermTrader) {
        this.uniProtClient = uniProtClient;
        this.arrayDesignDAO = arrayDesignDAO;
        this.linkBuilder = linkBuilder;
        this.goPoTermTrader = goPoTermTrader;
    }

    public Map<String, Object> modelAttributes(String identifier, Species species, List<BioentityPropertyName> desiredOrderOfPropertyNames,
                                               String entityName, Map<BioentityPropertyName, Set<String>> propertyValuesByType){
        addReactomePropertyValues(propertyValuesByType);
        addDesignElements(identifier, propertyValuesByType);

        Map<String, Object> result = new HashMap<>();
        result.put("entityName",entityName);
        result.put("bioEntityDescription",getBioEntityDescription(propertyValuesByType));
        result.put("propertyNames", propertiesWeWillDisplay(desiredOrderOfPropertyNames, propertyValuesByType));

        result.put("bioentityProperties", gson.toJson(bioentityProperties(identifier, species, desiredOrderOfPropertyNames,propertyValuesByType)));
        return result;
    }

    private List<BioentityPropertyName> propertiesWeWillDisplay(List<BioentityPropertyName> desiredOrderOfPropertyNames,
                                      final Map<BioentityPropertyName, Set<String>> propertyValuesByType) {
        return FluentIterable.from(desiredOrderOfPropertyNames).filter(new Predicate<BioentityPropertyName>() {
            @Override
            public boolean apply(@Nullable BioentityPropertyName propertyName) {
                return isDisplayedInPropertyList(propertyName) && propertyValuesByType.containsKey(propertyName);
            }
        }).toList();
    }

    private boolean isDisplayedInPropertyList(BioentityPropertyName propertyName) {
        return ! ImmutableList.of(BioentityPropertyName.DESCRIPTION, BioentityPropertyName.SYMBOL).contains(propertyName);
    }


    private JsonArray bioentityProperties(String identifier, Species species,List<BioentityPropertyName> desiredOrderOfPropertyNames,Map<BioentityPropertyName, Set<String>> propertyValuesByType){

        JsonArray result = new JsonArray();
        for(BioentityPropertyName bioentityPropertyName : propertiesWeWillDisplay(desiredOrderOfPropertyNames, propertyValuesByType)) {
            JsonArray values = new JsonArray();
            for (PropertyLink propertyLink : fetchPropertyLinks(identifier, species, bioentityPropertyName,
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

    private List<PropertyLink> fetchPropertyLinks(String identifier, Species species, BioentityPropertyName
            bioentityPropertyName, Set<String> propertyValues) {
        List<PropertyLink> propertyLinks = Lists.newArrayList();
        for (String propertyValue : propertyValues) {
            Optional<PropertyLink> link =
                    linkBuilder.createLink(identifier, bioentityPropertyName,
                            propertyValue, species.mappedName, assessRelevance(bioentityPropertyName, propertyValue));
            if (link.isPresent()) {
                propertyLinks.add(link.get());
            }
        }
        return propertyLinks;
    }

    private int assessRelevance(BioentityPropertyName bioentityPropertyName, String propertyValue){
        if(ImmutableList.of(BioentityPropertyName.GO, BioentityPropertyName.PO).contains(bioentityPropertyName)){
            OntologyTerm o = goPoTermTrader.getTerm(propertyValue);
            if(o!=null){
                return o.depth();
            } else {
                return 0;
            }
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

    private String getFirstValueOfProperty(BioentityPropertyName propertyType, Map<BioentityPropertyName, Set<String>>
            propertyValuesByType) {
        Collection<String> properties = propertyValuesByType.get(propertyType);
        return CollectionUtils.isNotEmpty(properties) ? properties.iterator().next() : "";
    }

    private void addReactomePropertyValues(Map<BioentityPropertyName, Set<String>> propertyValuesByType) {
        Collection<String> uniprotIds = propertyValuesByType.get(BioentityPropertyName.UNIPROT);
        Set<String> reactomeIds = new HashSet<>();
        if (CollectionUtils.isNotEmpty(uniprotIds)) {
            for (String uniprotId : uniprotIds) {
                reactomeIds.addAll(uniProtClient.fetchReactomeIds(uniprotId));
            }
        }
        if(reactomeIds.size()>0){
            propertyValuesByType.put(BioentityPropertyName.REACTOME, reactomeIds);
        }
    }

}

