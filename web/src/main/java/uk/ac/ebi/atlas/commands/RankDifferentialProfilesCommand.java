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

package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;

//@Named
public class RankDifferentialProfilesCommand{
    protected static final Logger logger = Logger.getLogger(GeneProfilesInputStreamCommand.class);

    private Set<Contrast> selectedContrasts;
    private double cutoff;
    private Regulation regulation;
    private int rankingSize;
    private String geneQuery;
    private SolrClient solrClient;
    private String species;
    private InputStreamFactory inputStreamFactory;

    public RankDifferentialProfilesCommand(Set<Contrast> selectedContrasts, double cutoff, Regulation regulation, int rankingSize, String geneQuery, SolrClient solrClient, String species, InputStreamFactory inputStreamFactory){
        this.selectedContrasts = selectedContrasts;
        this.cutoff = cutoff;
        this.regulation = regulation;
        this.rankingSize = rankingSize;
        this.geneQuery = geneQuery;
        this.solrClient = solrClient;
        this.species = species;
        this.inputStreamFactory = inputStreamFactory;
    }

    public GeneProfilesList<DifferentialProfile> execute(DifferentialExperiment experiment) throws GenesNotFoundException {

        Set<String> selectedGeneIds = null;

        if(StringUtils.isNotBlank(geneQuery)){

            selectedGeneIds = solrClient.findGeneIds(geneQuery, species);

        }

        ObjectInputStream<DifferentialProfile> geneProfileInputStream = inputStreamFactory.createDifferentialProfileInputStream(experiment.getAccession(), cutoff, regulation);

        try (ObjectInputStream<DifferentialProfile> inputStream = new GeneProfileInputStreamFilter(geneProfileInputStream, selectedGeneIds, selectedContrasts)) {


            Queue<DifferentialProfile> rankingQueue = buildRankingQueue();

            DifferentialProfile differentialProfile;

            int geneCount = 0;

            while ((differentialProfile = inputStream.readNext()) != null) {
                rankingQueue.add(differentialProfile);
                geneCount++;
            }

            GeneProfilesList<DifferentialProfile> list = new GeneProfilesList(rankingQueue);

            Collections.sort(list, buildGeneProfileComparator());

            list.setTotalResultCount(geneCount);

            return list;

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }

    }

    Ordering<DifferentialProfile> buildGeneProfileComparator() {
        return Ordering.natural().onResultOf(new Function<DifferentialProfile, Double>() {
            @Override
            public Double apply(DifferentialProfile differentialProfile) {
                return differentialProfile.getMinExpressionLevel();
            }
        });
    }

    protected Queue<DifferentialProfile> buildRankingQueue() {
        Comparator<DifferentialProfile> differentialProfileComparator = buildGeneProfileComparator();
        return MinMaxPriorityQueue.orderedBy(differentialProfileComparator).maximumSize(rankingSize).create();
    }


}
