package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.MinMaxPriorityQueue;
import uk.ac.ebi.atlas.services.AtlasObjectInputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class RankStreamingObjects<E extends Comparable<E>> implements Function<AtlasObjectInputStream<E>, List<E>>{

    private static final int DEFAULT_SIZE = 100;

    private int size = DEFAULT_SIZE;

    public RankStreamingObjects setSize(int size){
        this.size = size;
        return this;
    }

    @Override
    public List<E> apply(AtlasObjectInputStream<E> objectStream) {
        Queue<E> topTenObjects = MinMaxPriorityQueue.maximumSize(size).create();

        E object;
        do{
            object = objectStream.readNext();
            if (object !=null) {
                topTenObjects.add(object);
            }
        } while (object != null);
        return new ArrayList<E>(topTenObjects);
    }
}
