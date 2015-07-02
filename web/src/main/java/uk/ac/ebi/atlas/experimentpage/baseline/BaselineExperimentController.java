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

package uk.ac.ebi.atlas.experimentpage.baseline;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import java.util.Set;

public abstract class BaselineExperimentController {

    private BaselineRequestContextBuilder baselineRequestContextBuilder;
    private FilterFactorsConverter filterFactorsConverter;

    protected BaselineExperimentController(BaselineRequestContextBuilder baselineRequestContextBuilder,
                                           FilterFactorsConverter filterFactorsConverter) {
        this.baselineRequestContextBuilder = baselineRequestContextBuilder;
        this.filterFactorsConverter = filterFactorsConverter;
    }

    protected void setPreferenceDefaults(BaselineRequestPreferences preferences, BaselineExperiment baselineExperiment) {

        if (StringUtils.isBlank(preferences.getQueryFactorType())) {
            preferences.setQueryFactorType(baselineExperiment.getExperimentalFactors().getDefaultQueryFactorType());
        }

        if (StringUtils.isBlank(preferences.getSerializedFilterFactors())) {
            preferences.setSerializedFilterFactors(filterFactorsConverter.serialize(baselineExperiment.getExperimentalFactors().getDefaultFilterFactors()));
        }

        if (allFactorsInSliceSelected(preferences, baselineExperiment)) {
            preferences.setSpecific(false);
        }

    }

    private boolean allFactorsInSliceSelected(BaselineRequestPreferences preferences, BaselineExperiment experiment) {
        Set<Factor> selectedFilterFactors = filterFactorsConverter.deserialize(preferences.getSerializedFilterFactors());

        Set<Factor> allFactorsInSlice;
        if(experiment.getExperimentalFactors().getAllFactorsOrderedByXML() != null &&
                !experiment.getExperimentalFactors().getAllFactorsOrderedByXML().isEmpty()) {
            allFactorsInSlice = experiment.getExperimentalFactors().getComplementFactorsByXML(selectedFilterFactors);
        } else {
            allFactorsInSlice = experiment.getExperimentalFactors().getComplementFactors(selectedFilterFactors);
        }

        return (preferences.getQueryFactorValues().size() == allFactorsInSlice.size());
    }

    protected BaselineRequestContext buildRequestContext(BaselineExperiment experiment, BaselineRequestPreferences preferences) {

        return baselineRequestContextBuilder.forExperiment(experiment)
                                        .withPreferences(preferences)
                                        .build();
    }

    @ExceptionHandler(value = {SolrException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView InternalServerHandleException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }

}
