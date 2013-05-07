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
import com.google.gson.reflect.TypeToken;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.transcript.TranscriptContributions;
import uk.ac.ebi.atlas.transcript.TranscriptContributionsCalculator;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Collection;

@Controller
@Scope("request")
public class RankedGeneTranscriptsController {

    private TranscriptContributionsCalculator transcriptContributionsCalculator;

    @Inject
    public RankedGeneTranscriptsController(TranscriptContributionsCalculator transcriptContributionsCalculator) {
        this.transcriptContributionsCalculator = transcriptContributionsCalculator;
    }

    @RequestMapping(value = "/json/transcripts/{experimentAccession}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getRankedTranscripts(HttpServletRequest request, @PathVariable String experimentAccession,
                                       @RequestParam("geneId") String geneId,
                                       @RequestParam("factorType") String factorType,
                                       @RequestParam("factorValue") String factorValue,
                                       @RequestParam("selectedFilterFactorsJson") String selectedFilterFactorsJson,
                                       @RequestParam(value = "rankingSize", defaultValue = "3") Integer rankingSize) {

        Gson gson = new Gson();

        Factor selectedQueryFactor = new Factor(factorType, factorValue);
        FactorSet selectedFactorGroup = new FactorSet().add(selectedQueryFactor);
        Type factorsCollectionType = new TypeToken<Collection<Factor>>(){}.getType();
        Collection<Factor> selectedFilterFactors = gson.fromJson(selectedFilterFactorsJson, factorsCollectionType);
        selectedFactorGroup.addAll(selectedFilterFactors);

        TranscriptContributions transcriptContributions = transcriptContributionsCalculator.getTranscriptsContribution(geneId, experimentAccession, selectedFactorGroup);

        return gson.toJson(transcriptContributions);
        //return gson.toJson(transcriptsContribution);

    }

}
