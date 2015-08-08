package uk.ac.ebi.atlas.trader;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.SortedSet;

/**
 * Created with IntelliJ IDEA.
 * User: barrera
 */
@Named
@Scope("singleton")
public class ArrayDesignTrader {

    private Map<String, String> arrayDesignMap;

    @Inject
    public ArrayDesignTrader(ArrayDesignDAO arrayDesignDAO) {
        this.arrayDesignMap = arrayDesignDAO.getArrayDesignMapNames();
    }

    public String getArrayDesignByName(String arrayDesignAccession) {
        return arrayDesignMap.get(arrayDesignAccession);
    }

    public Map<String, String> getArrayDesignMap() {
        return arrayDesignMap;
    }

    public String getArrayDesignAccession(String value) {
        for(Map.Entry<String, String> entry : arrayDesignMap.entrySet()) {
            if(value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        return null;
    }

    public SortedSet<String> getArrayDesignNames(SortedSet<String> arrayDesignAccessions) {
        SortedSet<String> arrayDesignNames = Sets.newTreeSet();

        for(String arrayDesign : arrayDesignAccessions) {
            arrayDesignNames.add(getArrayDesignByName(arrayDesign));
        }

        return arrayDesignNames;
    }

}
