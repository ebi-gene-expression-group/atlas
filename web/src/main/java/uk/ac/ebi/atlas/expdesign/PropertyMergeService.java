package uk.ac.ebi.atlas.expdesign;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class PropertyMergeService {

    // Units containing the following values should never be pluralised when being joined to factor values
    private static final List<String> NON_PLURALISABLE_UNITS = Lists.newArrayList();

    static {
        NON_PLURALISABLE_UNITS.add("other");
        NON_PLURALISABLE_UNITS.add("percent");
        NON_PLURALISABLE_UNITS.add("molar");
    }

    // Units that should end in 'es' rather than 's' in their plural form
    private static final String INCH = "inch";

    // separator in units in which only the first work should be pluralised (e.g. "micromole per kilogram")
    private static final String PER = "per";
    // The only case other than the above in which only the first word should be pluralised (e.g. "degree celsius")
    private static final String DEGREE = "degree";

    // A temporary mapping from MAGE-OM to EFO - for certain units that for operational reasons cannot for the time being arrive
    // into Atlas as EFO units
    private static final Map<String, String> TRANSLATED_UNITS = Maps.newHashMap();

    static {
        TRANSLATED_UNITS.put("K", "kelvin");
        TRANSLATED_UNITS.put("degrees_C", "degree celsius");
        TRANSLATED_UNITS.put("degrees_F", "degree fahrenheit");
    }

    public String mergeValueAndUnit(String value, String unit) {
        String unitValue = translateUnitToEFOIfApplicable(unit);
        if (!isValidEFOTerm(unitValue)) {
            throw new IllegalArgumentException("Unit: " + unitValue + " not found in EFO");
        }
        return Joiner.on(" ").join(value.trim(), pluraliseUnitIfApplicable(unitValue, value));
    }

    /**
     * Pluralise a unit only if:
     * - unit is not empty
     * - factor value it describes is not equal to 1
     * - it is not equal to OTHER or contains PERCENT in it
     * - it does not already end in "s"
     * <p/>
     * Pluralisation method is as follows:
     * - if a unit contains PER, pluralise the term preceding it (unless that term ends in "s" already)
     * - else if a unit starts with DEGREE, pluralise word DEGREE unless the unit starts with DEGREE + "s"
     * - else unless the units already ends in "s", pluralise thh whole unit.
     * <p/>
     * See the junit test case of this method for the full list of test cases.
     * <p/> c.f. examples of units in EFO in ticket 3356:
     * MAGE-OM_to_EFO_Units.txt
     * OtherUnitsMappedToEFO.txt
     *
     * @param unit
     * @param factorValue
     * @return
     */
    protected String pluraliseUnitIfApplicable(String unit, String factorValue) {
        try {
            if (Strings.isNullOrEmpty(factorValue) || Integer.parseInt(factorValue) == 1)
                return unit;
        } catch (NumberFormatException nfe) {
            // quiesce
        }

        if (!Strings.isNullOrEmpty(unit) && isPluralisable(unit)) {
            int idx = unit.indexOf(PER);
            if (idx != -1)
                return pluralise(unit.substring(0, idx - 1).trim()) + " " + unit.substring(idx);
            else if (unit.startsWith(DEGREE) && !unit.equals(DEGREE + "s"))
                return pluralise(DEGREE) + unit.substring(DEGREE.length());
            else
                return pluralise(unit);
        }

        return unit;
    }

    /**
     * @param unit
     * @return true if unit should be pluralised; false otherwise
     */
    protected boolean isPluralisable(String unit) {
        for (String unitVal : NON_PLURALISABLE_UNITS) {
            if (unit.contains(unitVal))
                return false;
        }
        return true;
    }

    /**
     * @param word
     * @return Word pluralised according to a naive pluralisation definition - this method does not implement an exhaustive
     *         English language  pluralisation method but is designed to cover the universe of units used in Atlas only.
     */
    protected String pluralise(String word) {
        if (!word.endsWith("s")) {
            if (INCH.equals(word)) {
                return word + "es";
            } else
                return word + "s";
        }
        return word;
    }

    /**
     * @param unit
     * @return an EFO term, corresponding to unit - if unit is a key in TRANSLATED_UNITS; else unit itself
     */
    private String translateUnitToEFOIfApplicable(String unit) {
        String trimedUnit = unit.trim();
        if (TRANSLATED_UNITS.containsKey(trimedUnit))
            return TRANSLATED_UNITS.get(trimedUnit);
        return trimedUnit;
    }

    protected boolean isValidEFOTerm(String unit) {
        //ToDo: implement when bioportal client is ready
        return true;
    }
}
