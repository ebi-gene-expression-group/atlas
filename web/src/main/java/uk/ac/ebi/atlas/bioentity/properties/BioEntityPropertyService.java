package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTrader;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.utils.UniProtClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named("bioEntityPropertyService")
@Scope("request")
public class BioEntityPropertyService {

    public static final String PROPERTY_TYPE_DESCRIPTION = "description";

    private BioEntityPropertyDao bioEntityPropertyDao;
    private UniProtClient uniProtClient;
    private ArrayDesignDAO arrayDesignDAO;
    private final BioEntityPropertyLinkBuilder linkBuilder;
    private final GoPoTermTrader goPoTermTrader;

    private SortedSetMultimap<String, String> propertyValuesByType;

    private Multimap<Integer, OntologyTerm> depthToGoTerms;
    private Multimap<Integer, OntologyTerm> depthToPoTerms;

    private String species;

    private String entityName;

    private String identifier;


    @Inject
    public BioEntityPropertyService(BioEntityPropertyDao bioEntityPropertyDao, UniProtClient uniProtClient,
                                    BioEntityPropertyLinkBuilder linkBuilder, ArrayDesignDAO arrayDesignDAO,
                                    GoPoTermTrader goPoTermTrader) {
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.uniProtClient = uniProtClient;
        this.arrayDesignDAO = arrayDesignDAO;
        this.linkBuilder = linkBuilder;
        this.goPoTermTrader = goPoTermTrader;
    }

    public void init(String species, SortedSetMultimap<String, String> propertyValuesByType, Multimap<Integer, OntologyTerm> goTerms, Multimap<Integer, OntologyTerm> poTerms, SortedSet<String> entityNames, String identifier) {
        this.species = species;
        this.propertyValuesByType = propertyValuesByType;
        this.identifier = identifier;
        this.depthToGoTerms = goTerms;
        this.depthToPoTerms = poTerms;

        // this is to add mirbase sequence for ENSEMBL mirnas
        if (propertyValuesByType.containsKey("mirbase_id") && !propertyValuesByType.containsKey("mirbase_sequence")) {
            addMirBaseSequence();
        }
        this.init(species, propertyValuesByType, entityNames.first(),identifier);
    }

    public void init(String species, SortedSetMultimap<String, String> propertyValuesByType, String entityName, String identifier) {
        this.species = species;
        this.propertyValuesByType = propertyValuesByType;
        this.entityName = entityName;
        this.identifier = identifier;
    }

    public String getSpecies() {
        return species;
    }

    public List<PropertyLink> fetchPropertyLinks(String propertyType) {
        if ("reactome".equals(propertyType) && !propertyValuesByType.containsKey(propertyType)) {
            addReactomePropertyValues();
        } else if ("design_element".equals(propertyType) && !propertyValuesByType.containsKey(propertyType)) {
            addDesignElements();
        }

        List<PropertyLink> propertyLinks = Lists.newArrayList();
        for (String propertyValue : propertyValuesByType.get(propertyType)) {
            Optional<PropertyLink> link = linkBuilder.createLink(identifier, propertyType, propertyValue, species,
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

    //TODO have the frontend decide what's relevant
    @Deprecated
    public List<PropertyLink> fetchRelevantGoPoLinks(String ontology, int includeAtLeast) {
        switch (ontology) {
            case "go":
                return fetchRelevantGoLinks(includeAtLeast);
            case "po":
                return fetchRelevantPoLinks(includeAtLeast);
            default:
                return new ImmutableList.Builder<PropertyLink>().build();
        }
    }

    public List<PropertyLink> fetchGoPoLinksOrderedByDepth(String ontology) {
        switch (ontology) {
            case "go":
                return fetchGoLinksOrderedByDepth();
            case "po":
                return fetchPoLinksOrderedByDepth();
            default:
                return new ImmutableList.Builder<PropertyLink>().build();
        }
    }

    @Deprecated
    private List<PropertyLink> fetchRelevantGoLinks(int includeAtLeast) {
        List<PropertyLink> propertyLinks = Lists.newArrayList();

        if (!depthToGoTerms.isEmpty()) {
            for (int depth = Collections.max(depthToGoTerms.keySet()) ; depth >= 1 && propertyLinks.size() < includeAtLeast; depth--) {
                for (OntologyTerm goPoTerm : depthToGoTerms.get(depth)) {
                    Optional<PropertyLink> link = linkBuilder.createLink(identifier, "go", goPoTerm.accession(),
                            species, depth);
                    if (link.isPresent()) {
                        propertyLinks.add(link.get());
                    }
                }
            }
        }

        return propertyLinks;
    }

    private List<PropertyLink> fetchGoLinksOrderedByDepth() {
        List<PropertyLink> propertyLinks = Lists.newArrayList();

        if (!depthToGoTerms.isEmpty()) {
            for (int depth = Collections.max(depthToGoTerms.keySet()) ; depth >= 1 ; depth--) {
                for (OntologyTerm goPoTerm : depthToGoTerms.get(depth)) {
                    Optional<PropertyLink> link = linkBuilder.createLink(identifier, "go", goPoTerm.accession(),
                            species, depth);
                    if (link.isPresent()) {
                        propertyLinks.add(link.get());
                    }
                }
            }
        }

        return propertyLinks;
    }

    @Deprecated
    // We don’t have depth information so far for PO. Once we do, remove this comment and apply the same logic as in GO
    private List<PropertyLink> fetchRelevantPoLinks(int maxLinkCount) {
        List<PropertyLink> propertyLinks = Lists.newArrayList();

        if (!depthToPoTerms.isEmpty()) {
            for (OntologyTerm goPoTerm : depthToPoTerms.values()) {
                Optional<PropertyLink> link = linkBuilder.createLink(identifier, "po", goPoTerm.accession(), species, 0);
                if (link.isPresent()) {
                    propertyLinks.add(link.get());
                }
                if (propertyLinks.size() >= maxLinkCount) {
                    break;
                }
            }
        }

        return propertyLinks;
    }

    private List<PropertyLink> fetchPoLinksOrderedByDepth() {
        List<PropertyLink> propertyLinks = Lists.newArrayList();

            if (!depthToPoTerms.isEmpty()) {
            for (int i = Collections.max(depthToPoTerms.keySet()) ; i >= 1 ; i--) {
                for (OntologyTerm goPoTerm : depthToPoTerms.get(i)) {
                    Optional<PropertyLink> link = linkBuilder.createLink(identifier, "po", goPoTerm.accession(), species, 0);
                    if (link.isPresent()) {
                        propertyLinks.add(link.get());
                    }
                }
            }
        }

        return propertyLinks;
    }

    private void addMirBaseSequence() {
        String mirbase_id = propertyValuesByType.get("mirbase_id").first();
        Set<String> mirbase_sequence = bioEntityPropertyDao.fetchPropertyValuesForGeneId(mirbase_id, "mirbase_sequence");
        propertyValuesByType.putAll("mirbase_sequence", mirbase_sequence);
    }

    private void addDesignElements() {
        List<String> designElements = arrayDesignDAO.getDesignElements(identifier);
        if (!designElements.isEmpty()) {
            propertyValuesByType.putAll("design_element", designElements);
        }
    }

    public String getBioEntityDescription() {
        String description = getFirstValueOfProperty(PROPERTY_TYPE_DESCRIPTION);
        return StringUtils.substringBefore(description, "[");
    }

    public String getEntityName() {
        return entityName;
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

