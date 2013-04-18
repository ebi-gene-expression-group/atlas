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

package uk.ac.ebi.atlas.model.baseline.impl;

import com.google.common.base.Objects;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

import java.util.*;

public class FactorSet implements FactorGroup {

    private Map<String, Factor> factorsByType = new HashMap<>();

    public FactorSet() {}


    public FactorSet add(Factor factor) {
        factorsByType.put(factor.getType(), factor);
        return this;
    }

    @Override
    public Iterator<Factor> iterator() {
        return Iterators.unmodifiableIterator(factorsByType.values().iterator());
    }


    @Override
    public Factor getFactorByType(String type) {
        return factorsByType.get(type);
    }

    @Override
    public boolean containsAll(Set<Factor> factors) {
        return this.factorsByType.values().containsAll(factors);
    }

    @Override
    public boolean equals(Object other) {
        if (getClass() != other.getClass()) {
            return false;
        }
        return Objects.equal(this.getClass(), other.getClass())
                && Objects.equal(this.factorsByType, ((FactorSet) other).factorsByType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(factorsByType);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("factors", factorsByType.values())
                .toString();
    }

    @Override
    public boolean overlapsWith(Collection<Factor> factors) {
        return !Collections.disjoint(factorsByType.values(), factors);
    }

    @Override
    public List<Factor> remove(Collection<Factor> factors) {
        ArrayList<Factor> allFactors = Lists.newArrayList(factorsByType.values());

        allFactors.removeAll(factors);

        return allFactors;
    }

    @Override
    public boolean contains(Factor factor) {
        return factorsByType.containsValue(factor);
    }


}
