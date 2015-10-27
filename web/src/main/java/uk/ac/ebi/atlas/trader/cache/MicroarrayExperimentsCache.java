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
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class MicroarrayExperimentsCache implements ExperimentsCache<MicroarrayExperiment> {

    private static final Logger LOGGER = LogManager.getLogger(MicroarrayExperimentsCache.class);

    private LoadingCache<String, MicroarrayExperiment> experiments;

    @Inject
    @Named("microarrayExperimentsLoadingCache")
    //this is the name of the implementation being injected, required because LoadingCache is an interface
    public MicroarrayExperimentsCache(LoadingCache<String, MicroarrayExperiment> experiments) {
        this.experiments = experiments;
    }

    @Override
    public MicroarrayExperiment getExperiment(String experimentAccession) {
        try {

            return experiments.get(experimentAccession);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(String.format("Exception while loading experiment %s: %s", experimentAccession, e.getMessage()), e.getCause());
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
