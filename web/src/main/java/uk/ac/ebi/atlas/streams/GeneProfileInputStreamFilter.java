package uk.ac.ebi.atlas.streams;

import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.commons.ObjectInputStreamFilter;
import uk.ac.ebi.atlas.model.GeneProfile;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class GeneProfileInputStreamFilter extends ObjectInputStreamFilter<GeneProfile> {

    private Set<String> geneIDs;

    public GeneProfileInputStreamFilter(ObjectInputStream<GeneProfile> geneProfileInputStream, Set<String> geneIDs){
        super(geneProfileInputStream);
        this.geneIDs = checkNotNull(geneIDs);
    }

    @Override
    protected Predicate<GeneProfile> getAcceptanceCriteria() {

        return new Predicate<GeneProfile>() {
            @Override
            public boolean apply(GeneProfile profile) {
                return CollectionUtils.isEmpty(geneIDs)
                        || geneIDs.contains(profile.getGeneId());
            }
        };

    }

}