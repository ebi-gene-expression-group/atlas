package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.MinMaxPriorityQueue;
import uk.ac.ebi.atlas.services.ObjectInputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class RankStreamingObjects<E extends Comparable<E>> implements Function<ObjectInputStream<E>, List<E>>{

    private static final int DEFAULT_SIZE = 100;

    private int size;

    public RankStreamingObjects(){
        this(DEFAULT_SIZE);
    }

    public RankStreamingObjects(int size){
        this.size = size;
    }

    @Override
    public List<E> apply(ObjectInputStream<E> objectStream) {
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
