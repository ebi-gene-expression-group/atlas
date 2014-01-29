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
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

public abstract class RankProfilesCommand<T extends GeneProfilesList, K extends Profile> extends GeneProfilesQueryCommand<T, K> {

    @Inject
    protected RankProfilesCommand(RequestContext requestContext) {
        super(requestContext);
    }

    @Override
    public T execute(ObjectInputStream<K> inputStream, RequestContext requestContext) {

        Comparator<K> profilesComparator = createGeneProfileComparator(requestContext);

        if (requestContext.isGeneSetMatch()) {
            return buildAverageGeneSetProfiles(requestContext.getGeneQueryResponse(), inputStream, profilesComparator);
        }

        MinMaxPriorityQueue<K> rankingQueue = buildRankingQueue(requestContext.getHeatmapMatrixSize(), profilesComparator);

        int geneCount = 0;
        K geneProfile;

        while ((geneProfile = inputStream.readNext()) != null) {
            rankingQueue.add(geneProfile);
            geneCount++;
        }
        T list = createGeneProfilesList(rankingQueue);
        list.setTotalResultCount(geneCount);

        //TODO: this sort is not necessary if we pull items off the queue in order into geneProfilesList
        Collections.sort(list, profilesComparator);

        return list;
    }

    protected abstract T buildAverageGeneSetProfiles(GeneQueryResponse geneQueryResponse, ObjectInputStream<K> objectInputStream, Comparator<K> geneProfilesComparator);

    protected abstract T createGeneProfilesList(Queue<K> geneProfiles);

    protected abstract Comparator<K> createGeneProfileComparator(RequestContext requestContext);

    MinMaxPriorityQueue<K> buildRankingQueue(int heatmapMatrixSize, Comparator<K> profilesComparator) {
        return MinMaxPriorityQueue.orderedBy(profilesComparator).maximumSize(heatmapMatrixSize).create();
    }

}
