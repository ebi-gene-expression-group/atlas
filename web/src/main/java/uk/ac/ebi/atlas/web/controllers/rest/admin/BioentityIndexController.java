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

package uk.ac.ebi.atlas.web.controllers.rest.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.solr.admin.BioentityIndexAdmin;
import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;

import javax.inject.Inject;

@Controller
@Scope("request")
@RequestMapping(value = "/admin")
public class BioentityIndexController {
    private static final Logger LOGGER = LogManager.getLogger(BioentityIndexController.class);

    private BioentityIndexAdmin bioentityIndexAdmin;
    private BioentityIndexMonitor bioentityIndexMonitor;

    @Inject
    BioentityIndexController(BioentityIndexAdmin bioentityIndexAdmin, BioentityIndexMonitor bioentityIndexMonitor){
        this.bioentityIndexAdmin = bioentityIndexAdmin;
        this.bioentityIndexMonitor = bioentityIndexMonitor;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        bioentityIndexMonitor.failed(e);

        LOGGER.error(e.getMessage(),e);
        return e.getMessage();
    }

    @RequestMapping(value = "/buildIndex/status", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String buildStatus() {
        return bioentityIndexMonitor.reportProgress();
    }


    @RequestMapping(value = "/buildIndex",  produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String build() {

        bioentityIndexAdmin.rebuildIndex();

        return bioentityIndexMonitor.reportProgress();

    }
}