package uk.ac.ebi.atlas.commands;

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
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
    public GeneProfilesList apply(RequestPreferences requestPreferences, ObjectInputStream<GeneProfile> inputStream) {

        Comparator<GeneProfile> reverseSpecificityComparator = buildReverseSpecificityComparator(requestPreferences.getOrganismParts());

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

    protected Ordering<GeneProfile> buildReverseSpecificityComparator(Set<String> organismParts) {
        boolean orderBySpecificity = CollectionUtils.isEmpty(organismParts);
        return Ordering.from(new GeneSpecificityComparator(orderBySpecificity)).reverse();
    }

    protected Queue<GeneProfile> buildRankingQueue(Comparator<GeneProfile> reverseSpecificityComparator, int heatmapMatrixSize) {
        return MinMaxPriorityQueue.orderedBy(reverseSpecificityComparator).maximumSize(heatmapMatrixSize).create();
    }


}