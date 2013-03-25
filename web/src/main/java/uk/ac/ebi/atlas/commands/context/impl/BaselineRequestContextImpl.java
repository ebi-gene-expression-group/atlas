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

package uk.ac.ebi.atlas.commands.context.impl;

import com.google.common.base.Objects;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Named;
import java.util.Set;

@Named
@Scope("request")
public class BaselineRequestContextImpl extends RequestContextImpl<Factor> implements BaselineRequestContext {

    private Set<Factor> selectedFilterFactors;

    public BaselineRequestContextImpl() {
    }

    @Override
    public Set<Factor> getSelectedFilterFactors() {
        return selectedFilterFactors;
    }

    @Override
    public String getQueryFactorType() {
        return requestPreferences.getQueryFactorType();
    }
    public void setSelectedFilterFactors(Set<Factor> selectedFilterFactors) {
        this.selectedFilterFactors = selectedFilterFactors;
    }

    @Override
    public String toString(){
        return Objects.toStringHelper(this.getClass())
                .add("selectedFilterFactors", selectedFilterFactors).toString();
    }


}