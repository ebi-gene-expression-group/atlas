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

package uk.ac.ebi.atlas.model.baseline.barcharts;

import com.google.common.cache.CacheLoader;

//Be aware that this is a spring managed singleton object and uses the lookup-method injection to get a new instance of BarChartTraderBuilder everytime the load method is invoked
//The reason to do so is that Guava CacheBuilder, that is the one using this class, is not spring managed.
public abstract class BarChartTradersCacheLoader extends CacheLoader<String, BarChartTrader> {

    @Override
    public BarChartTrader load(String experimentAccession) {

        return createBarChartTraderBuilder().forExperiment(experimentAccession).create();

    }

    public abstract BarChartTraderBuilder createBarChartTraderBuilder();

}
