package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Collection;

public class IsGeneIdMatch implements Predicate<Profile> {
    private Collection<String> uppercaseGeneIDs;

    public IsGeneIdMatch(Collection<String> uppercaseGeneIDs) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(uppercaseGeneIDs));
        this.uppercaseGeneIDs = uppercaseGeneIDs;
    }

    @Override
    public boolean apply(Profile geneProfile) {
        return uppercaseGeneIDs.contains(geneProfile.getId().toUpperCase());
    }
}