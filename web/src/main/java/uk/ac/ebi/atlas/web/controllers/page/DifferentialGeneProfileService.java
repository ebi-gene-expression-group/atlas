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
import uk.ac.ebi.atlas.commands.RankProfilesCommandFactory;
import uk.ac.ebi.atlas.commands.RankRnaSeqProfilesCommand;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesListMap;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;

@Named("differentialGeneProfileService")
@Scope("request")
public class DifferentialGeneProfileService {

    private ApplicationProperties applicationProperties;

    private RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder;

    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    private RankProfilesCommandFactory rankProfilesCommandFactory;

    private DifferentialProfilesListMap differentialProfilesListMap;

    @Inject
    public DifferentialGeneProfileService(ApplicationProperties applicationProperties,
                                          RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder,
                                          RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                                          RankProfilesCommandFactory rankProfilesCommandFactory,
                                          DifferentialProfilesListMap differentialProfilesListMap) {
        this.applicationProperties = applicationProperties;
        this.rnaSeqRequestContextBuilder = rnaSeqRequestContextBuilder;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.rankProfilesCommandFactory = rankProfilesCommandFactory;
        this.differentialProfilesListMap = differentialProfilesListMap;
    }

    public DifferentialProfilesListMap getDifferentialProfilesListMapForIdentifier(String identifier, double cutoff) {

        // just being paranoid here, maybe not necessary because of request scope
        differentialProfilesListMap.clear();

        for (String experimentAccession : applicationProperties.getDifferentialExperimentsIdentifiers()) {
            DifferentialProfilesList retrievedProfilesList = retrieveDifferentialProfilesForExperiment(experimentAccession, identifier, cutoff);
            if (!retrievedProfilesList.isEmpty()) {
                differentialProfilesListMap.putDifferentialProfilesListForExperiment(experimentAccession, retrievedProfilesList);
            }
        }

        return differentialProfilesListMap;
    }

    DifferentialProfilesList retrieveDifferentialProfilesForExperiment(String experimentAccession, String geneQuery, double cutoff) {

        DifferentialRequestPreferences differentialRequestPreferences = new DifferentialRequestPreferences();
        differentialRequestPreferences.setGeneQuery(geneQuery.toLowerCase());
        differentialRequestPreferences.setCutoff(cutoff);

        // no need to worry about species for now, as geneQuery is already species specific
        DifferentialExperiment differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);
        return executeForExperimentAndAllContrasts(differentialExperiment, differentialRequestPreferences);
    }

    DifferentialProfilesList executeForExperimentAndAllContrasts(DifferentialExperiment differentialExperiment, DifferentialRequestPreferences differentialRequestPreferences) {

        DifferentialProfilesList differentialProfilesList = new DifferentialProfilesList(Collections.emptyList());

        for (String contrastId : differentialExperiment.getContrastIds()) {

            differentialRequestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet(contrastId)));

            rnaSeqRequestContextBuilder.withPreferences(differentialRequestPreferences).forExperiment(differentialExperiment).build();

            RankRnaSeqProfilesCommand rankRnaSeqProfilesCommand = rankProfilesCommandFactory.getRankRnaSeqProfilesCommand();
            try {
                differentialProfilesList.addAll(rankRnaSeqProfilesCommand.execute(differentialExperiment.getAccession()));
            } catch (GenesNotFoundException e) {
                // this happens when a gene identifier is searched within another species than it belongs to
            }
        }

        return differentialProfilesList;
    }

}