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

package uk.ac.ebi.atlas.web.controllers.page.bioentity;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.*;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.*;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named("differentialGeneProfileService")
@Scope("request")
public class DifferentialGeneProfileService {

    private SolrQueryService solrQueryService;

    private RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder;

    private MicroarrayRequestContextBuilder microarrayRequestContextBuilder;

    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    private MicroarrayExperimentsCache microarrayExperimentsCache;

    private RankProfilesCommandFactory rankProfilesCommandFactory;

    private ExperimentTrader experimentTrader;

    private List<DifferentialBioentityExpression> differentialBioentityExpressions = new ArrayList<DifferentialBioentityExpression>();


    @Inject
    public DifferentialGeneProfileService(ExperimentTrader experimentTrader,
                                          SolrQueryService solrQueryService,
                                          RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder,
                                          MicroarrayRequestContextBuilder microarrayRequestContextBuilder,
                                          RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                                          MicroarrayExperimentsCache microarrayExperimentsCache,
                                          RankProfilesCommandFactory rankProfilesCommandFactory) {
        this.experimentTrader = experimentTrader;
        this.solrQueryService = solrQueryService;
        this.rnaSeqRequestContextBuilder = rnaSeqRequestContextBuilder;
        this.microarrayRequestContextBuilder = microarrayRequestContextBuilder;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
        this.rankProfilesCommandFactory = rankProfilesCommandFactory;
    }

    public DifferentialBioentityExpressions initDifferentialBioentityExpressions(String geneQuery, double cutoff) {

        String species = solrQueryService.findSpeciesForBioentityId(geneQuery);
        species = StringUtils.capitalize(species);

        Set<String> mirbaseIds = solrQueryService.findPropertyValuesForGeneId(geneQuery, "mirbase_id");
        if (mirbaseIds.size() > 0) {
            // there should be a one to one mapping between ENSEMBL gene IDs and miRBase IDs
            filterMatureRNADifferentialProfilesForIdentifier(mirbaseIds.iterator().next(), cutoff, species);
        } else {
            filterMatureRNADifferentialProfilesForIdentifier(geneQuery, cutoff, species);
        }

        Collections.sort(differentialBioentityExpressions, new Comparator<DifferentialBioentityExpression>() {
            @Override
            public int compare(DifferentialBioentityExpression o1, DifferentialBioentityExpression o2) {
                return o1.getExpression().getLevel() - o2.getExpression().getLevel() < 0 ? -1 : 1;
            }
        });

        return new DifferentialBioentityExpressions(differentialBioentityExpressions, differentialBioentityExpressions.size());
    }

    private void filterMatureRNADifferentialProfilesForIdentifier(String identifier, double cutoff, String species) {
        Set<String> matureRNAsForMirbaseId = solrQueryService.fetchGeneIdentifiersFromSolr(identifier, "mirna", "hairpin_id");
        if (matureRNAsForMirbaseId.size() > 0) {
            for (String matureRNAIdentifier : matureRNAsForMirbaseId) {
                processDifferentialProfilesForIdentifier(matureRNAIdentifier, cutoff, species);
            }
        } else {
            processDifferentialProfilesForIdentifier(identifier, cutoff, species);
        }
    }

    private void processDifferentialProfilesForIdentifier(String identifier, double cutoff, String species) {

        for (String experimentAccession : experimentTrader.getDifferentialExperimentAccessions()) {
            try {
                DifferentialProfilesList differentialProfilesList = retrieveDifferentialProfilesForRnaSeqExperiment(experimentAccession, identifier, cutoff, species);
                addAllDifferentialBaselineExpressions(experimentAccession, differentialProfilesList, species);

            } catch (GenesNotFoundException e) {
                // this happens when the experiment does not contain identifier
            }
        }

        for (String experimentAccession : experimentTrader.getMicroarrayExperimentAccessions()) {
            try {
                Collection<DifferentialProfilesList> differentialProfilesLists = retrieveDifferentialProfilesForMicroarrayExperiment(experimentAccession, identifier, cutoff, species);
                if (!differentialProfilesLists.isEmpty()) {
                    for (DifferentialProfilesList differentialProfilesList : differentialProfilesLists) {
                        addAllDifferentialBaselineExpressions(experimentAccession, differentialProfilesList, species);
                    }
                }
            } catch (GenesNotFoundException e) {
                // this happens when the experiment does not contain identifier
            }
        }
    }

