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
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.ContrastProperty;
import uk.ac.ebi.atlas.model.differential.ContrastSummary;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;

@Controller
@Scope("request")
public class ContrastSummaryController {

    private ApplicationProperties applicationProperties;

    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    private MicroarrayExperimentsCache microarrayExperimentsCache;

    @Inject
    public ContrastSummaryController(ApplicationProperties applicationProperties,
                                     RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                                     MicroarrayExperimentsCache microarrayExperimentsCache) {
        this.applicationProperties = applicationProperties;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
    }

    @RequestMapping(value = "/rest/contrast-summary", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTooltipContent(@RequestParam(value = "experimentAccession") String experimentAccession,
                                    @RequestParam(value = "contrastId") String contrastId) {

        /*DifferentialExperiment differentialExperiment;

        if (applicationProperties.getDifferentialExperimentsIdentifiers().contains(experimentAccession)) {
            differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);
        } else if (applicationProperties.getMicroarrayExperimentsIdentifiers().contains(experimentAccession)) {
            differentialExperiment = microarrayExperimentsCache.getExperiment(experimentAccession);
        } else {
            throw new IllegalStateException("Experiment for accession " + experimentAccession + " not found.");
        }

        Contrast contrast = differentialExperiment.getContrast(contrastId);
        if (contrast == null) {
            throw new IllegalStateException("No contract with id " + contrastId + " found.");
        }*/

        //this is only mock data
        String experimentDescription = "RNA-seq of mouse spinal cord expressing wild type human TDP-43";
        String contrastName = "genotype:'expressing human TDP-43' vs 'non transgenic'";
        ContrastProperty propertyOne = new ContrastProperty("genotype", "expressing human TDP-43", "non transgenic");
        ContrastProperty propertyTwo = new ContrastProperty("age", "21 days", "21 days");
        ContrastProperty propertyThree = new ContrastProperty("species", "fiorelli", null);
        //

        ContrastSummary contrastSummary = new ContrastSummary(experimentDescription, contrastName);

        contrastSummary.addContrastProperty(propertyOne);
        contrastSummary.addContrastProperty(propertyTwo);
        contrastSummary.addContrastProperty(propertyThree);

        return new Gson().toJson(contrastSummary);
    }
}
