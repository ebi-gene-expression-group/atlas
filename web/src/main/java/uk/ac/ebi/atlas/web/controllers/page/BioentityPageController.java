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

    public static final String PROPERTY_TYPE_DESCRIPTION = "description";

    private SolrClient solrClient;

    private BioentityPageProperties geneCardProperties;

    BioentityPageController(SolrClient solrClient, BioentityPageProperties geneCardProperties) {
        this.solrClient = solrClient;
        this.geneCardProperties = geneCardProperties;
    }

    public String showGenePage(String identifier, Model model) {

        String species = solrClient.findSpeciesForGeneId(identifier);
        model.addAttribute("species", species);

        Multimap<String, String> properties = solrClient.fetchGenePageProperties(identifier, getAllBioentityProperties());

        addExtraProperties(properties);

        model.addAttribute("properties", transformPropertiesMapWithLinks(properties, species));

        // there should be only one element of this kind
        model.addAttribute(getSymbolType(), properties.get(getSymbolType()).iterator().next());

        // cleanup gene description
        String description = properties.get(PROPERTY_TYPE_DESCRIPTION).iterator().next();
        if (description.contains("[")) {
            description = description.substring(0, description.indexOf("["));
        }
        model.addAttribute(PROPERTY_TYPE_DESCRIPTION, description);

        model.addAttribute("names", generateTypeToNameMap());

        return "gene";
    }


    Multimap<String, Pair<String, String>> transformPropertiesMapWithLinks(final Multimap<String, String> properties, String species) {

        final String linkSpecies = species.replaceAll(" ", "_");

        Multimap<String, Pair<String, String>> results =

                //ToDo: this is called from jsp
                Multimaps.transformEntries(properties, new Maps.EntryTransformer<String, String, Pair<String, String>>() {
                    @Override
                    public Pair<String, String> transformEntry(String propertyType, String propertyValue) {
                        String value = propertyValue;
                        if (propertyType.equals("ortholog")) {
                            value = transformOrthologToSymbol(value);
                        }
                        String link = geneCardProperties.getLink(propertyType);
                        if (link != null) {
                            try {
                                link = MessageFormat.format(link,
                                        URLEncoder.encode(propertyValue, "ISO-8859-1"), linkSpecies);
                            } catch (UnsupportedEncodingException e) {
                                throw new IllegalStateException("Cannot create URL from " + linkSpecies, e);
                            }
                            return Pair.of(value, link);
                        }
                        return Pair.of(value, "");
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

    List<String> getFilteredPropertyTypes() {
        String[] split = getAllBioentityProperties();
        List<String> allPropertyTypes = Arrays.asList(split);
        List<String> filteredPropertyTypes = Lists.newArrayList();
        for (String property_type : allPropertyTypes) {
            if (!property_type.equals(getSymbolType()) && !property_type.equals(PROPERTY_TYPE_DESCRIPTION)) {
                filteredPropertyTypes.add(property_type);
            }
        }
        return filteredPropertyTypes;
    }

    private String[] getAllBioentityProperties() {return getPagePropertyTypes().split(",");}

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