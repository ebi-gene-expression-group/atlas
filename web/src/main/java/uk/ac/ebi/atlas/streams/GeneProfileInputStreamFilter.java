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
    private Set<String> organismParts;

    public GeneProfileInputStreamFilter(ObjectInputStream geneProfileInputStream, Set<String> geneIDs){
        super(geneProfileInputStream);
        this.geneIDs = checkNotNull(geneIDs);
    }

    @Override
    protected Predicate<GeneProfile> getAcceptanceCriteria() {

        return new Predicate<GeneProfile>() {
            @Override
            public boolean apply(GeneProfile profile) {
                if(CollectionUtils.isEmpty(geneIDs)){
                    return true;
                }
                return geneIDs.contains(profile.getGeneId());
            }
        };

    }

}