    private void addAllDifferentialBaselineExpressions(String experimentAccession,
                                                       DifferentialProfilesList<DifferentialProfile> differentialProfilesList,
                                                       String species) {
        for(DifferentialProfile<DifferentialExpression> differentialProfile:differentialProfilesList){
            for (Contrast contrast : differentialProfile.getConditions()) {
                DifferentialBioentityExpression differentialBioentityExpression =
                    new DifferentialBioentityExpression(differentialProfile.getId(), differentialProfile.getId(), experimentAccession,
                            differentialProfile.getExpression(contrast), species, null);
                differentialBioentityExpressions.add(differentialBioentityExpression);
            }
        }
    }


    DifferentialProfilesList retrieveDifferentialProfilesForRnaSeqExperiment(String experimentAccession, String identifier, double cutoff, String specie) throws GenesNotFoundException {

        // limit experiment selection by specie
        DifferentialExperiment differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);
        if (!differentialExperiment.getSpecies().contains(specie)) {
            return new DifferentialProfilesList(Collections.emptyList());
        }

        DifferentialRequestPreferences differentialRequestPreferences = new DifferentialRequestPreferences();
        differentialRequestPreferences.setCutoff(cutoff);

        RnaSeqRequestContext rnaSeqRequestContext = rnaSeqRequestContextBuilder.withPreferences(differentialRequestPreferences).forExperiment(differentialExperiment).build();

        RankRnaSeqProfilesCommand rankRnaSeqProfilesCommand = rankProfilesCommandFactory.getRankRnaSeqProfilesCommand();

        GeneProfileInputStreamFilter geneProfileInputStreamFilter = createGeneProfileInputStreamFilter(experimentAccession, identifier, rankRnaSeqProfilesCommand);

        return rankRnaSeqProfilesCommand.execute(geneProfileInputStreamFilter, rnaSeqRequestContext);
    }


    Collection<DifferentialProfilesList> retrieveDifferentialProfilesForMicroarrayExperiment(String experimentAccession, String identifier, double cutoff, String specie) throws GenesNotFoundException {

        // limit experiment selection by specie
        MicroarrayExperiment microarrayExperiment = microarrayExperimentsCache.getExperiment(experimentAccession);
        if (!microarrayExperiment.getSpecies().contains(specie)) {
            return Collections.emptyList();
        }

        MicroarrayRequestPreferences microarrayRequestPreferences = new MicroarrayRequestPreferences();
        microarrayRequestPreferences.setCutoff(cutoff);

        Collection<DifferentialProfilesList> results = Lists.newArrayList();
        Set<String> arrayDesignAccessions = microarrayExperiment.getArrayDesignAccessions();
        for (String arrayDesignAccession : arrayDesignAccessions) {
            microarrayRequestPreferences.setArrayDesignAccession(arrayDesignAccession);

            MicroarrayRequestContext microarrayRequestContext = microarrayRequestContextBuilder.withPreferences(microarrayRequestPreferences).forExperiment(microarrayExperiment).build();

            RankMicroarrayProfilesCommand rankMicroarrayProfilesCommand = rankProfilesCommandFactory.getRankMicroarrayProfilesCommand();

            GeneProfileInputStreamFilter geneProfileInputStreamFilter = createGeneProfileInputStreamFilter(experimentAccession, identifier, rankMicroarrayProfilesCommand);

            DifferentialProfilesList differentialProfilesList = rankMicroarrayProfilesCommand.execute(geneProfileInputStreamFilter, microarrayRequestContext);
            results.add(differentialProfilesList);
        }

        return results;
    }

    private GeneProfileInputStreamFilter createGeneProfileInputStreamFilter(String experimentAccession, String identifier, RankProfilesCommand rankRnaSeqProfilesCommand) {
        // this is to by-pass any Solr query as we are already working with identifier here
        ObjectInputStream<RnaSeqProfile> inputStream = rankRnaSeqProfilesCommand.createInputStream(experimentAccession);
        ArrayList<String> uppercaseGeneIDs = Lists.newArrayList(identifier.toUpperCase());
        return new GeneProfileInputStreamFilter(inputStream, uppercaseGeneIDs, Collections.emptySet());
    }

}