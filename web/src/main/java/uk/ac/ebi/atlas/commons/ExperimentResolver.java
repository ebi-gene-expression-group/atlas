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

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneindex.GeneQueryTokenizer;
import uk.ac.ebi.atlas.geneindex.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Properties;

@Named()
@Scope("singleton")
public class ExperimentResolver {
    private Properties speciesToExperimentProperties;

    private SolrQueryService solrQueryService;

    private GeneQueryTokenizer geneQueryTokenizer;

    @Inject
    public ExperimentResolver(@Named("speciesToExperimentPropertyFile") Properties speciesToExperimentProperties,
                              SolrQueryService solrQueryService, GeneQueryTokenizer geneQueryTokenizer) {
        this.speciesToExperimentProperties = speciesToExperimentProperties;
        this.solrQueryService = solrQueryService;
        this.geneQueryTokenizer = geneQueryTokenizer;
    }

    public String getExperimentAccessionByProperty(String value, String type) {
        String species = getSpecies(value, type);
        return speciesToExperimentProperties.getProperty(species.replace(" ", "_"));
    }

    public String getExperimentAccessionBySpecies(String species) {
        return speciesToExperimentProperties.getProperty(species.replace(" ", "_"));
    }

    public String getSpecies(String value, String type) {
        List<String> partsOfGeneQuery = geneQueryTokenizer.split(value);
        String firstIdentifier = partsOfGeneQuery.get(0);
        String species = StringUtils.isEmpty(type) ? solrQueryService.getSpeciesForPropertyValue(firstIdentifier) : solrQueryService.getSpeciesForPropertyValue(firstIdentifier, type);
        return species;
    }

}
