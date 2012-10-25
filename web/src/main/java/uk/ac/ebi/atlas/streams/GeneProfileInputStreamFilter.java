package uk.ac.ebi.atlas.streams;

import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.commons.ObjectInputStreamFilter;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.GeneProfile;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class GeneProfileInputStreamFilter extends ObjectInputStreamFilter<GeneProfile> {

    private Set<String> geneIDs;

    private Set<String> organismParts;

    private int cutoff;

    private GeneNamesProvider geneNamesProvider;

    public GeneProfileInputStreamFilter(ObjectInputStream<GeneProfile> geneProfileInputStream, Set<String> geneIDs, Set<String> organismParts){
        super(geneProfileInputStream);
        if (geneIDs != null) {
            this.geneIDs = toUpperCase(geneIDs);
        }
        this.organismParts = organismParts;
    }

    public void setGeneNamesProvider(GeneNamesProvider geneNamesProvider) {
        this.geneNamesProvider = geneNamesProvider;
    }

    @Override
    protected Predicate<GeneProfile> getAcceptanceCriteria() {

        return new Predicate<GeneProfile>() {
            @Override
            public boolean apply(GeneProfile profile) {
                return checkGeneId(profile.getGeneId()) && profile.isExpressedAtMostOn(organismParts);
            }
        };

    }

    private boolean checkGeneId(String geneId){
        geneId = geneId.toUpperCase();
        return CollectionUtils.isEmpty(geneIDs)
                || geneIDs.contains(geneId)
                //ToDo: move gene name resolution into gene profile,
                // when gene profile is built if the provider is available the name will be set
                || (geneNamesProvider !=null && geneIDs.contains(geneNamesProvider.getGeneName(geneId)));
    }

    private Set<String> toUpperCase(Set<String> geneIDs) {
        Set<String> result = new HashSet<>(geneIDs.size());
        for (String s : geneIDs) {
            result.add(s.toUpperCase());
        }
        return  result;
    }
}