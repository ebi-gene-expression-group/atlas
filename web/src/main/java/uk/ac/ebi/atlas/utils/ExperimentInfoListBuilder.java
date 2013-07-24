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

package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Scope("prototype")
public class ExperimentInfoListBuilder {

    private BaselineExperimentsCache baselineExperimentsCache;

    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    private MicroarrayExperimentsCache microarrayExperimentsCache;

    private ExperimentTrader experimentTrader;

    @Inject
    public ExperimentInfoListBuilder(ExperimentTrader experimentTrader,
                                     BaselineExperimentsCache baselineExperimentsCache,
                                     RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                                     MicroarrayExperimentsCache microarrayExperimentsCache) {
        this.experimentTrader = experimentTrader;
        this.baselineExperimentsCache = baselineExperimentsCache;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
    }

    public List<ExperimentInfo> build() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();
        experimentInfos.addAll(extractBaselineExperiments());
        experimentInfos.addAll(extractRnaSeqDiffExperiments());
        experimentInfos.addAll(extractMicrorarryExperiments());

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractMicrorarryExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : experimentTrader.getMicroarrayExperimentAccessions()) {
            MicroarrayExperiment experiment = microarrayExperimentsCache.getExperiment(experimentAccession);

            ExperimentInfo experimentInfo = extractBasicExperimentInfo(experiment);
            experimentInfo.setNumberOfAssays(experiment.getAssayAccessions().size());
            experimentInfo.setNumberOfContrasts(experiment.getContrastIds().size());
            experimentInfo.setArrayDesigns(experiment.getArrayDesignAccessions());

            experimentInfos.add(experimentInfo);
        }

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractRnaSeqDiffExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : experimentTrader.getDifferentialExperimentAccessions()) {
            DifferentialExperiment experiment = rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);

            ExperimentInfo experimentInfo = extractBasicExperimentInfo(experiment);
            experimentInfo.setNumberOfAssays(experiment.getAssayAccessions().size());
            experimentInfo.setNumberOfContrasts(experiment.getContrastIds().size());

            experimentInfos.add(experimentInfo);
        }

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractBaselineExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : experimentTrader.getBaselineExperimentAccessions()) {
            BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);

            ExperimentInfo experimentInfo = extractBasicExperimentInfo(experiment);
            experimentInfo.setNumberOfAssays(experiment.getExperimentRunAccessions().size());

            experimentInfos.add(experimentInfo);
        }

        return experimentInfos;
    }

    protected ExperimentInfo extractBasicExperimentInfo(Experiment experiment) {
        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        ExperimentInfo experimentInfo = new ExperimentInfo();
        experimentInfo.setExperimentAccession(experiment.getAccession());
        experimentInfo.setExperimentDescription(experiment.getDescription());
        experimentInfo.setSpecies(experiment.getSpecies());
        //ToDo: there are only types (BASELINE, DIFFERENTIAL, MICROARRAY)
        experimentInfo.setExperimentType(experiment.getType().getParent());
        experimentInfo.setExperimentalFactors(experimentDesign.getFactorHeaders());

        return experimentInfo;
    }

}