package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Collection;
import java.util.Set;

public class IsGeneIdMatch<P extends Profile> implements Predicate<P> {
    private Collection<String> uppercaseGeneIDs;

    public IsGeneIdMatch(Set<String> geneIds) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(geneIds));
        this.uppercaseGeneIDs = upper(geneIds);
    }

    private ImmutableSet<String> upper(Set<String> geneIds) {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        for (String geneId : geneIds) {
            builder.add(geneId.toUpperCase());
        }

        return builder.build();
    }

    @Override
    public boolean apply(P geneProfile) {
        return uppercaseGeneIDs.contains(geneProfile.getId().toUpperCase());
    }
}
