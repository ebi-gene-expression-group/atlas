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

package uk.ac.ebi.atlas.web.controllers.widgets;

import com.google.common.collect.Lists;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.web.controllers.page.crossexperiment.BaselineBioentitiesCount;

import java.util.List;

@Controller
@Scope("request")
public class BaselineCountsWidgetController {

    @RequestMapping(value = "/widgets/baselinecounts")
    public String showResultPage(@RequestParam(value="condition", required = false) String condition, Model model) {

        if(StringUtils.isNotBlank(condition)){

            List<BaselineBioentitiesCount> baselineCounts = buildBaselineCounts();

            model.addAttribute("baselineCounts", baselineCounts);

        }
        return "baselineCounts-widget";
    }

    private List<BaselineBioentitiesCount> buildBaselineCounts() {
        return Lists.newArrayList(buildBaselineCount("E-MTAB-513"), buildBaselineCount("E-MTAB-599"));
    }

    private BaselineBioentitiesCount buildBaselineCount(String experimentAccession) {
        return new BaselineBioentitiesCount(experimentAccession,
                "pa" + RandomStringUtils.random(3, new char[]{'c','h','i','u','b','o'}) + "ngo patongo", experimentAccession, 123);
    }

}
