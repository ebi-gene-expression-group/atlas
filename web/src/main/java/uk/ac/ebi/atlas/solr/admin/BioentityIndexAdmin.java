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

package uk.ac.ebi.atlas.solr.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.admin.index.BioentityIndex;
import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

@Named
@Scope("prototype")
public class BioentityIndexAdmin {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityIndexAdmin.class);

    private BioentityIndexMonitor bioentityIndexMonitor;
    private String bioentityPropertiesDirectory;
    private ExecutorService executorService;

    @Value("#{configuration['solr.data.location']}")
    private String solrDataLocation;

    private BioentityIndex bioentityIndex;

    // Injecting the ExecutorService allows us to inject a sameThreadExecutor in our unit tests,
    // so that verifications can be executed on the runnable task being started
    @Inject
    BioentityIndexAdmin(BioentityIndex bioentityIndex, BioentityIndexMonitor bioentityIndexMonitor,
                        @Value("#{configuration['bioentity.properties']}") String bioentityPropertiesDirectory,
                        @Named("singleThreadExecutorService") ExecutorService executorService){

        this.bioentityIndex = bioentityIndex;
        this.bioentityIndexMonitor = bioentityIndexMonitor;
        this.bioentityPropertiesDirectory = bioentityPropertiesDirectory;
        this.executorService = executorService;
    }

    public void rebuildIndex() {
        final Path bioentityPropertiesPath = Paths.get(bioentityPropertiesDirectory);

        if (bioentityIndexMonitor.start()){

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        bioentityIndex.deleteAll();
                        bioentityIndex.indexAll(Files.newDirectoryStream(bioentityPropertiesPath));
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                        bioentityIndexMonitor.failed(e);
                    }
                }
            });
            executorService.shutdown();
        }
    }

}
