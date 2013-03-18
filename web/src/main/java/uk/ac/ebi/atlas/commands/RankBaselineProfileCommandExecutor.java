package uk.ac.ebi.atlas.commands;

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfilesList;
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
public class RankBaselineProfileCommandExecutor implements CommandExecutor<GeneProfilesList, BaselineProfile> {

    private BaselineRequestContext requestContext;

    @Inject
    public RankBaselineProfileCommandExecutor(BaselineRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    public GeneProfilesList execute(ObjectInputStream<BaselineProfile> inputStream) {
        Comparator<BaselineProfile> geneProfileComparator = buildGeneProfileComparator(requestContext.isSpecific()
                , requestContext.getSelectedQueryFactors(), requestContext.getAllQueryFactors(), requestContext.getCutoff());

        Queue<BaselineProfile> rankingQueue = buildRankingQueue(geneProfileComparator, requestContext.getHeatmapMatrixSize());

        BaselineProfile baselineProfile;

        int geneCount = 0;

        while ((baselineProfile = inputStream.readNext()) != null) {
            rankingQueue.add(baselineProfile);
            geneCount++;
        }

        GeneProfilesList list = new GeneProfilesList(rankingQueue);

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

}
