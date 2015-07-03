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

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.atlas.experimentpage.baseline.genedistribution.BarChartTrader;
import uk.ac.ebi.atlas.experimentpage.baseline.genedistribution.BarChartTradersCacheLoader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ProteomicsBaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.loader.*;

import javax.inject.Inject;

@Configuration
public class CacheConfiguration {

    @Bean(name="baselineExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, BaselineExperiment> baselineExperimentsCache(BaselineExperimentsCacheLoader cacheLoader){

        return CacheBuilder.newBuilder().build(cacheLoader);

    }

    @Bean(name="proteomicsBaselineExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, ProteomicsBaselineExperiment> proteomicsBaselineExperimentsCache(ProteomicsBaselineExperimentsCacheLoader cacheLoader){

        return CacheBuilder.newBuilder().build(cacheLoader);

    }


    @Bean(name="differentialExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, DifferentialExperiment> differentialExperimentsCache(DifferentialExperimentsCacheLoader cacheLoader){

        return CacheBuilder.newBuilder().build(cacheLoader);

    }

    @Bean(name="microarrayExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, MicroarrayExperiment> microarrayExperimentsCache(MicroarrayExperimentsCacheLoader cacheLoader){

        return CacheBuilder.newBuilder().build(cacheLoader);

    }

    @Bean(name="barChartTradersLoadingCache")
    @Inject
    public LoadingCache<String, BarChartTrader> barChartTradersCache(BarChartTradersCacheLoader barChartTradersCacheLoader) {

        return CacheBuilder.newBuilder().build(barChartTradersCacheLoader);

    }

    @Bean(name="publicExperimentTypesLoadingCache")
    @Inject
    public LoadingCache<String, ExperimentType> experimentTypesCache(PublicExperimentTypesCacheLoader cacheLoader) {

        return CacheBuilder.newBuilder().build(cacheLoader);

    }
}