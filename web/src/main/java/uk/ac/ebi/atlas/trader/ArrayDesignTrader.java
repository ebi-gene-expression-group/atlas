package uk.ac.ebi.atlas.trader;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.ArrayDesignDao;

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

    private ArrayDesignDao arrayDesignDao;

    @Inject
    public ArrayDesignTrader(ArrayDesignDao arrayDesignDao) {
        this.arrayDesignDao = arrayDesignDao;
        this.arrayDesignMap = arrayDesignDao.getArrayDesignMapNames();
    }

    public String getArrayDesignByName(String arrayDesignAccession) {
        return arrayDesignMap.get(arrayDesignAccession);
    }

    public Map<String, String> getArrayDesignMap() {
        return arrayDesignMap;
    }

    public SortedSet<String> getArrayDesignNames(SortedSet<String> arrayDesignAccessions) {
        SortedSet<String> arrayDesignNames = Sets.newTreeSet();

        for(String arrayDesign : arrayDesignAccessions) {
            arrayDesignNames.add(getArrayDesignByName(arrayDesign));
        }

        return arrayDesignNames;
    }

}
