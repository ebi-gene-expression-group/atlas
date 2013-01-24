/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.*;
import uk.ac.ebi.atlas.streams.RankingParameters;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;

@Named("rankGeneProfiles")
@Scope("prototype")
public class RankGeneProfilesCommand extends GeneProfilesInputStreamCommand<GeneProfilesList> {

    private RankingParameters rankingParameters;

    @Inject
    public void setRankingParameters(RankingParameters rankingParameters) {
        this.rankingParameters = rankingParameters;
    }

    @Override
    public GeneProfilesList apply(Experiment experiment, ObjectInputStream<GeneProfile> inputStream) {

        Comparator<GeneProfile> geneProfileComparator = buildGeneProfileComparator(rankingParameters.isSpecific()
                , getFilterParameters().getQueryFactorValues()
                , experiment.getFactorValues(getFilterParameters().getQueryFactorType()));

        Queue<GeneProfile> rankingQueue = buildRankingQueue(geneProfileComparator, rankingParameters.getHeatmapMatrixSize());

        GeneProfile geneProfile;

        int geneCount = 0;

        while ((geneProfile = inputStream.readNext()) != null) {
            rankingQueue.add(geneProfile);
            geneCount++;
        }

        GeneProfilesList list = new GeneProfilesList(rankingQueue);

        Collections.sort(list, geneProfileComparator);

        list.setTotalResultCount(geneCount);

        return list;

    }


    @Override
    protected GeneProfilesList returnEmpty() {
        return new GeneProfilesList();
    }

    protected Ordering<GeneProfile> buildGeneProfileComparator(boolean isSpecific, Set<FactorValue> selectedQueryFactorValues,
                                                               Set<FactorValue> allFactorValues) {
        return Ordering.from(new GeneProfileComparator(isSpecific, selectedQueryFactorValues, allFactorValues)).reverse();
    }

    protected Queue<GeneProfile> buildRankingQueue(Comparator<GeneProfile> geneProfileComparator, int heatmapMatrixSize) {
        return MinMaxPriorityQueue.orderedBy(geneProfileComparator).maximumSize(heatmapMatrixSize).create();
    }


}