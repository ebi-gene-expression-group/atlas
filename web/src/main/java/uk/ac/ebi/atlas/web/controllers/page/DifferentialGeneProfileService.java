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

package uk.ac.ebi.atlas.web.controllers.page;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.RankRnaSeqProfilesCommand;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.TreeSet;

@Named("differentialGeneProfileService")
@Scope("request")
public class DifferentialGeneProfileService {

    private ApplicationProperties applicationProperties;

    private RankRnaSeqProfilesCommand rankRnaSeqProfilesCommand;

    private RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder;

    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    @Inject
    public DifferentialGeneProfileService(ApplicationProperties applicationProperties,
                                          RankRnaSeqProfilesCommand rankRnaSeqProfilesCommand,
                                          RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder,
                                          RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache) {
        this.applicationProperties = applicationProperties;
        this.rankRnaSeqProfilesCommand = rankRnaSeqProfilesCommand;
        this.rnaSeqRequestContextBuilder = rnaSeqRequestContextBuilder;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
    }

    public DifferentialProfilesList getDifferentialProfilesList(String identifier, String species) throws GenesNotFoundException {

        DifferentialProfilesList differentialProfilesList = null;

        for (String experimentAccession : applicationProperties.getDifferentialExperimentsIdentifiers()) {
            if (differentialProfilesList == null) {
                differentialProfilesList = retrieveDifferentialProfileForExperiment(experimentAccession, identifier, species);
            } else {
                differentialProfilesList.addAll(retrieveDifferentialProfileForExperiment(experimentAccession, identifier, species));
            }
        }

        return differentialProfilesList;
    }

    protected DifferentialProfilesList retrieveDifferentialProfileForExperiment(String experimentAccession, String identifier, String species) throws GenesNotFoundException {

        DifferentialRequestPreferences differentialRequestPreferences = new DifferentialRequestPreferences();
        differentialRequestPreferences.setRegulation(Regulation.UP_DOWN);
        differentialRequestPreferences.setGeneQuery(identifier.toLowerCase());
        differentialRequestPreferences.setExactMatch(true);
        differentialRequestPreferences.setGeneSetMatch(false);
        differentialRequestPreferences.setSpecific(false);
        differentialRequestPreferences.setQueryFactorValues(new TreeSet<String>());


        DifferentialExperiment differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);

        if (differentialExperiment.getSpecies().contains(species)) {
            rnaSeqRequestContextBuilder.withPreferences(differentialRequestPreferences).forExperiment(differentialExperiment).build();
            return rankRnaSeqProfilesCommand.execute(experimentAccession);
        }

        return new DifferentialProfilesList(Collections.emptyList());
    }
}