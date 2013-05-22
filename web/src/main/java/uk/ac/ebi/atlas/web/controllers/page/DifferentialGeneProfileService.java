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

import com.google.common.collect.Sets;
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

    public DifferentialProfilesList getDifferentialProfilesListForIdentifier(String identifier, String species, double cutoff) throws GenesNotFoundException {

        DifferentialProfilesList differentialProfilesList = new DifferentialProfilesList(Collections.emptyList());

        for (String experimentAccession : applicationProperties.getDifferentialExperimentsIdentifiers()) {
            DifferentialProfilesList retrievedProfilesList = retrieveDifferentialProfilesForExperiment(experimentAccession, identifier, species, cutoff);
            differentialProfilesList.addAll(retrievedProfilesList);
        }

        return differentialProfilesList;
    }

    protected DifferentialProfilesList retrieveDifferentialProfilesForExperiment(String experimentAccession, String identifier, String species, double cutoff) throws GenesNotFoundException {

        DifferentialRequestPreferences differentialRequestPreferences = new DifferentialRequestPreferences();
        differentialRequestPreferences.setRegulation(Regulation.UP_DOWN);
        differentialRequestPreferences.setGeneQuery(identifier.toLowerCase());
        differentialRequestPreferences.setExactMatch(true);
        differentialRequestPreferences.setGeneSetMatch(false);
        differentialRequestPreferences.setSpecific(false);
        differentialRequestPreferences.setCutoff(cutoff);

        DifferentialExperiment differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);

        if (differentialExperiment.getSpecies().contains(species)) {
            return executeForExperimentAndAllContrasts(differentialExperiment, differentialRequestPreferences);
        }

        return new DifferentialProfilesList(Collections.emptyList());
    }

    protected DifferentialProfilesList executeForExperimentAndAllContrasts(DifferentialExperiment differentialExperiment, DifferentialRequestPreferences differentialRequestPreferences) throws GenesNotFoundException {

        DifferentialProfilesList differentialProfilesList = null;

        for (String contrastId : differentialExperiment.getContrastIds()) {

            differentialRequestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet(contrastId)));

            rnaSeqRequestContextBuilder.withPreferences(differentialRequestPreferences).forExperiment(differentialExperiment).build();

            if (differentialProfilesList == null) {
                differentialProfilesList = rankRnaSeqProfilesCommand.execute(differentialExperiment.getAccession());
            } else {
                differentialProfilesList.addAll(rankRnaSeqProfilesCommand.execute(differentialExperiment.getAccession()));
            }
        }

        return differentialProfilesList;
    }

}