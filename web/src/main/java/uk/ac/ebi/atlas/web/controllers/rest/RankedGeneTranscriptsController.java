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

import com.google.common.base.Function;
import com.google.gson.Gson;
import org.apache.commons.math.util.MathUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.transcript.TranscriptContributionCalculator;
import uk.ac.ebi.atlas.transcript.TranscriptsContribution;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Scope("request")
public class RankedGeneTranscriptsController {

    private TranscriptContributionCalculator transcriptContributionCalculator;

    @Inject
    public RankedGeneTranscriptsController(TranscriptContributionCalculator transcriptContributionCalculator) {
        this.transcriptContributionCalculator = transcriptContributionCalculator;
    }

    @RequestMapping(value = "/json/transcripts/{experimentAccession}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getRankedTranscripts(HttpServletRequest request, @PathVariable String experimentAccession,
                                       @RequestParam("geneId") String geneId,
                                       @RequestParam("factorType") String factorType,
                                       @RequestParam("factorValue") String factorValue,
                                       @RequestParam(value = "rankingSize", defaultValue = "3") Integer rankingSize) {

        Factor factor = new Factor(factorType, factorValue);

        TranscriptsContribution transcriptsContribution = transcriptContributionCalculator.getTranscriptsContribution(geneId, experimentAccession, factor);

        //ToDo: this task of generating rates should be done in the calculator or somewhere else but still used by the calculator and not here.
        //ToDo: Calculator should just return an object wrapping the ordered sorted rates map (OTHER must be the last) and the total number of transcript profiles, including also all 0 profiles

        //ToDo: Javascript will have to be modified to extract the profiles count AND the data map from the json representation of this new object

        return new Gson().toJson(transcriptsContribution.getTranscriptPercentageRates(), Map.class);

    }

    private class PercentageFunction implements Function<Double, Double>{

        private Double totalExpression;

        public PercentageFunction(Double totalExpression){
            this.totalExpression = totalExpression;
        }

        @Override
        public Double apply(java.lang.Double aDouble) {
            return MathUtils.round((aDouble / totalExpression) * 100, 1);
        }
    }

}
