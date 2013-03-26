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
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfileComparator;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

@Named
@Scope("prototype")
public class RankDifferentialProfilesExecutor extends AbstractCommandExecutor<DifferentialProfilesList, DifferentialProfile> implements CommandExecutor<DifferentialProfilesList> {

    private DifferentialRequestContext requestContext;

    private InputStreamFactory inputStreamFactory;

    @Inject
    public RankDifferentialProfilesExecutor(DifferentialRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Inject
    public void setInputStreamFactory(InputStreamFactory inputStreamFactory) {
        this.inputStreamFactory = inputStreamFactory;
    }


    @Override
    protected DifferentialProfilesList execute(ObjectInputStream<DifferentialProfile> inputStream) {
        Queue<DifferentialProfile> rankingQueue = buildRankingQueue();

        DifferentialProfile differentialProfile;

        int geneCount = 0;

        while ((differentialProfile = inputStream.readNext()) != null) {
            rankingQueue.add(differentialProfile);
            geneCount++;
        }

        DifferentialProfilesList list = new DifferentialProfilesList(rankingQueue);

        Collections.sort(list, buildGeneProfileComparator());

        list.setTotalResultCount(geneCount);

        return list;
    }

    Ordering<DifferentialProfile> buildGeneProfileComparator() {
        return Ordering.from(new DifferentialProfileComparator(requestContext.isSpecific(), requestContext.getSelectedQueryFactors(),
                requestContext.getAllQueryFactors(), requestContext.getRegulation()));
    }

    protected Queue<DifferentialProfile> buildRankingQueue() {
        Comparator<DifferentialProfile> differentialProfileComparator = buildGeneProfileComparator();
        return MinMaxPriorityQueue.orderedBy(differentialProfileComparator).maximumSize(requestContext.getHeatmapMatrixSize()).create();
    }

    @Override
    protected ObjectInputStream<DifferentialProfile> createInputStream(String experimentAccession) {
        return inputStreamFactory.createDifferentialProfileInputStream(experimentAccession);
    }

    @Override
    protected RequestContext getRequestContext() {
        return requestContext;
    }
}
