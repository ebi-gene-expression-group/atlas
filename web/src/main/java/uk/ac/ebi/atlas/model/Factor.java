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

package uk.ac.ebi.atlas.model;

import com.google.common.base.Objects;

import java.util.SortedSet;
import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkNotNull;

public class Factor implements Comparable<Factor> {

    private String type;

    private String name;

    private String value;

    public Factor(String type, String value) {
        this(type, null, value);
    }

    public Factor(String type, String name, String value) {

        this.type = normalize(checkNotNull(type));
        this.name = name;
        this.value = checkNotNull(value).toLowerCase();
    }

    protected final String normalize(String type) {
        return type.replaceAll(" ", "_").toUpperCase();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public Factor setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, value);
    }

    @Override
    public boolean equals(Object other) {
        if (getClass() != other.getClass()) {
            return false;
        }
        return Objects.equal(this.getClass(), other.getClass())
                && Objects.equal(this.type, ((Factor) other).type)
                && Objects.equal(this.value, ((Factor) other).value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("name", name)
                .add("value", value)
                .toString();
    }

    @Override
    public int compareTo(Factor factor) {
        int factorCompare = type.compareTo(factor.type);
        if (factorCompare != 0) {
            return factorCompare;
        }
        return value.compareTo(factor.value);
    }

    public static SortedSet<String> getValues(SortedSet<Factor> factors) {
        SortedSet<String> result = new TreeSet<>();
        for (Factor factor : factors) {
            result.add(factor.getValue());
        }
        return result;
    }

}
