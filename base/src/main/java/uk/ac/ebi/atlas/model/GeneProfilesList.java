package uk.ac.ebi.atlas.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;

public class GeneProfilesList<T extends Profile> extends ArrayList<T> {

    private static final long serialVersionUID = -1678371004778942235L;

    private Integer totalResultCount = 0;

    public GeneProfilesList(Collection<T> collection) {
        super(collection);
    }

    public GeneProfilesList() {
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        checkArgument(0 <= toIndex, "Upper index value must be larger than 0");
        if (toIndex > size()) {
            return this;
        }
        return super.subList(fromIndex, toIndex);
    }

    public Integer getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(int totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    //test only
    public ImmutableList<String> extractGeneNames() {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (T profile : this) {
            builder.add(profile.getName());
        }
        return builder.build();
    }

    // add all from queue, in order they come off the queue
    public void addAll(Queue<T> queue) {
        T profile;
        while ((profile = queue.poll()) != null) {
            this.add(profile);
        }
    }

    public Map<String,String> properties(){
        return ImmutableMap.of("searchResultTotal", Integer.toString(getTotalResultCount()));
    }

}
