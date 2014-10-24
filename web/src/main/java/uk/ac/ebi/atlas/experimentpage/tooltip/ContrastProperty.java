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

package uk.ac.ebi.atlas.experimentpage.tooltip;

import com.google.common.base.Objects;

public class ContrastProperty extends AssayProperty {

    private String referenceValue;

    public ContrastProperty(String propertyName, String testValue, String referenceValue, ContrastPropertyType contrastPropertyType) {
        super(propertyName, testValue, contrastPropertyType );
        this.referenceValue = referenceValue;
    }

    public String getReferenceValue() {
        return referenceValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContrastProperty)) return false;
        if (!super.equals(o)) return false;

        ContrastProperty that = (ContrastProperty) o;

        if (referenceValue != null ? !referenceValue.equalsIgnoreCase(that.referenceValue) : that.referenceValue != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (referenceValue != null ? referenceValue.toLowerCase().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("propertyName", propertyName)
                .add("referenceValue", referenceValue)
                .add("testValue", testValue)
                .toString();
    }
}
