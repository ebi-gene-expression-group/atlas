package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

@Named
@Scope("prototype")
public class RankDifferentialProfilesExecutor extends AbstractCommandExecutor<GeneProfilesList<DifferentialProfile>, DifferentialProfile> implements CommandExecutor<GeneProfilesList<DifferentialProfile>> {

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
    protected GeneProfilesList<DifferentialProfile> execute(ObjectInputStream<DifferentialProfile> inputStream) {
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
