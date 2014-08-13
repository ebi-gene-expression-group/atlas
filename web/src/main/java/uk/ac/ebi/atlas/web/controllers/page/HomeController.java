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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.trader.cache.OrganismsCache;

import javax.inject.Inject;
import java.util.ArrayList;

@Controller
@Scope("singleton")
public class HomeController {

    private OrganismsCache organismsCache;

    @Inject
    public HomeController(OrganismsCache organismsCache) {
        this.organismsCache = organismsCache;
    }

    @RequestMapping(value = "/home"
                   )
    public String getHomePage(Model model) {
        model.addAttribute("dummyPath", "");

        ArrayList<String> organismSelection =  new ArrayList<>();
        organismSelection.add("Any"); organismSelection.addAll(organismsCache.getOrganismsList());
        model.addAttribute("organisms", organismSelection);
        return "home";
    }
}
