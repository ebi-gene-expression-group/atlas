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

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.utils.OntologyTermUtils;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class Factor implements Comparable<Factor>, Serializable {

    private final String header;
    private final String type;
    private final String value;
    private final Set<OntologyTerm> valueOntologyTerms;

    public Factor(String header, String value) {
        this(header, value, new OntologyTerm[0]);
    }

    public Factor(String header, String value, OntologyTerm ... valueOntologyTerms) {
        this.header = header;
        this.type = normalize(checkNotNull(header));
        this.value = checkNotNull(value);
        this.valueOntologyTerms = new ImmutableSet.Builder<OntologyTerm>().add(valueOntologyTerms).build();
    }

    public static String normalize(String type) {
        return type.replaceAll(" ", "_").toUpperCase();
    }

    //same as type but un-normalized
    public String getHeader() {
        return header;
    }

    public String getValue() {
        return value;
    }

    //normalized version of header
    public String getType() {
        return type;
    }

    public @Nullable String getValueOntologyTermId() {
        return valueOntologyTerms.isEmpty() ? null : OntologyTermUtils.joinIds(valueOntologyTerms);
    }

    public @Nullable String getValueOntologyTermUri() {
        return valueOntologyTerms.isEmpty() ? null : OntologyTermUtils.joinURIs(valueOntologyTerms);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, value);
    }

    // header is not part of identity, it is only for display purposes
    // ontology term is not part of identity, it is only used for display purposes (ie: the anatomogram)
    @Override
    public boolean equals(Object other) {
        return Objects.equal(this.getClass(), other.getClass())
                && Objects.equal(this.type, ((Factor) other).type)
                && Objects.equal(this.value, ((Factor) other).value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("value", value)
                .add("valueOntologyTerms", Arrays.asList(valueOntologyTerms))
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

    public static SortedSet<String> getValues(Set<Factor> factors) {
        SortedSet<String> result = new TreeSet<>();
        for (Factor factor : factors) {
            result.add(factor.getValue());
        }
        return result;
    }

    public Set<OntologyTerm> getValueOntologyTerms() {
        return valueOntologyTerms;
    }

    public OntologyTerm[] getValueOntologyTermsAsArray() {
        return valueOntologyTerms.toArray(new OntologyTerm[0]);
    }
}
