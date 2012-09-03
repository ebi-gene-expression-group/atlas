package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.MinMaxPriorityQueue;
import uk.ac.ebi.atlas.commons.ObjectInputStream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

class RankStreamingObjects<E extends Comparable<E>> implements Function<ObjectInputStream<E>, List<E>> {

    private static final int DEFAULT_SIZE = 100;

    private int size;

    public RankStreamingObjects() {
        this(DEFAULT_SIZE);
    }

    public RankStreamingObjects(int size) {
        this.size = size;
    }

    @Override
    public List<E> apply(ObjectInputStream<E> objectStream) {
        Queue<E> topTenObjects = MinMaxPriorityQueue.orderedBy(new ReverseOrderComparator()).maximumSize(size).create();

        E object;
        while ((object = objectStream.readNext()) != null) {
            topTenObjects.add(object);
        }

        return new ArrayList<>(topTenObjects);
    }

    class ReverseOrderComparator implements Comparator<E> {

        @Override
        public int compare(E object, E other) {
            return other.compareTo(object);
        }
    }
}
