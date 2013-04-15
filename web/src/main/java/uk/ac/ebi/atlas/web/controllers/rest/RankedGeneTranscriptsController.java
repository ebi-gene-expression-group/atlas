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

package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.gson.Gson;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.math.util.MathUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Controller
@Scope("request")
public class RankedGeneTranscriptsController {



    @RequestMapping(value = "/json/transcripts/{experimentAccession}/{geneId}/{factor}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getRankedTranscripts(HttpServletRequest request, @PathVariable String experimentAccession,
                                       @PathVariable String geneId,
                                       @PathVariable String factor,
                                        @RequestParam(value = "rankingSize", defaultValue = "3") Integer rankingSize) {

        SortedMap<String, Double> transcriptRates = new TreeMap();

        //Following code is just to generate random ranking, don't keep it seriously!

        double topTranscriptsTotalPercentage = 0;
        for(int i = 0; i < rankingSize; i++){
            double randomPortion = (100d - RandomUtils.nextInt(50))/(rankingSize + 1);
            randomPortion = MathUtils.round(randomPortion, 2);

            transcriptRates.put("ENST00000" + (i + 1), randomPortion);
            topTranscriptsTotalPercentage += randomPortion;
        }

        double othersPortion = 100 - topTranscriptsTotalPercentage;
        transcriptRates.put("Others", othersPortion);


        return new Gson().toJson(transcriptRates, Map.class);

    }

}
