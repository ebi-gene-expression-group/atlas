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

@Controller
@Scope("request")
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

    @RequestMapping("/home")
    public String get(Model model) {

        // this could be extracted from database
        WordWrapper[] array = new WordWrapper[]{

                new WordWrapper("Homo sapiens", 13, "experiments/E-MTAB-513"),
                new WordWrapper("Mus musculus", 10.5, "experiments/E-MTAB-599"),
                new WordWrapper("Dolor", 9.4),
                new WordWrapper("Sit", 8),
                new WordWrapper("Amet", 6.2),
                new WordWrapper("Consectetur", 5),
                new WordWrapper("Adipiscing", 5),
                new WordWrapper("Elit", 5),
                new WordWrapper("Nam et", 5),
                new WordWrapper("Leo", 4),
                new WordWrapper("ArrayExpress", 4, "http://www.ebi.ac.uk/arrayexpress"),
                new WordWrapper("Pellentesque", 3),
                new WordWrapper("habitant", 3),
                new WordWrapper("morbi", 3),
                new WordWrapper("tristisque", 3),
                new WordWrapper("senectus", 3),
                new WordWrapper("et netus", 3),
                new WordWrapper("et malesuada", 3),
                new WordWrapper("fames", 2),
                new WordWrapper("ac turpis", 2),
                new WordWrapper("egestas", 2),
                new WordWrapper("Aenean", 2),
                new WordWrapper("vestibulum", 2),
                new WordWrapper("elit", 2),
                new WordWrapper("sit amet", 2),
                new WordWrapper("metus", 2),
                new WordWrapper("adipiscing", 2),
                new WordWrapper("ut ultrices", 2),
                new WordWrapper("justo", 1),
                new WordWrapper("dictum", 1),
                new WordWrapper("Ut et leo", 1),
                new WordWrapper("metus", 1),
                new WordWrapper("at molestie", 1),
                new WordWrapper("purus", 1),
                new WordWrapper("Curabitur", 1),
                new WordWrapper("diam", 1),
                new WordWrapper("dui", 1),
                new WordWrapper("ullamcorper", 1),
                new WordWrapper("id vuluptate ut", 1),
                new WordWrapper("mattis", 1),
                new WordWrapper("et nulla", 1),
                new WordWrapper("Sed", 1)

        };

        // does the serialisation to JSON
        Gson gson = new Gson();

        // add data to model
        model.addAttribute("wordlist", gson.toJson(array));

        return "home";
    }
}