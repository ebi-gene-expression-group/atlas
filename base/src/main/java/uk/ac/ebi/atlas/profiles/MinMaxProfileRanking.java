package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import com.google.common.collect.MinMaxPriorityQueue;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Comparator;
import java.util.function.Supplier;

public class MinMaxProfileRanking<T extends Profile, L extends GeneProfilesList<T>> implements SelectProfiles<T, L> {

    private final Comparator<T> comparator;
    private final Supplier<L> newList;

    public MinMaxProfileRanking(Comparator<T> comparator, Supplier<L> newList) {
        this.comparator = comparator;
        this.newList = newList;
    }

    /**
    @param maxSize value is restricted between 1 and 1000 when set as a bean binding to UI parameters
    @see uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences
  */
    @Override
    public L select(ObjectInputStream<T> profiles, int maxSize) {

        MinMaxPriorityQueue<T> rankingQueue =
                maxSize > 0
                        ? MinMaxPriorityQueue.orderedBy(comparator).maximumSize(maxSize).create()
                        : MinMaxPriorityQueue.orderedBy(comparator).create();

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
