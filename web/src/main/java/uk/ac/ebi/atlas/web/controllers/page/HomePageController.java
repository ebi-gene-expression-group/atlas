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
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("singleton")
public class HomePageController {

    private class WordWrapper {

        String text;
        double weight;
        String link;

        public WordWrapper(String t, double d) {
            this(t, d, null);
        }

        public WordWrapper(String t, double d, String l) {
            this.text = t;
            this.weight = d;
            this.link = l;
        }

    }

    private ApplicationProperties properties;

    private ExperimentsCache experimentsCache;

    private Map<String, Double> counts;

    private Map<String, List<String>> speciesToExperiments;

    @Inject
    public HomePageController(ApplicationProperties properties, ExperimentsCache experimentsCache) {
        this.properties = properties;
        this.experimentsCache = experimentsCache;
        extractFactorValueCounts();
    }

    /**
     * Gets all experiments in data directory and collates experimental factor occurrences into map
     */
    private void extractFactorValueCounts() {

        counts = new HashMap<>();

        speciesToExperiments = new HashMap<>();

        int totalNumberExperiments = 0;

        // check mage-tab directory for its children
        for (String expAcc : properties.getExperimentIdentifiers()) {

            // get experiment for directory name
            Experiment experiment = experimentsCache.getExperiment(expAcc);
            if (experiment != null) {
                totalNumberExperiments++;

                if (!speciesToExperiments.containsKey(experiment.getSpecie()))
                    speciesToExperiments.put(experiment.getSpecie(), new ArrayList<String>());
                speciesToExperiments.get(experiment.getSpecie()).add(expAcc);

                // count per experiment and sum across all experiments
                for (String factor : experiment.getAllExperimentalFactors()) {
                    if (!counts.containsKey(factor))
                        counts.put(factor, 0.0);
                    counts.put(factor, counts.get(factor) +
                            1.0);
                }
            }
        }

        // normalise for total number of experiments
        for (String factor : counts.keySet()) {
            counts.put(factor, counts.get(factor) / totalNumberExperiments);
        }
    }

    @RequestMapping("/home")
    public String get(Model model) {

        ArrayList<WordWrapper> wordList = new ArrayList<>();

        for (String factor : counts.keySet()) {
            wordList.add(new WordWrapper(factor, counts.get(factor)));
        }

        // does the serialisation to JSON
        Gson gson = new Gson();

        // add data to model
        model.addAttribute("wordlist", gson.toJson(wordList));
        model.addAttribute("speciesToExperiments", speciesToExperiments);

        return "home";
    }
}