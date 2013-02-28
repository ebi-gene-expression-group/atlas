/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commons.configuration.ExperimentFactorsConfiguration;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;

@Controller
@Scope("request")
public class HomePageController {

    private ApplicationProperties properties;

    private ExperimentsCache experimentsCache;

    private SortedSetMultimap<String, String> experimentAccessions;

    private Map<String, String> experimentLinks = new HashMap<>();

    private SortedMap<String, String> experimentDisplayNames = new TreeMap<>();

    private ExperimentFactorsConfiguration configuration;

    @Inject
    public HomePageController(ApplicationProperties properties, ExperimentsCache experimentsCache, ExperimentFactorsConfiguration configuration) {
        this.properties = properties;
        this.experimentsCache = experimentsCache;
        this.configuration = configuration;
    }

    @RequestMapping("/home")
    public String getHomePage(Model model) {

        loadExperimentAccessionsBySpecie();

        model.addAttribute("experimentAccessions", experimentAccessions);
        model.addAttribute("experimentLinks", experimentLinks);
        model.addAttribute("experimentDisplayNames", experimentDisplayNames);

        return "home";
    }

    @PostConstruct
    private void loadExperimentAccessionsBySpecie() {

        for (String experimentAccession : properties.getExperimentIdentifiers()) {
            String displayName = parseDisplayNameForExperiment(experimentAccession);
            experimentDisplayNames.put(experimentAccession, displayName);
        }

        Comparator<String> keyComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
        // experiments should be sorted by their display name, not accession
        Comparator<String> valueComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return experimentDisplayNames.get(o1).compareTo(experimentDisplayNames.get(o2));
            }
        };
        experimentAccessions = TreeMultimap.create(keyComparator, valueComparator);

        for (String experimentAccession : properties.getExperimentIdentifiers()) {

            Experiment experiment = experimentsCache.getExperiment(experimentAccession);

            for (String specie : experiment.getSpecies()) {
                experimentAccessions.put(specie, experimentAccession);
                if (experiment.getSpecies().size() > 1) {
                    experimentLinks.put(experimentAccession + specie, "?serializedFilterFactors=ORGANISM:" + specie);
                } else {
                    experimentLinks.put(experimentAccession + specie, "");
                }
            }

        }

    }

    private String parseDisplayNameForExperiment(String experimentAccession) {

        XMLConfiguration xmlConfiguration = configuration.forExperiment(experimentAccession);
        String displayName = xmlConfiguration.getString("landingPageDisplayName");
        if (displayName != null && displayName.trim().length() > 0) {
            return displayName;
        } else {
            return experimentAccession;
        }

    }


}