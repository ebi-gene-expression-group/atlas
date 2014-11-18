package uk.ac.ebi.atlas.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress.utils.efo.EFOLoader;
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;
import uk.ac.ebi.arrayexpress.utils.efo.IEFO;
import uk.ac.ebi.atlas.model.OntologyTerm;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Named
@Scope("singleton")
public class EFOTreeDAO {

    private String efoOwlFilePath;
    private Map<String, EFONode> efoShortIdMap;
    private Map<String, String> efoTermIdMap;

    @Inject
    public EFOTreeDAO(@Value("#{configuration['efo.owl.file']}") String efoOwlFilePath) {
        this.efoOwlFilePath = efoOwlFilePath;
        try {
            IEFO efo = new EFOLoader().load(new FileInputStream(this.efoOwlFilePath));
            Map<String, EFONode> efoMap = efo.getMap();

            efoShortIdMap = new HashMap<>(efoMap.size());
            efoTermIdMap = new HashMap<>(efoMap.size());
            for (EFONode efoNode : efoMap.values())
            {
                String id = OntologyTerm.splitAtFinalSlash(efoNode.getId())[1];
                efoShortIdMap.put(id, efoNode);
                efoTermIdMap.put(efoNode.getTerm(), id);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getAllParents(String efoNodeShortId)
    {
        HashSet<String> efoNodeShortIdSet = new HashSet<>();

        if (efoShortIdMap.containsKey(efoNodeShortId)) {
            Set<EFONode> parents = efoShortIdMap.get(efoNodeShortId).getParents();
            for (EFONode parent : parents) {
                String parentShortId = OntologyTerm.splitAtFinalSlash(parent.getId())[1];
                efoNodeShortIdSet.addAll(getAllParents(parentShortId));
                efoNodeShortIdSet.add(parentShortId);
            }
        }

        return efoNodeShortIdSet;
    }

    public Set<String> getAllParents(Set<String> efoNodeShortIds)
    {
        HashSet<String> efoNodeShortIdSet = new HashSet<>();
        for (String efoNodeShortId : efoNodeShortIds) {
            efoNodeShortIdSet.addAll(getAllParents(efoNodeShortId));
        }

        return efoNodeShortIdSet;
    }

    public String getIdFromTerm(String term) {
        return efoTermIdMap.get(term);
    }
}
