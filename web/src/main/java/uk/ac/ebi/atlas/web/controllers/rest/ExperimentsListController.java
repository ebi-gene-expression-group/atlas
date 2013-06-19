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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.utils.ExperimentInfo;
import uk.ac.ebi.atlas.utils.ExperimentInfoListBuilder;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;


@Controller
@Scope("request")
public class ExperimentsListController {

    private ExperimentInfoListBuilder experimentInfoListBuilder;

    @Inject
    public ExperimentsListController(ExperimentInfoListBuilder experimentInfoListBuilder) {
        this.experimentInfoListBuilder = experimentInfoListBuilder;
    }

    @RequestMapping(value = "/json/experiments", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getExperimentsList() {

        List<ExperimentInfo> experimentInfos = experimentInfoListBuilder.build();
        // this is required for the initial sorting order on the experiments page, which the IT relies on
        Collections.sort(experimentInfos);
        ExperimentInfoWrapper experimentInfoWrapper = new ExperimentInfoWrapper(experimentInfos);

        Gson gson = new Gson();
        return gson.toJson(experimentInfoWrapper);
    }

    /* This is a wrapper class used via Gson to produce the right JSON input for DataTables. */
    public class ExperimentInfoWrapper {

        private List<ExperimentInfo> aaData;

        public ExperimentInfoWrapper(List<ExperimentInfo> list) {
            this.aaData = list;
        }

        public List<ExperimentInfo> getAaData() {
            return aaData;
        }
    }

}