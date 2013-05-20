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
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.web.BioentityPageProperties;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class BioentityPageController {

    protected static final String PROPERTY_TYPE_DESCRIPTION = "description";

    protected static final String PROPERTY_TYPE_SYMBOL = "symbol";

    private SolrClient solrClient;

    private BioentityPageProperties geneCardProperties;

    private Multimap<String, String> propertiesWithValues;

    BioentityPageController(SolrClient solrClient, BioentityPageProperties geneCardProperties) {
        this.solrClient = solrClient;
        this.geneCardProperties = geneCardProperties;
    }

    public String showGenePage(String identifier, Model model) {

        String species = solrClient.findSpeciesForGeneId(identifier);

        model.addAttribute("species", species);

        model.addAttribute("names", generateTypeToNameMap());

        propertiesWithValues = fetchProperties(identifier);

        model.addAttribute("properties", transformPropertiesMapWithLinks(species));

        // there should be only one element of this kind
        model.addAttribute(PROPERTY_TYPE_SYMBOL, getFirstValueOfProperty(getSymbolType()));

        // cleanup gene description
        model.addAttribute(PROPERTY_TYPE_DESCRIPTION, getCleanedUpDescription());

        return "gene";
    }

    private Multimap<String, String> fetchProperties(String identifier) {
        Multimap<String, String> properties = solrClient.fetchGenePageProperties(identifier, getAllBioentityProperties());

        addExtraProperties(properties);
        return properties;
    }

    private String getCleanedUpDescription() {
        String description = getFirstValueOfProperty(PROPERTY_TYPE_DESCRIPTION);
        if (description.contains("[")) {
            description = description.substring(0, description.indexOf('['));
        }
        return description;
    }

    protected String getFirstValueOfProperty(String propertyType) {
        return propertiesWithValues.get(propertyType).iterator().next();
    }


    Multimap<String, Pair<String, String>> transformPropertiesMapWithLinks(final String species) {

        final Multimap<String, Pair<String, String>> results =

                //ToDo: this is called from jsp
                Multimaps.transformEntries(propertiesWithValues, new Maps.EntryTransformer<String, String, Pair<String, String>>() {
                    @Override
                    public Pair<String, String> transformEntry(String propertyType, String propertyValue) {
                        String displayName = propertyValue;
                        if (propertyType.equals("ortholog")) {
                            displayName = transformOrthologToSymbol(displayName);
                        }
                        return createLink(propertyType, displayName, propertyValue, species);
                    }
                });

        return results;
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

    protected Pair createLink(String propertyType, String displayName, String propertyValue, String species) {
        final String linkSpecies = species.replaceAll(" ", "_");

        String link = geneCardProperties.getLink(propertyType);
        if (link != null) {

            if (propertyType.equals("ensprotein") || propertyType.equals("enstranscript")) {
                link = MessageFormat.format(link, linkSpecies, getEncodedString(getFirstValueOfProperty("ensgene"))
                        , getEncodedString(getFirstValueOfProperty("enstranscript")));
            } else {
                link = MessageFormat.format(link, getEncodedString(propertyValue), linkSpecies);
            }

            return Pair.of(displayName, link);
        }
        return Pair.of(displayName, "");
    }

    protected String getEncodedString(String value) {
        try {
            return URLEncoder.encode(value, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Cannot create URL from " + value, e);
        }
    }

    List<String> getFilteredPropertyTypes() {
        String[] split = getAllBioentityProperties();
        List<String> allPropertyTypes = Arrays.asList(split);
        List<String> filteredPropertyTypes = Lists.newArrayList();
        for (String propertyType : allPropertyTypes) {
            if (!propertyType.equals(PROPERTY_TYPE_SYMBOL) && !propertyType.equals(PROPERTY_TYPE_DESCRIPTION)) {
                filteredPropertyTypes.add(propertyType);
            }
        }
        return filteredPropertyTypes;
    }

    private String[] getAllBioentityProperties() {
        return getPagePropertyTypes().split(",");
    }

    protected Map<String, String> generateTypeToNameMap() {
        LinkedHashMap<String, String> result = Maps.newLinkedHashMap();
        List<String> filteredPropertyTypes = getFilteredPropertyTypes();
        for (String propertyType : filteredPropertyTypes) {
            result.put(propertyType, geneCardProperties.getPropertyName(propertyType));
        }

        return result;
    }

    abstract String getPagePropertyTypes();

    abstract String getSymbolType();

    protected void addExtraProperties(Multimap<String, String> properties) {
    }
}