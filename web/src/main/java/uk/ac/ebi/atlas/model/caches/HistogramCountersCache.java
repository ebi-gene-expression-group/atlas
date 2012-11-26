/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model.caches;

import com.google.common.cache.LoadingCache;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.score.HistogramCounter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named("histogramCounters")
@Scope("singleton")
public class HistogramCountersCache {

    private static final Logger logger = Logger.getLogger(HistogramCountersCache.class);

    private LoadingCache<String, HistogramCounter> histogramCounters;

    @Inject
    @Named("histogramCountersCache")
    public HistogramCountersCache(LoadingCache<String, HistogramCounter> histogramCounters) {
        this.histogramCounters = histogramCounters;
    }

    public HistogramCounter getExperimentRuns(String experimentAccession) {
        try {

            return histogramCounters.get(experimentAccession);

        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Exception while loading histogram data from file: " + e.getMessage(),
                    e.getCause());
        }
    }

}
