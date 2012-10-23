package uk.ac.ebi.atlas.streams;

import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.commons.ObjectInputStreamFilter;
import uk.ac.ebi.atlas.geneannotation.GeneService;
import uk.ac.ebi.atlas.model.GeneProfile;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class GeneProfileInputStreamFilter extends ObjectInputStreamFilter<GeneProfile> {

    private Set<String> geneIDs;

    private GeneService geneService;

    public GeneProfileInputStreamFilter(ObjectInputStream<GeneProfile> geneProfileInputStream, Set<String> geneIDs){
        super(geneProfileInputStream);
        checkNotNull(geneIDs);
        this.geneIDs = toUpperCase(geneIDs);
    }

    public void setGeneService(GeneService geneService) {
        this.geneService = geneService;
    }

    @Override
    protected Predicate<GeneProfile> getAcceptanceCriteria() {

        return new Predicate<GeneProfile>() {
            @Override
            public boolean apply(GeneProfile profile) {
                return CollectionUtils.isEmpty(geneIDs)
                        || geneIDs.contains(profile.getGeneId().toUpperCase())
                || (geneService!=null && geneIDs.contains(geneService.getGeneName(profile.getGeneId()).toUpperCase()));
            }
        };

    }

    private Set<String> toUpperCase(Set<String> geneIDs) {
        Set<String> result = new HashSet<>(geneIDs.size());
        for (String s : geneIDs) {
            result.add(s.toUpperCase());
        }
        return  result;
    }
}