package uk.ac.ebi.atlas.streams;

import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.commons.ObjectInputStreamFilter;
import uk.ac.ebi.atlas.model.GeneProfile;

import java.util.HashSet;
import java.util.Set;

public class GeneProfileInputStreamFilter extends ObjectInputStreamFilter<GeneProfile> {

    private Set<String> geneIDs;

    private Set<String> organismParts;

    public GeneProfileInputStreamFilter(ObjectInputStream<GeneProfile> geneProfileInputStream, Set<String> geneIDs, Set<String> organismParts){
        super(geneProfileInputStream);
        this.geneIDs = toUpperCase(geneIDs);
        this.organismParts = organismParts;
    }

    @Override
    protected Predicate<GeneProfile> getAcceptanceCriteria() {

        return new Predicate<GeneProfile>() {
            @Override
            public boolean apply(GeneProfile profile) {
                boolean b = checkGeneId(profile.getGeneId(), profile.getGeneName());
                return b
                        && profile.isExpressedAtMostOn(organismParts);
            }
        };

    }

    private boolean checkGeneId(String geneId, String geneName){
        return CollectionUtils.isEmpty(geneIDs)
                || geneIDs.contains(geneId.toUpperCase())
                || (geneName != null && geneIDs.contains(geneName.toUpperCase()));
    }

    private Set<String> toUpperCase(Set<String> strings) {
        Set<String> capitalizedStrings = new HashSet<>();
        if (strings != null) {
            for (String s : strings) {
                capitalizedStrings.add(s.toUpperCase());
            }
        }
        return  capitalizedStrings;
    }
}