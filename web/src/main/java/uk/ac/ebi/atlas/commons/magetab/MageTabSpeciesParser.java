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

package uk.ac.ebi.atlas.commons.magetab;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class MageTabSpeciesParser implements MageTabSpeciesParserBuilder {

    private String experimentAccession;

    private MAGETABInvestigation investigation;

    private String idfUrlTemplate;

    private String idfPathTemplate;

    @Inject
    public void setIdfUrlTemplate(@Value("#{configuration['experiment.magetab.idf.url.template']}") String idfUrlTemplate) {
        this.idfUrlTemplate = idfUrlTemplate;
    }

    @Inject
    public void setIdfPathTemplate(@Value("#{configuration['experiment.magetab.idf.path.template']}") String idfPathTemplate) {
        this.idfPathTemplate = idfPathTemplate;
    }


    public MageTabSpeciesParser forExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    public MageTabSpeciesParser build() throws IOException, ParseException {
        checkState(experimentAccession != null, "Please invoke forExperimentAccession method to initialize the builder !");

        investigation = MageTabLimpopoUtils.parseInvestigation(experimentAccession, idfPathTemplate, idfUrlTemplate);

        return this;
    }

    public Set<String> extractSpecies() {
        return MageTabLimpopoUtils.extractSpeciesFromSDRF(investigation);
    }

}