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

package uk.ac.ebi.atlas.solr.index;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

//@Controller
//@Scope("singleton")
public class SolrIndexController {
    private static final Logger LOGGER = Logger.getLogger(SolrIndexBuilder.class);

    private SolrIndexBuilder builder;

    private AtomicBoolean buildInProgress = new AtomicBoolean(false);

    private String status;

//    @Inject
    SolrIndexController(SolrIndexBuilder builder){
        this.builder = builder;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        LOGGER.error(e.getMessage(),e);
        return e.getMessage();
    }

    @RequestMapping(value = "/buildIndex/status")
    @ResponseBody
    public String buildStatus() {
        return status != null ? status : "no build started since last application deployment";
    }


    @RequestMapping(value = "/buildIndex")
    @ResponseBody
    public String build() {

        if (buildInProgress.compareAndSet(false, true)){

            status = "Index build starting";

            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    builder.build(new IndexAllCommand(), new IndexBuilderService.ProgressUpdater() {
                        public void update(String progress) {
                            status = progress;
                            LOGGER.info("<update> index build status: " + status);
                        }
                    });
                    status = "Index build completed";
                }
            });

        }

        return status;

   }
}