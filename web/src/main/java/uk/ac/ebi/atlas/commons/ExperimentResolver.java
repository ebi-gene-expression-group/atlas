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

package uk.ac.ebi.atlas.commons;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneindex.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Named()
@Scope("singleton")
public class ExperimentResolver {
    private Properties speciesToExperimentProperties;

    private SolrQueryService solrQueryService;

    @Inject
    public ExperimentResolver(@Named("speciesToExperimentPropertyFile") Properties speciesToExperimentProperties, SolrQueryService solrQueryService) {
        this.speciesToExperimentProperties = speciesToExperimentProperties;
        this.solrQueryService = solrQueryService;
    }

    public String getExperimentAccessionByUniprotAccession(String accession) {
        String species = solrQueryService.getSpeciesForPropertyValue(accession);
        return speciesToExperimentProperties.getProperty(species.replace(" ", "_"));
    }
}
