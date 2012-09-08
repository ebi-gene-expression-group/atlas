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

@Named("rankTopObjects")
@Scope("prototype")
public class RankTopObjectsCommand<E extends Comparable<E>> implements Function<ObjectInputStream<E>, List<E>> {

    private static final int DEFAULT_SIZE = 10;

    private int rankingSize = DEFAULT_SIZE;

    public RankTopObjectsCommand() {
        this.rankingSize = rankingSize;
    }

    @Override
    public List<E> apply(ObjectInputStream<E> objectStream) {
        Queue<E> topTenObjects = MinMaxPriorityQueue.orderedBy(new ReverseOrderComparator()).maximumSize(rankingSize).create();

        E object;
        while ((object = objectStream.readNext()) != null) {
            topTenObjects.add(object);
        }
        return Ordering.natural().reverse().sortedCopy(topTenObjects);
    }

    public RankTopObjectsCommand<E> setRankingSize(int rankingSize) {
        this.rankingSize = rankingSize;
        return this;
    }

    class ReverseOrderComparator implements Comparator<E> {

        @Override
        public int compare(E object, E other) {
            return other.compareTo(object);
        }
    }
}
