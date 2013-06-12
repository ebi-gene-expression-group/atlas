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

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.ContrastProperty;
import uk.ac.ebi.atlas.model.differential.ContrastSummary;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import java.util.Collection;
import java.util.SortedSet;

@Controller
@Scope("request")
public class ContrastSummaryController {

    protected static final String ARRAY_DESIGN = "array design";

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

        DifferentialExperiment differentialExperiment;

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
        }

        ExperimentDesign experimentDesign = differentialExperiment.getExperimentDesign();

        Multimap<String, String> allTestFactorValues = HashMultimap.create();
        Multimap<String, String> allTestSampleValues = HashMultimap.create();
        for (String testAssay : contrast.getTestAssayGroup()) {
            extractAllFactorValues(experimentDesign, allTestFactorValues, testAssay);
            extractAllSampleValues(experimentDesign, allTestSampleValues, testAssay);
            allTestSampleValues.put(ARRAY_DESIGN, experimentDesign.getArrayDesign(testAssay));
        }

        Multimap<String, String> allReferenceFactorValues = HashMultimap.create();
        Multimap<String, String> allReferenceSampleValues = HashMultimap.create();
        for (String referenceAssay : contrast.getReferenceAssayGroup()) {
            extractAllFactorValues(experimentDesign, allReferenceFactorValues, referenceAssay);
            extractAllSampleValues(experimentDesign, allReferenceSampleValues, referenceAssay);
            allReferenceSampleValues.put(ARRAY_DESIGN, experimentDesign.getArrayDesign(referenceAssay));
        }

        ContrastSummary contrastSummary = new ContrastSummary(differentialExperiment.getDescription(), contrast.getDisplayName());

        for (String factorHeader : experimentDesign.getFactorHeaders()) {
            ContrastProperty property = composeContrastProperty(allTestFactorValues, allReferenceFactorValues, factorHeader, ContrastProperty.ContrastPropertyType.FACTOR);
            contrastSummary.addContrastProperty(property);
        }

        // array design row should be sorted within samples category
        SortedSet<String> sampleHeaders = Sets.newTreeSet(experimentDesign.getSampleHeaders());
        sampleHeaders.add(ARRAY_DESIGN);
        for (String sampleHeader : sampleHeaders) {
            ContrastProperty property = composeContrastProperty(allTestSampleValues, allReferenceSampleValues, sampleHeader, ContrastProperty.ContrastPropertyType.SAMPLE);
            contrastSummary.addContrastProperty(property);
        }

        return new Gson().toJson(contrastSummary);
    }

    private ContrastProperty composeContrastProperty(Multimap<String, String> allTestValues, Multimap<String, String> allReferenceValues,
                                                     String header, ContrastProperty.ContrastPropertyType contrastPropertyType) {
        Collection<String> testValues = allTestValues.get(header);
        Collection<String> referenceValues = allReferenceValues.get(header);
        ContrastProperty property = new ContrastProperty(header,
                Joiner.on(",").skipNulls().join(testValues),
                Joiner.on(",").skipNulls().join(referenceValues),
                contrastPropertyType);
        return property;
    }

    private void extractAllFactorValues(ExperimentDesign experimentDesign, Multimap<String, String> values, String runOrAssay) {
        for (String factorHeader : experimentDesign.getFactorHeaders()) {
            String factorValue = experimentDesign.getFactorValue(runOrAssay, factorHeader);
            values.put(factorHeader, factorValue);
        }
    }

    private void extractAllSampleValues(ExperimentDesign experimentDesign, Multimap<String, String> values, String runOrAssay) {
        for (String sampleHeader : experimentDesign.getSampleHeaders()) {
            String sampleValue = experimentDesign.getSampleValue(runOrAssay, sampleHeader);
            values.put(sampleHeader, sampleValue);
        }
    }
}
