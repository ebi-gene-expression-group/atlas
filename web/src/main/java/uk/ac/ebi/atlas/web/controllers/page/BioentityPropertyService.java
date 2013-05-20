package uk.ac.ebi.atlas.web.controllers.page;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.utils.UniProtClient;
import uk.ac.ebi.atlas.web.BioentityPageProperties;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

@Named
@Scope("request")
public class BioentityPropertyService {

    private SolrClient solrClient;
    private UniProtClient uniProtClient;

    private BioentityPageProperties geneCardProperties;

    private Multimap<String, String> propertyValuesByType;

    private String species;

    @Inject
    public BioentityPropertyService(SolrClient solrClient, UniProtClient uniProtClient, BioentityPageProperties geneCardProperties) {
        this.solrClient = solrClient;
        this.uniProtClient = uniProtClient;
        this.geneCardProperties = geneCardProperties;
    }

    public void init(String identifier, String queryPropertyTypes) {
        species = solrClient.findSpeciesForGeneId(identifier);
        propertyValuesByType = solrClient.fetchGenePageProperties(identifier, queryPropertyTypes.split(","));
    }

    public String getSpecies(){
        return species;
    }

    public List<PropertyLink> getPropertyLinks(String propertyType) {
        if ("reactome".equals(propertyType)){
            addReactomePropertyValues();
        }
        List<PropertyLink> propertyLinks = Lists.newArrayList();
        for(String propertyValue: propertyValuesByType.get(propertyType) ){

            propertyLinks.add(createLink(propertyType, propertyValue, species));
        }
        return propertyLinks;
    }

    String transformOrthologToSymbol(String identifier) {
        String species = solrClient.findSpeciesForGeneId(identifier);
        if (!StringUtils.isBlank(species)) {
            species = species.substring(0, 1).toUpperCase() + species.substring(1);
            List<String> valuesForGeneId = solrClient.findPropertyValuesForGeneId(identifier, "symbol");
            if (!valuesForGeneId.isEmpty()) {
                String symbol = valuesForGeneId.get(0);
                return symbol + " (" + species + ")";
            }
        }
        return identifier;
    }

    PropertyLink createLink(String propertyType, String propertyValue, String species) {
        final String linkSpecies = species.replaceAll(" ", "_");

        String linkText = getLinkText(propertyType, propertyValue);

        String link = geneCardProperties.getLinkTemplate(propertyType);
        if (link != null) {

            if (propertyType.equals("ensprotein") || propertyType.equals("enstranscript")) {
                link = MessageFormat.format(link, linkSpecies, getEncodedString(getFirstValueOfProperty("ensgene"))
                        , getEncodedString(getFirstValueOfProperty("enstranscript")));
            } else {
                link = MessageFormat.format(link, getEncodedString(propertyValue), linkSpecies);
            }

            return new PropertyLink(linkText, link);
        }
        return new PropertyLink(linkText);
    }

    String getLinkText(String propertyType, String propertyValue) {
        String displayName = propertyValue;
        if (propertyType.equals("ortholog")) {
            displayName = transformOrthologToSymbol(displayName);
        }
        return displayName;
    }

    String getEncodedString(String value) {
        try {
            return URLEncoder.encode(value, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Cannot create URL from " + value, e);
        }
    }

    String getFirstValueOfProperty(String propertyType) {
        Collection<String> properties = propertyValuesByType.get(propertyType);
        return CollectionUtils.isNotEmpty(properties) ? properties.iterator().next() : "";
    }

    void addReactomePropertyValues() {
        Collection<String> uniprotIds = propertyValuesByType.get("uniprot");
        if (CollectionUtils.isNotEmpty(uniprotIds)) {
            for (String uniprotId : uniprotIds) {
                Collection<String> reactomeIds = uniProtClient.fetchReactomeIds(uniprotId);
                propertyValuesByType.putAll("reactome", reactomeIds);
            }
        }
    }

}

