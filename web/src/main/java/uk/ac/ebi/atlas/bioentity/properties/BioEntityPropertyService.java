package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTrader;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.utils.UniProtClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named("bioEntityPropertyService")
@Scope("request")
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

    private SortedSetMultimap<String, String> propertyValuesByType;


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

    public void init(SortedSetMultimap<String, String> propertyValuesByType) {
        this.propertyValuesByType = propertyValuesByType;

        // this is to add mirbase sequence for ENSEMBL mirnas
        if (propertyValuesByType.containsKey("mirbase_id") && !propertyValuesByType.containsKey("mirbase_sequence")) {
            addMirBaseSequence();
        }
    }

    public Map<String, Object> modelAttributes(String identifier, Species species,String [] propertyNames,
                                               String entityName){
        Map<String, Object> result = new HashMap<>();
        result.put("entityName",entityName);
        result.put("bioEntityDescription",getBioEntityDescription());
        result.put("propertyNames", buildPropertyNamesByTypeMap(propertyNames));

        result.put("bioentityProperties", gson.toJson(bioentityProperties(identifier, species, propertyNames)));
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


    JsonArray bioentityProperties(String identifier, Species species,String [] propertyNames){
        Map<String, String> propertyNamesByType = buildPropertyNamesByTypeMap(propertyNames);
        Map<String,List<PropertyLink>> propertyLinksByType = new HashMap<>();
        for(String propertyName: propertyNamesByType.keySet()){
            propertyLinksByType.put(propertyName, fetchPropertyLinks(identifier, species,propertyName));
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




    public List<PropertyLink> fetchPropertyLinks(String identifier, Species species, String propertyType) {
        if ("reactome".equals(propertyType) && !propertyValuesByType.containsKey(propertyType)) {
            addReactomePropertyValues();
        } else if ("design_element".equals(propertyType) && !propertyValuesByType.containsKey(propertyType)) {
            addDesignElements(identifier);
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

    private void addMirBaseSequence() {
        String mirbase_id = propertyValuesByType.get("mirbase_id").first();
        Set<String> mirbase_sequence = bioEntityPropertyDao.fetchPropertyValuesForGeneId(mirbase_id, "mirbase_sequence");
        propertyValuesByType.putAll("mirbase_sequence", mirbase_sequence);
    }

    private void addDesignElements(String identifier) {
        List<String> designElements = arrayDesignDAO.getDesignElements(identifier);
        if (!designElements.isEmpty()) {
            propertyValuesByType.putAll("design_element", designElements);
        }
    }

    //TODO geneset used this
    public String getBioEntityDescription() {
        String description = getFirstValueOfProperty(PROPERTY_TYPE_DESCRIPTION);
        return StringUtils.substringBefore(description, "[");
    }

    private String getFirstValueOfProperty(String propertyType) {
        Collection<String> properties = propertyValuesByType.get(propertyType);
        return CollectionUtils.isNotEmpty(properties) ? properties.iterator().next() : "";
    }

    private void addReactomePropertyValues() {
        Collection<String> uniprotIds = propertyValuesByType.get("uniprot");
        if (CollectionUtils.isNotEmpty(uniprotIds)) {
            for (String uniprotId : uniprotIds) {
                Collection<String> reactomeIds = uniProtClient.fetchReactomeIds(uniprotId);
                propertyValuesByType.putAll("reactome", reactomeIds);
            }
        }
    }

}

