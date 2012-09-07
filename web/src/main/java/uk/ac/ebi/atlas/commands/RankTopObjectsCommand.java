package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;

import javax.inject.Named;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

@Named("rankStreamingObjects")
@Scope("prototype")
class RankTopObjectsCommand<E extends Comparable<E>> implements Function<ObjectInputStream<E>, List<E>> {

    private static final int DEFAULT_SIZE = 1000;

    private int size;

    RankTopObjectsCommand() {
        this(DEFAULT_SIZE);
    }

    public RankTopObjectsCommand(int size) {
        this.size = size;
    }

    @Override
    public List<E> apply(ObjectInputStream<E> objectStream) {
        Queue<E> topTenObjects = MinMaxPriorityQueue.orderedBy(new ReverseOrderComparator()).maximumSize(size).create();

        E object;
        while ((object = objectStream.readNext()) != null) {
            topTenObjects.add(object);
        }
        return Ordering.natural().reverse().sortedCopy(topTenObjects);
    }

    public RankTopObjectsCommand<E> setRankSize(int rankingSize) {
        this.size = rankingSize;
        return this;
    }

    class ReverseOrderComparator implements Comparator<E> {

        @Override
        public int compare(E object, E other) {
            return other.compareTo(object);
        }
    }
}
