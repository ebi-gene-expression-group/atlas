package uk.ac.ebi.atlas.search.EFO;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;
import uk.ac.ebi.atlas.commons.efo.EFOTreeNodesTrader;
import uk.ac.ebi.atlas.model.OntologyTerm;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Named
@Scope("singleton")
public class EFOIdToTermMapper {

    private EFOTreeNodesTrader efoTreeNodesTrader;
    private ImmutableMap<String, String> termToId;

    @Inject
    public EFOIdToTermMapper(EFOTreeNodesTrader efoTreeNodesTrader) {
        this.efoTreeNodesTrader = efoTreeNodesTrader;
    }

    public Set<String> getIdsForTermSubstring(String substring) {
        if (termToId == null) {
            buildTermToIdMap();
        }

        Set<String> efoIds = new HashSet<>();
        for (String efoTerm : termToId.keySet()) {
            if (StringUtils.containsIgnoreCase(efoTerm, substring)) {
                efoIds.add(termToId.get(efoTerm));
            }
        }

        return efoIds;
    }


    private void buildTermToIdMap() {
        Map<String, EFONode> urlToEFONode = efoTreeNodesTrader.getTreeNodes();

        ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<>();
        for (EFONode efoNode : urlToEFONode.values()) {
            String id = OntologyTerm.splitAtFinalSlash(efoNode.getId())[1];
            builder.put(efoNode.getTerm(), id);
        }
        termToId = builder.build();
    }
}
