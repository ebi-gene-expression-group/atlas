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
import org.springframework.util.CollectionUtils;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.GeneSpecificityComparator;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;

@Named("rankGeneProfiles")
@Scope("prototype")
public class RankGeneProfilesCommand extends GeneProfilesInputStreamCommand<GeneProfilesList> {


    @Override
    public GeneProfilesList apply(RequestPreferences requestPreferences, Experiment experiment, ObjectInputStream<GeneProfile> inputStream) {

        Set<String> selectedOrganismParts = CollectionUtils.isEmpty(requestPreferences.getOrganismParts())?
                                                                experiment.getAllExperimentalFactors():
                                                                requestPreferences.getOrganismParts();

        Comparator<GeneProfile> reverseSpecificityComparator = buildReverseSpecificityComparator(requestPreferences.isRankGenesExpressedOnMostFactorsLast()
                                                                                                ,selectedOrganismParts
                                                                                                ,experiment.getAllExperimentalFactors());

        Queue<GeneProfile> rankingQueue = buildRankingQueue(reverseSpecificityComparator, requestPreferences.getHeatmapMatrixSize());

        GeneProfile geneProfile;

        int geneCount = 0;

        while ((geneProfile = inputStream.readNext()) != null) {
            rankingQueue.add(geneProfile);
            geneCount++;
        }

        GeneProfilesList list = new GeneProfilesList(rankingQueue);

        Collections.sort(list, reverseSpecificityComparator);

        list.setTotalResultCount(geneCount);

        return list;

    }

    @Override
    protected GeneProfilesList returnEmpty() {
        return new GeneProfilesList();
    }

    protected Ordering<GeneProfile> buildReverseSpecificityComparator(boolean rankGenesExpressedOnMostFactorsLast, Set<String> selectedOrganismParts, Set<String> allOrganismParts) {
        return Ordering.from(new GeneSpecificityComparator(rankGenesExpressedOnMostFactorsLast, selectedOrganismParts, allOrganismParts)).reverse();
    }

    protected Queue<GeneProfile> buildRankingQueue(Comparator<GeneProfile> reverseSpecificityComparator, int heatmapMatrixSize) {
        return MinMaxPriorityQueue.orderedBy(reverseSpecificityComparator).maximumSize(heatmapMatrixSize).create();
    }


}