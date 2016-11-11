package uk.ac.ebi.atlas.bioentity.properties;

import uk.ac.ebi.atlas.model.OntologyTerm;
import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTrader;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.utils.UniProtClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Named("bioEntityPropertyService")
public class BioEntityPropertyService {

    private static final String BIOENTITY_PROPERTY_NAME = "symbol";
    public static final String PROPERTY_TYPE_DESCRIPTION = "description";

    private BioEntityPropertyDao bioEntityPropertyDao;
    private UniProtClient uniProtClient;
    private ArrayDesignDAO arrayDesignDAO;
    private final BioEntityPropertyLinkBuilder linkBuilder;
    private final GoPoTermTrader goPoTermTrader;
    private final BioEntityCardProperties bioEntityCardProperties;
    private final Gson gson = new Gson();

    @Inject
    public BioEntityPropertyService(BioEntityPropertyDao bioEntityPropertyDao, UniProtClient uniProtClient,
                                    BioEntityPropertyLinkBuilder linkBuilder, ArrayDesignDAO arrayDesignDAO,
                                    GoPoTermTrader goPoTermTrader,BioEntityCardProperties bioEntityCardProperties) {
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.uniProtClient = uniProtClient;
        this.arrayDesignDAO = arrayDesignDAO;
        this.linkBuilder = linkBuilder;
        this.goPoTermTrader = goPoTermTrader;
        this.bioEntityCardProperties = bioEntityCardProperties;
    }

    public Map<String, Object> modelAttributes(String identifier, Species species, String [] propertyNames,
                                               String entityName, Map<BioentityPropertyName, Set<String>> propertyValuesByType){
        Map<String, Object> result = new HashMap<>();
        result.put("entityName",entityName);
        result.put("bioEntityDescription",getBioEntityDescription(propertyValuesByType));
        result.put("propertyNames", buildPropertyNamesByTypeMap(propertyNames));

        result.put("bioentityProperties", gson.toJson(bioentityProperties(identifier, species, propertyNames,propertyValuesByType)));
        return result;
    }

    private Map<String, String> buildPropertyNamesByTypeMap(String [] propertyNames) {
        LinkedHashMap<String, String> result = Maps.newLinkedHashMap();
        for (String propertyName : propertyNames) {
            if (isDisplayedInPropertyList(propertyName)) {
                result.put(propertyName, bioEntityCardProperties.getPropertyName(propertyName));
            }
        }
        return result;
    }

    private boolean isDisplayedInPropertyList(String propertyType) {
        return !propertyType.equals(PROPERTY_TYPE_DESCRIPTION) && !propertyType.equals(BIOENTITY_PROPERTY_NAME);
    }


    private JsonArray bioentityProperties(String identifier, Species species,String [] propertyNames,SortedSetMultimap<String, String> propertyValuesByType){
        Map<String, String> propertyNamesByType = buildPropertyNamesByTypeMap(propertyNames);
        Map<String, List<PropertyLink>> propertyLinksByType = new HashMap<>();
        for(String propertyName: propertyNamesByType.keySet()){
            propertyLinksByType.put(propertyName, fetchPropertyLinks(identifier, species,propertyName,propertyValuesByType));
        }

        JsonArray result = new JsonArray();
        for(Map.Entry<String,String> e: propertyNamesByType.entrySet()){
            String type = e.getKey();

            JsonArray values = new JsonArray();
            for(PropertyLink propertyLink: propertyLinksByType.get(type)){
                values.add(propertyLink.toJson());
            }
            if(values.size()>0){
                JsonObject o = new JsonObject();
                o.addProperty("type",type);
                o.addProperty("name", e.getValue());
                o.add("values",values);
                result.add(o);
            }
        }
        return result;
    }

    private List<PropertyLink> fetchPropertyLinks(String identifier, Species species, String propertyType, SortedSetMultimap<String, String> propertyValuesByType) {
        if ("reactome".equals(propertyType) && !propertyValuesByType.containsKey(propertyType)) {
            addReactomePropertyValues(propertyValuesByType);
        } else if ("design_element".equals(propertyType) && !propertyValuesByType.containsKey(propertyType)) {
            addDesignElements(identifier,propertyValuesByType);
        }

        List<PropertyLink> propertyLinks = Lists.newArrayList();
        for (String propertyValue : propertyValuesByType.get(propertyType)) {
            Optional<PropertyLink> link = linkBuilder.createLink(identifier, propertyType, propertyValue, species.mappedName,
                    assessRelevance(propertyType, propertyValue));
            if (link.isPresent()) {
                propertyLinks.add(link.get());
            }
        }
        return propertyLinks;
    }

    private int assessRelevance(String propertyType, String propertyValue){
        if(propertyType.equals("go") || propertyType.equals("po")){
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

    private void addDesignElements(String identifier, SortedSetMultimap<String, String> propertyValuesByType) {
        List<String> designElements = arrayDesignDAO.getDesignElements(identifier);
        if (!designElements.isEmpty()) {
            propertyValuesByType.putAll("design_element", designElements);
        }
    }

    private String getBioEntityDescription(SortedSetMultimap<String, String> propertyValuesByType) {
        String description = getFirstValueOfProperty(PROPERTY_TYPE_DESCRIPTION, propertyValuesByType);
        return StringUtils.substringBefore(description, "[");
    }

    private String getFirstValueOfProperty(String propertyType,SortedSetMultimap<String, String> propertyValuesByType) {
        Collection<String> properties = propertyValuesByType.get(propertyType);
        return CollectionUtils.isNotEmpty(properties) ? properties.iterator().next() : "";
    }

    private void addReactomePropertyValues(SortedSetMultimap<String, String> propertyValuesByType) {
        Collection<String> uniprotIds = propertyValuesByType.get("uniprot");
        if (CollectionUtils.isNotEmpty(uniprotIds)) {
            for (String uniprotId : uniprotIds) {
                Collection<String> reactomeIds = uniProtClient.fetchReactomeIds(uniprotId);
                propertyValuesByType.putAll("reactome", reactomeIds);
            }
        }
    }

}

