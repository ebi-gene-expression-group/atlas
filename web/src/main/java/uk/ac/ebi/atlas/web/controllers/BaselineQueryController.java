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

package uk.ac.ebi.atlas.web.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.RequestContext;
import uk.ac.ebi.atlas.commands.RequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.RequestPreferences;

@Scope("request")
public abstract class BaselineQueryController {

    private RequestContextBuilder requestContextBuilder;
    private FilterFactorsConverter filterFactorsConverter;

    public BaselineQueryController(RequestContextBuilder requestContextBuilder,
                                   FilterFactorsConverter filterFactorsConverter) {
        this.requestContextBuilder = requestContextBuilder;
        this.filterFactorsConverter = filterFactorsConverter;
    }

    protected void initPreferences(RequestPreferences preferences, BaselineExperiment baselineExperiment) {

        if (StringUtils.isBlank(preferences.getQueryFactorType())) {
            preferences.setQueryFactorType(baselineExperiment.getDefaultQueryFactorType());
        }
        if (StringUtils.isBlank(preferences.getSerializedFilterFactors())) {
            preferences.setSerializedFilterFactors(filterFactorsConverter.serialize(baselineExperiment.getDefaultFilterFactors()));
        }
    }

    protected RequestContext initRequestContext(String experimentAccession, RequestPreferences preferences) {

        return requestContextBuilder.forExperiment(experimentAccession)
                                        .withPreferences(preferences)
                                        .build();
    }
}
