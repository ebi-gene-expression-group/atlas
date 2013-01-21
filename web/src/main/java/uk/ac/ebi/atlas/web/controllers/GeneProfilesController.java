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

package uk.ac.ebi.atlas.web.controllers;

import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.FilterParameters;
import uk.ac.ebi.atlas.web.RequestPreferences;

import java.util.SortedSet;
import java.util.TreeSet;

public class GeneProfilesController {
    protected FilterParameters.Builder filterParameterBuilder;

    protected ExperimentsCache experimentsCache;

    public GeneProfilesController(FilterParameters.Builder filterParameterBuilder, ExperimentsCache experimentsCache) {
        this.filterParameterBuilder = filterParameterBuilder;
        this.experimentsCache = experimentsCache;
    }

    protected FilterParameters createFilterParameters(String experimentAccession, RequestPreferences preferences) {

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);

        // check for query factor type present, otherwise use default
        String queryFactorType;
        if (preferences.getQueryFactorType() == null ||
                preferences.getQueryFactorType().trim().length() == 0) {
            queryFactorType = experiment.getDefaultQueryFactorType();
        } else {
            queryFactorType = preferences.getQueryFactorType();
        }

        // check for filter factor values present, otherwise use default
        SortedSet<String> filterFactorValues;
        if (preferences.getFilterFactorValues() == null ||
                preferences.getFilterFactorValues().size() == 0) {
            // TODO: Experiment could also return the URL representation of factor values?
            SortedSet<String> filterDefaultFilterFactorValues = new TreeSet<>();
            for (FactorValue factorValue : experiment.getDefaultFilterFactorValues()) {
                // this simulates how filter factor values arrive from URL as type:value
                filterDefaultFilterFactorValues.add(factorValue.getType() + ":" + factorValue.getValue());
            }
            filterFactorValues = filterDefaultFilterFactorValues;
        } else {
            filterFactorValues = preferences.getFilterFactorValues();
        }

        return filterParameterBuilder.forExperimentAccession(experimentAccession)
                .withCutoff(preferences.getCutoff())
                .withFilterFactorValues(filterFactorValues)
                .withQueryFactorType(queryFactorType)
                .withQueryFactorValues(preferences.getQueryFactorValues())
                .withGeneQuery(preferences.getGeneQuery())
                .build();
    }
}
