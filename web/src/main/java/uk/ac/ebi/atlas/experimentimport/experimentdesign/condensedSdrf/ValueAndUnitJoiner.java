/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.experimentimport.experimentdesign.condensedSdrf;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;

@Named
public class ValueAndUnitJoiner {

    // Units
    private static final Set<String> UNITS = Sets.newHashSet();

    static {
        UNITS.add("year");
        UNITS.add("month");
        UNITS.add("week");
        UNITS.add("day");
        UNITS.add("mole");
        UNITS.add("micromole");
        UNITS.add("gram");
        UNITS.add("kilogram");
        UNITS.add("other");
        UNITS.add("percent");
        UNITS.add("molar");
    }

    // Units containing the following values should never be pluralised when being joined to factor values
    private static final Set<String> NON_PLURALISABLE_UNITS = Sets.newHashSet();

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

    public String pluraliseAndJoin(String value, String unit) {
        if (!Strings.isNullOrEmpty(unit) && UNITS.contains(unit)) {
            unit = translateUnitToEFOIfApplicable(unit.trim());
            value = value.trim();
            return Joiner.on(" ").join(value, pluraliseUnitIfApplicable(unit, value));
        } else {
            return value;
        }
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
     * - else unless the units already ends in "s", pluralise the whole unit.
     * <p/>
     * See the junit test case of this method for the full list of test cases.
     * <p/> c.f. examples of units in EFO in ticket 3356:
     * MAGE-OM_to_EFO_Units.txt
     * OtherUnitsMappedToEFO.txt
     *
     * @param unit
     * @param value
     * @return
     */
    String pluraliseUnitIfApplicable(String unit, String value) {
        try {
            if (StringUtils.isBlank(value) || NumberUtils.isDigits(value) && Integer.parseInt(value) == 1){
                return unit;
            }
        } catch (NumberFormatException e) {
            throw new IllegalStateException(e);
        }

        if (StringUtils.isNotBlank(unit) && isPluralisable(unit)) {
            int idx = unit.indexOf(PER);
            if (idx != -1) {
                return pluralise(unit.substring(0, idx - 1).trim()) + " " + unit.substring(idx);
            } else if (unit.startsWith(DEGREE) && !unit.equals(DEGREE + "s")) {
                return pluralise(DEGREE) + unit.substring(DEGREE.length());
            } else {
                return pluralise(unit);
            }
        }

        return unit;
    }

    /**
     * @param unit
     * @return true if unit should be pluralised; false otherwise
     */
    boolean isPluralisable(String unit) {
        for (String unitVal : NON_PLURALISABLE_UNITS) {
            if (unit.contains(unitVal)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param word
     * @return Word pluralised according to a naive pluralisation definition - this method does not implement an exhaustive
     *         English language  pluralisation method but is designed to cover the universe of units used in Atlas only.
     */
    String pluralise(String word) {
        if (!word.endsWith("s")) {
            if (INCH.equals(word)) {
                return word + "es";
            } else {
                return word + "s";
            }
        }
        return word;
    }

    /**
     * @param unit
     * @return an EFO term, corresponding to unit - if unit is a key in TRANSLATED_UNITS; else unit itself
     */
    private String translateUnitToEFOIfApplicable(String unit) {
        if (TRANSLATED_UNITS.containsKey(unit)) {
            return TRANSLATED_UNITS.get(unit);
        }
        return unit;
    }

    private boolean isValid(String unit) {
        return true;
    }
}
