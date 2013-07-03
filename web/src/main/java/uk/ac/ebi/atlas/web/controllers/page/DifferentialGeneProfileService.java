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
import uk.ac.ebi.atlas.commands.RankMicroarrayProfilesCommand;
import uk.ac.ebi.atlas.commands.RankProfilesCommandFactory;
import uk.ac.ebi.atlas.commands.RankRnaSeqProfilesCommand;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.SortedSet;

@Named("differentialGeneProfileService")
@Scope("request")
public class DifferentialGeneProfileService {

    private ApplicationProperties applicationProperties;

    private RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder;

    private MicroarrayRequestContextBuilder microarrayRequestContextBuilder;

    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    private MicroarrayExperimentsCache microarrayExperimentsCache;

    private RankProfilesCommandFactory rankProfilesCommandFactory;

    private DifferentialGeneProfileProperties differentialGeneProfileProperties;

    @Inject
    public DifferentialGeneProfileService(ApplicationProperties applicationProperties,
                                          RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder,
                                          MicroarrayRequestContextBuilder microarrayRequestContextBuilder,
                                          RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                                          MicroarrayExperimentsCache microarrayExperimentsCache,
                                          RankProfilesCommandFactory rankProfilesCommandFactory,
                                          DifferentialGeneProfileProperties differentialGeneProfileProperties) {
        this.applicationProperties = applicationProperties;
        this.rnaSeqRequestContextBuilder = rnaSeqRequestContextBuilder;
        this.microarrayRequestContextBuilder = microarrayRequestContextBuilder;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
        this.rankProfilesCommandFactory = rankProfilesCommandFactory;
        this.differentialGeneProfileProperties = differentialGeneProfileProperties;
    }

    public DifferentialGeneProfileProperties initDifferentialProfilesListMapForIdentifier(String geneQuery, double cutoff) {

        // just being paranoid here, maybe not necessary because of request scope
        differentialGeneProfileProperties.clear();

        // set cutoff used to calculate profile lists for showing on web page
        differentialGeneProfileProperties.setFdrCutoff(cutoff);

        for (String experimentAccession : applicationProperties.getDifferentialExperimentsIdentifiers()) {
            try {
                DifferentialProfilesList retrievedProfilesList = retrieveDifferentialProfilesForRnaSeqExperiment(experimentAccession, geneQuery, cutoff);
                if (!retrievedProfilesList.isEmpty()) {
                    differentialGeneProfileProperties.putDifferentialProfilesListForExperiment(experimentAccession, retrievedProfilesList);
                }
            } catch (GenesNotFoundException e) {
                // this happens when the experiment does not match the geneQuery
            }
        }

        for (String experimentAccession : applicationProperties.getMicroarrayExperimentsIdentifiers()) {
            try {
                DifferentialProfilesList retrievedProfilesList = retrieveDifferentialProfilesForMicroarrayExperiment(experimentAccession, geneQuery, cutoff);
                if (!retrievedProfilesList.isEmpty()) {
                    differentialGeneProfileProperties.putDifferentialProfilesListForExperiment(experimentAccession, retrievedProfilesList);
                }
            } catch (GenesNotFoundException e) {
                // this happens when the experiment does not match the geneQuery
            }
        }

        return differentialGeneProfileProperties;
    }

    DifferentialProfilesList retrieveDifferentialProfilesForRnaSeqExperiment(String experimentAccession, String geneQuery, double cutoff) throws GenesNotFoundException {

        // no need to worry about species for now, as an identifier in geneQuery is already species specific
        DifferentialRequestPreferences differentialRequestPreferences = new DifferentialRequestPreferences();
        differentialRequestPreferences.setGeneQuery(geneQuery.toLowerCase());
        differentialRequestPreferences.setCutoff(cutoff);

        DifferentialExperiment differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);
        rnaSeqRequestContextBuilder.withPreferences(differentialRequestPreferences).forExperiment(differentialExperiment).build();

        RankRnaSeqProfilesCommand rankRnaSeqProfilesCommand = rankProfilesCommandFactory.getRankRnaSeqProfilesCommand();
        return rankRnaSeqProfilesCommand.execute(differentialExperiment.getAccession());
    }

    DifferentialProfilesList retrieveDifferentialProfilesForMicroarrayExperiment(String experimentAccession, String geneQuery, double cutoff) throws GenesNotFoundException {

        // no need to worry about species for now, as an identifier in geneQuery is already species specific
        MicroarrayRequestPreferences microarrayRequestPreferences = new MicroarrayRequestPreferences();
        microarrayRequestPreferences.setGeneQuery(geneQuery.toLowerCase());
        microarrayRequestPreferences.setCutoff(cutoff);

        MicroarrayExperiment microarrayExperiment = microarrayExperimentsCache.getExperiment(experimentAccession);

        // TODO: this assumes that there is only one array design accession per experiment, this is true now, but might change in the future
        SortedSet<String> arrayDesignAccessions = microarrayExperiment.getArrayDesignAccessions();
        microarrayRequestPreferences.setArrayDesignAccession(arrayDesignAccessions.first());

        microarrayRequestContextBuilder.withPreferences(microarrayRequestPreferences).forExperiment(microarrayExperiment).build();

        RankMicroarrayProfilesCommand rankMicroarrayProfilesCommand = rankProfilesCommandFactory.getRankMicroarrayProfilesCommand();
        return rankMicroarrayProfilesCommand.execute(microarrayExperiment.getAccession());
    }

}