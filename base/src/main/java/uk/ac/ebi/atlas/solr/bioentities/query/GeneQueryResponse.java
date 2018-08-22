package uk.ac.ebi.atlas.solr.bioentities.query;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class GeneQueryResponse {

    private Multimap<String, String> geneIdsByQueryTerm = LinkedHashMultimap.create();

    public GeneQueryResponse addGeneIds(String queryTerm, Set<String> geneIds) {
        checkArgument(StringUtils.isNotBlank(queryTerm));
        if (!geneIdsByQueryTerm.containsKey(queryTerm)) {

            if (!geneIds.isEmpty()) {
                geneIdsByQueryTerm.putAll(queryTerm, geneIds);
            }

        }
        return this;
    }

    public Set<String> getAllGeneIds() {
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
