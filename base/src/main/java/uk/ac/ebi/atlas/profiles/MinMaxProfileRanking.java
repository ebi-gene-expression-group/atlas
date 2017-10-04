package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import com.google.common.collect.MinMaxPriorityQueue;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Comparator;

public class MinMaxProfileRanking<T extends Profile, L extends GeneProfilesList<T>> implements SelectProfiles<T,L> {

    private final Comparator<T> comparator;
    private final java.util.function.Supplier<L> newList;

    public MinMaxProfileRanking(Comparator<T> comparator, java.util.function.Supplier<L> newList) {
        this.comparator = comparator;
        this.newList = newList;
    }

    @Override
    public L select(ObjectInputStream<T> profiles, int maxSize) {

        MinMaxPriorityQueue<T> rankingQueue =  MinMaxPriorityQueue.orderedBy(comparator).maximumSize(maxSize).create();

        int count = 0;

        for (T profile : new IterableObjectInputStream<>(profiles)) {
            rankingQueue.add(profile);
            count++;
        }

        L list = newList.get();
        T profile;
        while ((profile = rankingQueue.poll()) != null) {
            list.add(profile);
        }
        list.setTotalResultCount(count);
        return list;
    }

}
