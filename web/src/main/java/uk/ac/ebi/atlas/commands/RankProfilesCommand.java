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

import com.google.common.collect.MinMaxPriorityQueue;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.GeneProfilesList;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

public abstract class RankProfilesCommand<T extends GeneProfilesList, K extends GeneProfile> extends GeneProfilesQueryCommand<T,K> {

    @Inject
    public RankProfilesCommand(RequestContext requestContext) {
        super(requestContext);
    }

    @Override
    protected T execute(ObjectInputStream<K> inputStream, RequestContext requestContext) {

        MinMaxPriorityQueue<K> rankingQueue = buildRankingQueue(requestContext);

        K geneProfile;

        int geneCount = 0;

        while ((geneProfile = inputStream.readNext()) != null) {
            rankingQueue.add(geneProfile);
            geneCount++;
        }

        T list = createGeneProfilesList(rankingQueue);

        Collections.sort(list, rankingQueue.comparator());

        list.setTotalResultCount(geneCount);

        return list;

    }

    protected abstract T createGeneProfilesList(Queue<K> geneProfiles);

    protected abstract Comparator<K> createGeneProfileComparator(RequestContext requestContext);

    MinMaxPriorityQueue<K> buildRankingQueue(RequestContext requestContext){
        Comparator<K> geneProfileComparator = createGeneProfileComparator(requestContext);
        return MinMaxPriorityQueue.orderedBy(geneProfileComparator).maximumSize(requestContext.getHeatmapMatrixSize()).create();
    }

}
