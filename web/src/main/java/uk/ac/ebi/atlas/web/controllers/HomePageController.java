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

package uk.ac.ebi.atlas.web.controllers;

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

import static com.google.common.base.Preconditions.checkNotNull;

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

    private Map<String, List<String>> organismToExperiments;

    @Inject
    public HomePageController(ApplicationProperties properties, ExperimentsCache experimentsCache) {
        this.properties = properties;
        this.experimentsCache = experimentsCache;
        extractOrganismPartCounts();
    }

    /**
     * Gets all experiments in data directory and collates organism part percentage into map
     */
    private void extractOrganismPartCounts() {

        counts = new HashMap<>();

        organismToExperiments = new HashMap<>();

        int totalNumberExperiments = 0;

        // check mage-tab directory for its children
        for (String expAcc : properties.getExperimentIdentifiers()) {

            // get experiment for directory name
            Experiment experiment = checkNotNull(experimentsCache.getExperiment(expAcc),
                    "Experiment with identifier " + expAcc + " not found.");
            totalNumberExperiments++;

            if (!organismToExperiments.containsKey(experiment.getSpecie()))
                organismToExperiments.put(experiment.getSpecie(), new ArrayList<String>());
            organismToExperiments.get(experiment.getSpecie()).add(expAcc);

            // count per experiment and sum across all experiments
            for (String organismPart : experiment.getAllOrganismParts()) {
                if (!counts.containsKey(organismPart))
                    counts.put(organismPart, 0.0);
                counts.put(organismPart, counts.get(organismPart) +
                        1.0);
            }
        }

        // normalise for total number of experiments
        for (String organismPart : counts.keySet()) {
            counts.put(organismPart, counts.get(organismPart) / totalNumberExperiments);
        }
    }

    @RequestMapping("/home")
    public String get(Model model) {

        ArrayList<WordWrapper> wordList = new ArrayList<>();

        for (String organismPart : counts.keySet()) {
            wordList.add(new WordWrapper(organismPart, counts.get(organismPart)));
        }

        // does the serialisation to JSON
        Gson gson = new Gson();

        // add data to model
        model.addAttribute("wordlist", gson.toJson(wordList));
        model.addAttribute("organismToExperiments", organismToExperiments);

        return "home";
    }
}