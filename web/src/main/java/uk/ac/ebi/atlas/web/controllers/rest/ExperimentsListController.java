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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;


@Controller
@Scope("request")
public class ExperimentsListController {

    private ApplicationProperties applicationProperties;

    private BaselineExperimentsCache baselineExperimentsCache;

    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    private MicroarrayExperimentsCache microarrayExperimentsCache;

    @Inject
    public ExperimentsListController(ApplicationProperties applicationProperties,
                                     BaselineExperimentsCache baselineExperimentsCache,
                                     RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                                     MicroarrayExperimentsCache microarrayExperimentsCache) {
        this.applicationProperties = applicationProperties;
        this.baselineExperimentsCache = baselineExperimentsCache;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
    }

    @RequestMapping(value = "/json/experiments", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getExperimentsList() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();
        experimentInfos.addAll(extractBaselineExperiments());
        experimentInfos.addAll(extractRnaSeqDiffExperiments());
        experimentInfos.addAll(extractMicrorarryExperiments());

        ExperimentInfoWrapper experimentInfoWrapper = new ExperimentInfoWrapper(experimentInfos);

        Gson gson = new Gson();
        return gson.toJson(experimentInfoWrapper);
    }

    protected List<ExperimentInfo> extractMicrorarryExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : applicationProperties.getMicroarrayExperimentsIdentifiers()) {
            MicroarrayExperiment experiment = microarrayExperimentsCache.getExperiment(experimentAccession);

            ExperimentInfo experimentInfo = new ExperimentInfo();
            experimentInfo.setExperimentAccession(experimentAccession);
            experimentInfo.setExperimentDescription(experiment.getDescription());
            experimentInfo.setSpecies(experiment.getSpecies());
            experimentInfo.setExperimentType(experiment.getType());
            experimentInfo.setNumberOfAssays(experiment.getAssayAccessions().size());
            experimentInfo.setNumberOfContrasts(experiment.getContrastIds().size());
            experimentInfo.setArrayDesigns(experiment.getArrayDesignAccessions());

            experimentInfos.add(experimentInfo);
        }

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractRnaSeqDiffExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : applicationProperties.getDifferentialExperimentsIdentifiers()) {
            DifferentialExperiment experiment = rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);

            ExperimentInfo experimentInfo = new ExperimentInfo();
            experimentInfo.setExperimentAccession(experimentAccession);
            experimentInfo.setExperimentDescription(experiment.getDescription());
            experimentInfo.setSpecies(experiment.getSpecies());
            experimentInfo.setExperimentType(experiment.getType());
            experimentInfo.setNumberOfAssays(experiment.getAssayAccessions().size());
            experimentInfo.setNumberOfContrasts(experiment.getContrastIds().size());

            experimentInfos.add(experimentInfo);
        }

        return experimentInfos;
    }


    protected List<ExperimentInfo> extractBaselineExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : applicationProperties.getBaselineExperimentsIdentifiers()) {
            BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);

            ExperimentInfo experimentInfo = new ExperimentInfo();
            experimentInfo.setExperimentAccession(experimentAccession);
            experimentInfo.setExperimentDescription(experiment.getDescription());
            experimentInfo.setSpecies(experiment.getSpecies());
            experimentInfo.setExperimentType(experiment.getType());
            experimentInfo.setExperimentalFactors(extractExperimentalFactors(experiment.getExperimentalFactors()));

            experimentInfos.add(experimentInfo);
        }

        return experimentInfos;
    }

    protected Set<String> extractExperimentalFactors(ExperimentalFactors experimentalFactors) {

        Set<String> allFactorNames = Sets.newHashSet();

        for (Factor factor : experimentalFactors.getAllFactors()) {
            String factorName = experimentalFactors.getFactorName(factor.getType());
            allFactorNames.add(factorName);
        }

        return allFactorNames;
    }

    /* This is a wrapper class used via Gson to produce the right JSON input for DataTables. */
    private class ExperimentInfoWrapper {

        private List<ExperimentInfo> aaData;

        public ExperimentInfoWrapper(List<ExperimentInfo> list) {
            this.aaData = list;
        }
    }

}