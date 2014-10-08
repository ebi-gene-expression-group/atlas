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

package uk.ac.ebi.atlas.transcript;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

@Controller
@Scope("request")
public class RankedGeneTranscriptsController {

    private final TranscriptContributionsCalculator transcriptContributionsCalculator;

    private final FilterFactorsConverter filterFactorsConverter;

    @Inject
    public RankedGeneTranscriptsController(TranscriptContributionsCalculator transcriptContributionsCalculator, FilterFactorsConverter filterFactorsConverter) {
        this.transcriptContributionsCalculator = transcriptContributionsCalculator;
        this.filterFactorsConverter = filterFactorsConverter;
    }


    @RequestMapping(value = "/json/transcripts/{experimentAccession}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getRankedTranscripts(@PathVariable String experimentAccession,
                                       @RequestParam("geneId") String geneId,
                                       @RequestParam("factorType") String factorType,
                                       @RequestParam("factorValue") String factorValue,
                                       @RequestParam(value = "serializedFilterFactors", required=false) String serializedFilterFactors) {

        Factor selectedQueryFactor = new Factor(factorType, factorValue);
        FactorSet selectedFactorGroup = new FactorSet().add(selectedQueryFactor);

        Collection<Factor> selectedFilterFactors = extractFilterFactors(serializedFilterFactors);

        selectedFactorGroup.addAll(selectedFilterFactors);

        TranscriptContributions transcriptContributions = transcriptContributionsCalculator.getTranscriptsContribution(geneId, experimentAccession, selectedFactorGroup);

        return new Gson().toJson(transcriptContributions, TranscriptContributions.class);

    }

    Collection<Factor> extractFilterFactors(String serializedFilterFactors)  {

        if (StringUtils.isEmpty(serializedFilterFactors)) {
            return Collections.emptyList();
        }

        return filterFactorsConverter.deserialize(serializedFilterFactors);
    }

}
