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

package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Objects;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamOptions;

import javax.inject.Named;

@Named
@Scope("request")
public class MicroarrayRequestContext extends DifferentialRequestContext<MicroarrayExperiment> implements MicroarrayProfileStreamOptions {

    private String arrayDesignAccession;

    public void setArrayDesignAccession(String arrayDesignAccession) {
        this.arrayDesignAccession = arrayDesignAccession;
    }

    public String getArrayDesignAccession() {
        return arrayDesignAccession;
    }

    @Override
    public Iterable<String> getArrayDesignAccessions() {
        return getExperiment().getArrayDesignAccessions();
    }

    @Override
    public String toString(){
        return Objects.toStringHelper(getClass())
                .addValue(super.toString())
                .add("arrayDesignAccession", arrayDesignAccession).toString();
    }

}