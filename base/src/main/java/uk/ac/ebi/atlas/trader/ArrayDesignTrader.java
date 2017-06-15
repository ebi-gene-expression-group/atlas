package uk.ac.ebi.atlas.trader;

import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import com.google.common.collect.Sets;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

@Named
public class ArrayDesignTrader {

    private Map<String, String> arrayDesignMap;

    @Inject
    public ArrayDesignTrader(ArrayDesignDAO arrayDesignDAO) {
        this(arrayDesignDAO.getArrayDesignMapNames());
    }

    ArrayDesignTrader(Map<String, String> arrayDesignMap){
        this.arrayDesignMap=arrayDesignMap;
    }

    public String getArrayDesignByName(String arrayDesignAccession) {
        return arrayDesignMap.containsKey(arrayDesignAccession) ? arrayDesignMap.get(arrayDesignAccession) : arrayDesignAccession;
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

        arrayDesignNames.addAll(arrayDesignAccessions.stream().map(this::getArrayDesignByName).collect(Collectors.toList()));

        return arrayDesignNames;
    }

}
