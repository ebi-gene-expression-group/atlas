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
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import java.util.List;


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

        ExperimentInfo experimentInfo = new ExperimentInfo();
        experimentInfo.setExperimentAccession("accession");

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();
        experimentInfos.add(experimentInfo);

        ExperimentInfoList experimentInfoList = new ExperimentInfoList(experimentInfos);

        Gson gson = new Gson();
        return gson.toJson(experimentInfoList);
    }

    private class ExperimentInfoList {

        private List<ExperimentInfo> aaData;

        public ExperimentInfoList(List<ExperimentInfo> list) {
            this.aaData = list;
        }

        public List<ExperimentInfo> getAaData() {
            return aaData;
        }
    }

}