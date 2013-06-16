/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.web.controllers.page;

import com.google.common.collect.Lists;
import com.google.common.collect.SortedSetMultimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.utils.ReactomeBiomartClient;
import uk.ac.ebi.atlas.utils.UniProtClient;
import uk.ac.ebi.atlas.web.BioEntityCardProperties;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

@Named("bioEntityPropertyService")
@Scope("request")
public class BioEntityPropertyService {

    public static final String PROPERTY_TYPE_DESCRIPTION = "description";

    private SolrClient solrClient;

    private UniProtClient uniProtClient;

    private BioEntityCardProperties bioEntityCardProperties;

    private SortedSetMultimap<String, String> propertyValuesByType;

    private String species;

    private SortedSet<String> entityNames;

    private ReactomeBiomartClient reactomeBiomartClient;

    @Inject
    public BioEntityPropertyService(SolrClient solrClient, UniProtClient uniProtClient, BioEntityCardProperties bioEntityCardProperties, ReactomeBiomartClient reactomeBiomartClient) {
        this.solrClient = solrClient;
        this.uniProtClient = uniProtClient;
        this.bioEntityCardProperties = bioEntityCardProperties;
        this.reactomeBiomartClient = reactomeBiomartClient;
    }

    void init(String species, SortedSetMultimap<String, String> propertyValuesByType, SortedSet<String> entityNames) {
        this.species = species;
        this.propertyValuesByType = propertyValuesByType;
        this.entityNames = entityNames;
    }

    public String getSpecies() {
        return species;
    }

    //used in bioEntity.jsp
    public List<PropertyLink> getPropertyLinks(String propertyType) {
        if ("reactome".equals(propertyType) && !propertyValuesByType.containsKey(propertyType)) {
            addReactomePropertyValues();
        }
        List<PropertyLink> propertyLinks = Lists.newArrayList();
        for (String propertyValue : propertyValuesByType.get(propertyType)) {

            propertyLinks.add(createLink(propertyType, propertyValue, species));
        }
        return propertyLinks;
    }

    public String getBioEntityDescription() {
        String description = getFirstValueOfProperty(PROPERTY_TYPE_DESCRIPTION);
        return StringUtils.substringBefore(description, "[");
    }

    public SortedSet<String> getEntityNames() {
        return entityNames;
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

    String transformOrthologToSymbol(String identifier) {
        String species = solrClient.findSpeciesForGeneId(identifier);
        species = StringUtils.capitalize(species);
        List<String> valuesForGeneId = solrClient.findPropertyValuesForGeneId(identifier, "symbol");
        if (!valuesForGeneId.isEmpty()) {
            String symbol = valuesForGeneId.get(0);
            return symbol + " (" + species + ")";
        }
        return identifier;
    }

    PropertyLink createLink(String propertyType, String propertyValue, String species) {
        final String linkSpecies = species.replaceAll(" ", "_");

        String linkText = getLinkText(propertyType, propertyValue);

        String link = bioEntityCardProperties.getLinkTemplate(propertyType);
        if (link != null) {

            link = MessageFormat.format(link, getEncodedString(propertyValue), linkSpecies);

            return new PropertyLink(linkText, link);
        }
        return new PropertyLink(linkText);
    }

    String getLinkText(String propertyType, String propertyValue) {
        String displayName = propertyValue;
        if (propertyType.equals("ortholog")) {
            displayName = transformOrthologToSymbol(displayName);
        } else if (propertyType.equals("reactome")) {
            displayName = reactomeBiomartClient.fetchPathwayName(propertyValue);
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

}

