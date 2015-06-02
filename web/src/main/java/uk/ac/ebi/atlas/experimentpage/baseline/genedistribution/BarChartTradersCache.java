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

package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.cache.LoadingCache;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named("barChartTraders")
@Scope("singleton")
public class BarChartTradersCache {

    private static final Logger LOGGER = Logger.getLogger(BarChartTradersCache.class);

    private LoadingCache<String, BarChartTrader> barchartTraders;

    @Inject
    // this is the name of the implementation being injected, required because LoadingCache is an interface
    public BarChartTradersCache(@Qualifier("barChartTradersLoadingCache") LoadingCache<String, BarChartTrader> barchartTraders) {
        this.barchartTraders = barchartTraders;
    }

    public BarChartTrader getBarchartTrader(String experimentAccession) {
        try {
            return barchartTraders.get(experimentAccession);
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Exception while loading histogram data from file: " + e.getMessage(), e.getCause());
        }
    }

}
