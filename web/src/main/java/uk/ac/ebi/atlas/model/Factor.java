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

package uk.ac.ebi.atlas.model;

import java.util.Objects;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class Factor implements Comparable<Factor> {

    public static enum FactorType {
        ORGANISM_PART, CELL_LINE, CELLULAR_COMPONENT, MATERIAL_TYPE
    }

    private String type;

    private String name;

    public Factor(String type, String name) {
        this.type = checkNotNull(type).replaceAll(" ", "_");
        this.name = checkNotNull(name);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Factor other = (Factor) obj;
        return Objects.equals(this.type, other.type) && Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return toStringHelper(this).addValue(name).toString();
    }

    @Override
    public int compareTo(Factor factor) {
        return name.compareTo(factor.name);
    }

}