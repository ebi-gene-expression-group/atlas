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

package uk.ac.ebi.atlas.model.barcharts;

/**
 * This class is a factory of BarChartTrader Builders, to be used only in cases where new instances of builder need to be created from within a "singleton" object, like a CacheLoader.
 * It is injected using spring lookup-method (application-context.xml). In Spring 3.0 no equivalent annotation has yet been implemented for this feature.
 * If you need to build a new BarChartTrader in the context of another class that has scope "prototype" you should directly inject BarChartTrader.Builder and not use this factory.
 */
public abstract class BarChartTraderBuilderFactory {

    public BarChartTrader.Builder with(String experimentAccession) {
        BarChartTrader.Builder barChartTraderBuilder = createNew();
        return barChartTraderBuilder.forExperiment(experimentAccession);
    }

    protected abstract BarChartTrader.Builder createNew();
}