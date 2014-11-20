package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;
import uk.ac.ebi.atlas.commons.efo.EFOTreeNodesTrader;
import uk.ac.ebi.atlas.model.OntologyTerm;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@Scope("singleton")
public class EFOParentsLookupService {

    private ImmutableMap<String, EFONode> idToEFONode;

    @Inject
    public EFOParentsLookupService(EFOTreeNodesTrader efoTreeNodesTrader) {
        Map<String, EFONode> urlToEFONode = efoTreeNodesTrader.getTreeNodes();

        ImmutableMap.Builder<String, EFONode> builder = new ImmutableMap.Builder<>();
        for (EFONode efoNode : urlToEFONode.values()) {
            String id = OntologyTerm.splitAtFinalSlash(efoNode.getId())[1];
            builder.put(id, efoNode);
        }
        idToEFONode = builder.build();
    }

    public Set<String> getAllParents(String id)
    {
        HashSet<String> parentIds = new HashSet<>();

        if (idToEFONode.containsKey(id)) {
            Set<EFONode> parents = idToEFONode.get(id).getParents();
            for (EFONode parent : parents) {
                String parentId = OntologyTerm.splitAtFinalSlash(parent.getId())[1];
                parentIds.addAll(getAllParents(parentId));
                parentIds.add(parentId);
            }
        }

        return parentIds;
    }

    public Set<String> getAllParents(Set<String> ids)
    {
        HashSet<String> parentIds = new HashSet<>();
        for (String id : ids) {
            parentIds.addAll(getAllParents(id));
        }

        return parentIds;
    }
}
