package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class GeneQueryResponse {

    private Multimap<String, String> geneIdsByQueryTerm = LinkedHashMultimap.create();

    private boolean containsGeneSets;

    public GeneQueryResponse addGeneIds(String queryTerm, Set<String> geneIds){
        checkArgument(StringUtils.isNotBlank(queryTerm));
        if(!geneIdsByQueryTerm.containsKey(queryTerm)) {
            checkArgument(!geneIdsByQueryTerm.containsKey(queryTerm));

            if (!geneIds.isEmpty()) {
                geneIdsByQueryTerm.putAll(queryTerm, geneIds);
            }

            if (geneIds.size() > 1) {
                containsGeneSets = true;
            }
        }
        return this;
    }

    public boolean containsGeneSets() {
        return this.containsGeneSets;
    }

    public ImmutableSetMultimap<String, String> getQueryTermsToIds() {
        return ImmutableSetMultimap.copyOf(geneIdsByQueryTerm);
    }

    public Set<String> getAllGeneIds(){
        return Sets.newHashSet(geneIdsByQueryTerm.values());
    }

    public boolean isEmpty() {
        return geneIdsByQueryTerm.isEmpty();
    }

    public Set<String> getQueryTerms() {
        return geneIdsByQueryTerm.keySet();
    }

    public boolean containsEntry(String queryTerm, String id) {
        return geneIdsByQueryTerm.containsEntry(queryTerm, id);
    }

    public Collection<String> getIds(String queryTerm) {
        return geneIdsByQueryTerm.get(queryTerm);
    }

}
