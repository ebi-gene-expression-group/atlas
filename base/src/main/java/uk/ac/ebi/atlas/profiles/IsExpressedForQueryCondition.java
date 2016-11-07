package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Set;

public class IsExpressedForQueryCondition<K, P extends Profile> implements Predicate<P> {
    private Set<K> queryConditions;

    public IsExpressedForQueryCondition(Set<K> queryConditions){
        this.queryConditions = queryConditions;
    }

    @Override
    public boolean apply(P profile) {
        return CollectionUtils.isEmpty(queryConditions) || profile.isExpressedOnAnyOf(queryConditions);
    }

}