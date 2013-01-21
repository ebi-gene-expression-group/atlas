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

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import java.util.*;

@Controller
@Scope("singleton")
public class HomePageController {

    private ApplicationProperties properties;

    private ExperimentsCache experimentsCache;

    private Map<String, Double> counts;

    // species > experiment acc > link
    private Map<String, SortedMap<String, String>> speciesToExperiments;

    @Inject
    public HomePageController(ApplicationProperties properties, ExperimentsCache experimentsCache) {
        this.properties = properties;
        this.experimentsCache = experimentsCache;
    }

    @RequestMapping("/home")
    public String getHomePage(Model model) {

        // lazy initialisation
        if (counts == null)
            extractFactorValueCounts();

        ArrayList<WordWeight> wordList = new ArrayList<>();

        for (String factor : counts.keySet()) {
            wordList.add(new WordWeight(factor, counts.get(factor)));
        }

        // does the serialisation to JSON
        Gson gson = new Gson();

        // add data to model
        model.addAttribute("wordlist", gson.toJson(wordList));
        model.addAttribute("speciesToExperiments", speciesToExperiments);

        return "home";
    }

    /**
     * Gets all experiments in data directory and collates experimental defaultFactorValue occurrences into map
     */
    private void extractFactorValueCounts() {

        counts = new HashMap<>();

        speciesToExperiments = new HashMap<>();

        int totalNumberExperiments = 0;

        // check mage-tab directory for its children
        for (String experimentAccession : properties.getExperimentIdentifiers()) {

            // get experiment for directory name
            Experiment experiment = experimentsCache.getExperiment(experimentAccession);

            if (experiment != null) {
                totalNumberExperiments++;

                if (!speciesToExperiments.containsKey(experiment.getSpecie()))
                    speciesToExperiments.put(experiment.getSpecie(), new TreeMap<String, String>());

                String link = buildLinkForExperiment(experiment);
                speciesToExperiments.get(experiment.getSpecie()).put(experimentAccession, link);

                // count per experiment and sum across all experiments, using experiment default factor value
                SortedSet<FactorValue> defaultFactorValues = experiment.getFactorValues(null);
                SortedSet<String> defaultFactorValuesValues = FactorValue.getFactorValuesStrings(defaultFactorValues);
                for (String defaultFactorValue : defaultFactorValuesValues) {
                    if (!counts.containsKey(defaultFactorValue))
                        counts.put(defaultFactorValue, 0.0);
                    counts.put(defaultFactorValue, counts.get(defaultFactorValue) +
                            1.0);
                }
            }
        }

        // normalise for total number of experiments
        for (String factor : counts.keySet()) {
            counts.put(factor, counts.get(factor) / totalNumberExperiments);
        }
    }

    private String buildLinkForExperiment(Experiment experiment) {
        return "experiments/" + experiment.getExperimentAccession();
    }

    private class WordWeight {

        String text;
        double weight;

        WordWeight(String factorValue, double weight) {
            this.text = factorValue;
            this.weight = weight;
        }

    }
}