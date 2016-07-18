package uk.ac.ebi.atlas.experimentimport.efo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static uk.ac.ebi.atlas.utils.StringUtil.splitAtLastSlash;

@Named
public class EFOLookupService {

    private EFOTreeNodesTrader efoTreeNodesTrader;
    private ImmutableMap<String, EFONode> idToEFONode;

    @Inject
    public EFOLookupService(EFOTreeNodesTrader efoTreeNodesTrader) {
        this.efoTreeNodesTrader = efoTreeNodesTrader;
    }

    private Set<String> getAllParents(String id)
    {
        if (idToEFONode == null) {
            buildIdToEFONodeMap();
        }

        HashSet<String> parentIds = new HashSet<>();

        if (idToEFONode.containsKey(id)) {
            Set<EFONode> parents = idToEFONode.get(id).getParents();
            for (EFONode parent : parents) {
                String parentId = parent.getId().contains("/") ? splitAtLastSlash(parent.getId())[1] : parent.getId();
                parentIds.addAll(getAllParents(parentId));
                parentIds.add(parentId);
            }
        }

        return parentIds;
    }

    public Set<String> getAllParents(Set<String> ids)
    {
        if (idToEFONode == null) {
            buildIdToEFONodeMap();
        }

        HashSet<String> parentIds = new HashSet<>();
        for (String id : ids) {
            parentIds.addAll(getAllParents(id));
        }

        return parentIds;
    }

    public ImmutableSet<String> getLabels(Set<String> ids) {
        if (idToEFONode == null) {
            buildIdToEFONodeMap();
        }

        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (String id : ids) {
            builder.add(idToEFONode.get(id).getTerm());
        }
        return builder.build();
    }


    private void buildIdToEFONodeMap() {
        Map<String, EFONode> urlToEFONode = efoTreeNodesTrader.getTreeNodes();

        ImmutableMap.Builder<String, EFONode> builder = new ImmutableMap.Builder<>();
        for (EFONode efoNode : urlToEFONode.values()) {
            String id = efoNode.getId().contains("/") ? splitAtLastSlash(efoNode.getId())[1] : efoNode.getId();
            builder.put(id, efoNode);
        }
        idToEFONode = builder.build();
    }
}
