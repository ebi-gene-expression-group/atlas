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

package uk.ac.ebi.atlas.commands.context;

import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.commands.context.impl.RequestContextImpl;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;


public abstract class RequestContextBuilder<R extends RequestContextImpl, E extends Experiment> implements Serializable {

    protected RequestPreferences preferences;

    private R requestContext;
    private E experiment;

    @Inject
    private void setRequestContext(R requestContext){

        this.requestContext = requestContext;
    }

    public RequestContextBuilder forExperiment(E experiment) {
        this.experiment = experiment;
        return this;
    }

    public RequestContextBuilder withPreferences(RequestPreferences preferences) {
        this.preferences = preferences;
        return this;
    }

    public abstract void setExtraFields();

    public abstract Set getQueryFactors();



    public R build() {

        return requestContext;
    }


    Set<String> getQueryFactorValues() {
        if (CollectionUtils.isNotEmpty(preferences.getQueryFactorValues())) {
            return preferences.getQueryFactorValues();
        } else {
            return Collections.EMPTY_SET;
        }
    }


}
