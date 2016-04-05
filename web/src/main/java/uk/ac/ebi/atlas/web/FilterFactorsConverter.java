/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.web;

import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("singleton")
public class FilterFactorsConverter {

    private static final String SEPARATOR = ":";

    public static String prettyPrint(Iterable<Factor> factors) {
        if (!factors.iterator().hasNext()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Factor factor : factors) {
            sb.append(factor.getValue()).append(", ");
        }

        sb.delete(sb.length()-2, sb.length());
        return sb.toString();
    }

    public static String serialize(Iterable<Factor> factors) {
        List<String> serializedFactors = new ArrayList<>();
        for (Factor factor : factors) {
            serializedFactors.add(factor.getType() + SEPARATOR + factor.getValue());
        }
        return StringUtils.join(serializedFactors, ",");
    }

    public Set<Factor> deserialize(String csvSerializedFactors) {
        Set<Factor> factors = Sets.newHashSet();

        if (StringUtils.isBlank(csvSerializedFactors)) {
            return factors;
        }

        String[] serializedFactors = csvSerializedFactors.split(",");
        for (String serializedFactor : serializedFactors) {
            String[] split = serializedFactor.split(SEPARATOR);

            checkState(split.length == 2, "serialized Factor string should be like TYPE:value");

            factors.add(new Factor(split[0], split[1]));
        }
        return factors;
    }

}
