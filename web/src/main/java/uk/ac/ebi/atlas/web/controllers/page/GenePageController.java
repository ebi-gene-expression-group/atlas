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
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.geneindex.SolrClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Controller
@Scope("request")
public class GenePageController {

    public static final String PROPERTY_TYPE_SYMBOL = "symbol";

    public static final String PROPERTY_TYPE_DESCRIPTION = "description";

    SolrClient solrClient;

    Properties geneCardProperties;

    String genePagePropertyTypes;

    @Inject
    GenePageController(SolrClient solrClient,
                       @Named("genecard") Properties geneCardProperties,
                       @Value("#{configuration['index.types.genepage']}") String genePagePropertyTypes) {
        this.solrClient = solrClient;
        this.geneCardProperties = geneCardProperties;
        this.genePagePropertyTypes = genePagePropertyTypes;
    }

    @RequestMapping(value = "/genes/{identifier}")
    public String showGenePage(@PathVariable String identifier, Model model) {

        model.addAttribute("property_types", filterPropertyTypes());

        String species = solrClient.findSpeciesForGeneId(identifier);
        model.addAttribute("species", species);

        Multimap<String, String> properties = solrClient.fetchGenePageProperties(identifier);
        model.addAttribute("properties", transformPropertiesMapWithLinks(properties, species));

        // there should be only one element of this kind
        model.addAttribute(PROPERTY_TYPE_SYMBOL, properties.get(PROPERTY_TYPE_SYMBOL).iterator().next());

        // cleanup gene description
        String description = properties.get(PROPERTY_TYPE_DESCRIPTION).iterator().next();
        if (description.contains("[")) {
            description = description.substring(0, description.indexOf("["));
        }
        model.addAttribute(PROPERTY_TYPE_DESCRIPTION, description);

        model.addAttribute("names", extractPropertiesFromConfiguration("property"));

        return "gene";
    }

    Multimap<String, Pair<String, String>> transformPropertiesMapWithLinks(final Multimap<String, String> properties, String species) {

        final String linkSpecies = species.replaceAll(" ", "_");

        final Map<String, String> linkTemplates = extractPropertiesFromConfiguration("link");

        Multimap<String, Pair<String, String>> results =

                Multimaps.transformEntries(properties, new Maps.EntryTransformer<String, String, Pair<String, String>>() {
                    @Override
                    public Pair<String, String> transformEntry(String propertyType, String propertyValue) {
                        if (linkTemplates.containsKey(propertyType)) {
                            String link = "";
                            try {
                                link = MessageFormat.format(linkTemplates.get(propertyType),
                                        URLEncoder.encode(propertyValue, "ISO-8859-1"), linkSpecies);
                            } catch (UnsupportedEncodingException e) {
                                // ignore
                            }
                            return Pair.of(propertyValue, link);
                        }
                        return Pair.of(propertyValue, "");
                    }
                });

        return results;
    }


    List<String> filterPropertyTypes() {
        String[] split = genePagePropertyTypes.split(",");
        List<String> allPropertyTypes = Arrays.asList(split);
        List<String> filteredPropertyTypes = Lists.newArrayList();
        for (String property_type : allPropertyTypes) {
            if (!property_type.equals(PROPERTY_TYPE_SYMBOL) && !property_type.equals(PROPERTY_TYPE_DESCRIPTION)) {
                filteredPropertyTypes.add(property_type);
            }
        }
        return filteredPropertyTypes;
    }

    Map<String, String> extractPropertiesFromConfiguration(String prefix) {
        Map<String, String> results = Maps.newHashMap();
        for (String key : geneCardProperties.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                String propertyType = key.substring(prefix.length() + 1);
                results.put(propertyType, geneCardProperties.getProperty(key));
            }
        }
        return results;
    }

}