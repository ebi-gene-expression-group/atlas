package uk.ac.ebi.atlas.streams.baseline;

import com.google.common.collect.MinMaxPriorityQueue;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfileComparator;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

@Named
@Scope("prototype")
public class RankBaselineProfiles {

    private int size = 50;
    private boolean isSpecific;
    private Set<Factor> queryFactors = Collections.emptySet();
    private Set<Factor> allQueryFactors = Collections.emptySet();
    private Iterable<BaselineProfile> profiles;
    private double cutOff;

    public RankBaselineProfiles profiles(Iterable<BaselineProfile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public RankBaselineProfiles top(int size) {
        this.size = size;
        return this;
    }

    public RankBaselineProfiles cutOff(double cutOff) {
        this.cutOff = cutOff;
        return this;
    }

    public RankBaselineProfiles isSpecific(boolean isSpecific) {
        this.isSpecific = isSpecific;
        return this;
    }

    public RankBaselineProfiles isSpecific() {
        this.isSpecific = true;
        return this;
    }

    public RankBaselineProfiles allQueryFactors(Set<Factor> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
        return this;
    }

    public RankBaselineProfiles queryFactors(Set<Factor> queryFactors) {
        this.queryFactors = queryFactors;
        return this;
    }

    public BaselineProfilesList rank() {

        Comparator<BaselineProfile> comparator = new BaselineProfileComparator(
                isSpecific, queryFactors, allQueryFactors, cutOff);

        MinMaxPriorityQueue<BaselineProfile> rankingQueue =  MinMaxPriorityQueue.
                                                                orderedBy(comparator).
                                                                maximumSize(size).create();

        int count = 0;

        for (BaselineProfile profile : profiles) {
            rankingQueue.add(profile);
            count++;
        }

        BaselineProfilesList list = new BaselineProfilesList();
        list.addAll(rankingQueue);
        list.setTotalResultCount(count);
        return list;

    }

}
