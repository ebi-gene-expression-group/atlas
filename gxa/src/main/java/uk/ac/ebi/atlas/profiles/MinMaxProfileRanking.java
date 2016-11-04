package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.GeneProfilesList;
import com.google.common.collect.MinMaxPriorityQueue;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Comparator;

public class MinMaxProfileRanking<T extends Profile, L extends GeneProfilesList<T>> implements SelectProfiles<T,L> {

    private Comparator<T> comparator;
    private GeneProfilesListBuilder<L> geneProfilesListBuilder;

    public MinMaxProfileRanking(Comparator<T> comparator, GeneProfilesListBuilder<L> geneProfilesListBuilder) {
        this.comparator = comparator;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    @Override
    public L select(Iterable<T> profiles, int maxSize) {

        MinMaxPriorityQueue<T> rankingQueue =  MinMaxPriorityQueue.orderedBy(comparator).maximumSize(maxSize).create();

        int count = 0;

        for (T profile : profiles) {
            rankingQueue.add(profile);
            count++;
        }

        L list = geneProfilesListBuilder.create();
        list.addAll(rankingQueue);
        list.setTotalResultCount(count);
        return list;
    }

}
