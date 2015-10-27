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

package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.ProteomicsBaselineExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
@Scope("singleton")
public class ProteomicsBaselineExperimentsCache implements ExperimentsCache<ProteomicsBaselineExperiment> {

    private static final Logger LOGGER = LogManager.getLogger(ProteomicsBaselineExperimentsCache.class);

    private LoadingCache<String, ProteomicsBaselineExperiment> experiments;

    @Inject
    @Named("proteomicsBaselineExperimentsLoadingCache")
    //this is the name of the implementation being injected, required because LoadingCache is an interface
    public ProteomicsBaselineExperimentsCache(LoadingCache<String, ProteomicsBaselineExperiment> experiments) {
        this.experiments = experiments;
    }

    /*
     * @throws IllegalStateException if an error was thrown while loading the experiment, or the experiment
     * doesn't exist
     */
    @Override
    public ProteomicsBaselineExperiment getExperiment(String experimentAccession) {
        try {

            return experiments.get(experimentAccession);

        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Exception while loading MAGE TAB file: " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public void evictExperiment(String experimentAccession) {
        experiments.invalidate(experimentAccession);
    }

    @Override
    public void evictAll() {
        experiments.invalidateAll();
    }


}
