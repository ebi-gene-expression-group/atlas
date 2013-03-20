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
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfileComparator;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;

@Named
@Scope("request")
public class RankBaselineProfileCommandExecutor extends AbstractCommandExecutor<BaselineProfilesList, BaselineProfile> implements CommandExecutor<BaselineProfilesList> {

    private BaselineRequestContext requestContext;


    @Inject
    public RankBaselineProfileCommandExecutor(BaselineRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    protected BaselineProfilesList execute(ObjectInputStream<BaselineProfile> inputStream) {
        Comparator<BaselineProfile> geneProfileComparator = buildGeneProfileComparator(requestContext.isSpecific()
                , requestContext.getSelectedQueryFactors(), requestContext.getAllQueryFactors(), requestContext.getCutoff());

        Queue<BaselineProfile> rankingQueue = buildRankingQueue(geneProfileComparator, requestContext.getHeatmapMatrixSize());

        BaselineProfile baselineProfile;

        int geneCount = 0;

        while ((baselineProfile = inputStream.readNext()) != null) {
            rankingQueue.add(baselineProfile);
            geneCount++;
        }

        BaselineProfilesList list = new BaselineProfilesList(rankingQueue);

        Collections.sort(list, geneProfileComparator);

        list.setTotalResultCount(geneCount);

        return list;

    }

    protected Ordering<BaselineProfile> buildGeneProfileComparator(boolean isSpecific, Set<Factor> selectedQueryFactors,
                                                                   Set<Factor> allFactors, double cutoff) {
        return Ordering.from(new BaselineProfileComparator(isSpecific, selectedQueryFactors, allFactors, cutoff)).reverse();
    }

    protected Queue<BaselineProfile> buildRankingQueue(Comparator<BaselineProfile> geneProfileComparator, int heatmapMatrixSize) {
        return MinMaxPriorityQueue.orderedBy(geneProfileComparator).maximumSize(heatmapMatrixSize).create();
    }

    @Override
    protected ObjectInputStream<BaselineProfile> createInputStream(String experimentAccession) {
        return inputStreamFactory.createGeneProfileInputStream(experimentAccession);
    }

    @Override
    protected RequestContext getRequestContext() {
        return requestContext;
    }
}
