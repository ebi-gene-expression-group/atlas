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

package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.FactorValue;

import javax.inject.Named;
import java.util.*;

@Named("factorValueUtils")
@Scope("singleton")
public class FactorValueUtils {

    public SortedSetMultimap<String, FactorValue> factorValuesByName(Set<FactorValue> factorValues) {
        // using factor names here for better readability and compatibility with experiment design page
        SortedSetMultimap<String, FactorValue> factorValuesByName = TreeMultimap.create();
        for (FactorValue factorValue : factorValues) {
            factorValuesByName.put(factorValue.getName(),factorValue);
        }
        return factorValuesByName;
    }

    public String formatFactorTypeForDisplay(String queryFactorType) {
        // this formats the default factor type for display on web page
        String result = queryFactorType.replaceAll("_", " ").toLowerCase();
        result = result.substring(0, 1).toUpperCase() + result.substring(1);
        return result;
    }
}