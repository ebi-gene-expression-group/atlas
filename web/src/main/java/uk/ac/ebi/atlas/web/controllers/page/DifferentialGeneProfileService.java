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

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.RankMicroarrayProfilesCommand;
import uk.ac.ebi.atlas.commands.RankProfilesCommandFactory;
import uk.ac.ebi.atlas.commands.RankRnaSeqProfilesCommand;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Named("differentialGeneProfileService")
@Scope("request")
public class DifferentialGeneProfileService {

    private ApplicationProperties applicationProperties;

    private SolrClient solrClient;

    private RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder;

    private MicroarrayRequestContextBuilder microarrayRequestContextBuilder;

    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    private MicroarrayExperimentsCache microarrayExperimentsCache;

    private RankProfilesCommandFactory rankProfilesCommandFactory;

    private DifferentialGeneProfileProperties differentialGeneProfileProperties;

    @Inject
    public DifferentialGeneProfileService(ApplicationProperties applicationProperties,
                                          SolrClient solrClient,
                                          RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder,
                                          MicroarrayRequestContextBuilder microarrayRequestContextBuilder,
                                          RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                                          MicroarrayExperimentsCache microarrayExperimentsCache,
                                          RankProfilesCommandFactory rankProfilesCommandFactory,
                                          DifferentialGeneProfileProperties differentialGeneProfileProperties) {
        this.applicationProperties = applicationProperties;
        this.solrClient = solrClient;
        this.rnaSeqRequestContextBuilder = rnaSeqRequestContextBuilder;
        this.microarrayRequestContextBuilder = microarrayRequestContextBuilder;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
        this.rankProfilesCommandFactory = rankProfilesCommandFactory;
        this.differentialGeneProfileProperties = differentialGeneProfileProperties;
    }

    public DifferentialGeneProfileProperties initDifferentialProfilesListMapForIdentifier(String geneQuery, double cutoff) {

        Collection<String> speciesForGeneId = solrClient.findSpeciesForGeneId(geneQuery);
        if (speciesForGeneId.size() != 1) {
            throw new ResourceNotFoundException("No unambiguous species could be determined.");
        }
        String specie = speciesForGeneId.iterator().next();
        specie = specie.substring(0, 1).toUpperCase() + specie.substring(1);

        // just being paranoid here, maybe not necessary because of request scope
        differentialGeneProfileProperties.clear();

        // set cutoff used to calculate profile lists for showing on web page
        differentialGeneProfileProperties.setFdrCutoff(cutoff);

        for (String experimentAccession : applicationProperties.getDifferentialExperimentsIdentifiers()) {
            try {
                DifferentialProfilesList retrievedProfilesList = retrieveDifferentialProfilesForRnaSeqExperiment(experimentAccession, geneQuery, cutoff, specie);
                if (!retrievedProfilesList.isEmpty()) {
                    differentialGeneProfileProperties.putDifferentialProfilesListForExperiment(experimentAccession, retrievedProfilesList);
                }
            } catch (GenesNotFoundException e) {
                // this happens when the experiment does not contain identifier
            }
        }

        for (String experimentAccession : applicationProperties.getMicroarrayExperimentsIdentifiers()) {
            try {
                Collection<DifferentialProfilesList> retrievedProfilesLists = retrieveDifferentialProfilesForMicroarrayExperiment(experimentAccession, geneQuery, cutoff, specie);
                if (!retrievedProfilesLists.isEmpty()) {
                    for (DifferentialProfilesList differentialProfilesList : retrievedProfilesLists) {
                        differentialGeneProfileProperties.putDifferentialProfilesListForExperiment(experimentAccession, differentialProfilesList);
                    }
                }
            } catch (GenesNotFoundException e) {
                // this happens when the experiment does not contain identifier
            }
        }

        return differentialGeneProfileProperties;
    }

    DifferentialProfilesList retrieveDifferentialProfilesForRnaSeqExperiment(String experimentAccession, String geneQuery, double cutoff, String specie) throws GenesNotFoundException {

        // limit experiment selection by specie
        DifferentialExperiment differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);
        if (!differentialExperiment.getSpecies().contains(specie)) {
            return new DifferentialProfilesList(Collections.emptyList());
        }

        DifferentialRequestPreferences differentialRequestPreferences = new DifferentialRequestPreferences();
        differentialRequestPreferences.setGeneQuery(geneQuery.toLowerCase());
        differentialRequestPreferences.setCutoff(cutoff);

        rnaSeqRequestContextBuilder.withPreferences(differentialRequestPreferences).forExperiment(differentialExperiment).build();

        RankRnaSeqProfilesCommand rankRnaSeqProfilesCommand = rankProfilesCommandFactory.getRankRnaSeqProfilesCommand();
        return rankRnaSeqProfilesCommand.execute(differentialExperiment.getAccession());
    }

    Collection<DifferentialProfilesList> retrieveDifferentialProfilesForMicroarrayExperiment(String experimentAccession, String geneQuery, double cutoff, String specie) throws GenesNotFoundException {

        // limit experiment selection by specie
        MicroarrayExperiment microarrayExperiment = microarrayExperimentsCache.getExperiment(experimentAccession);
        if (!microarrayExperiment.getSpecies().contains(specie)) {
            return Collections.emptyList();
        }

        MicroarrayRequestPreferences microarrayRequestPreferences = new MicroarrayRequestPreferences();
        microarrayRequestPreferences.setGeneQuery(geneQuery.toLowerCase());
        microarrayRequestPreferences.setCutoff(cutoff);

        Collection<DifferentialProfilesList> results = Lists.newArrayList();
        Set<String> arrayDesignAccessions = microarrayExperiment.getArrayDesignAccessions();
        for (String arrayDesignAccession : arrayDesignAccessions) {
            microarrayRequestPreferences.setArrayDesignAccession(arrayDesignAccession);

            microarrayRequestContextBuilder.withPreferences(microarrayRequestPreferences).forExperiment(microarrayExperiment).build();

            RankMicroarrayProfilesCommand rankMicroarrayProfilesCommand = rankProfilesCommandFactory.getRankMicroarrayProfilesCommand();
            DifferentialProfilesList differentialProfilesList = rankMicroarrayProfilesCommand.execute(microarrayExperiment.getAccession());
            results.add(differentialProfilesList);
        }

        return results;
    }

}