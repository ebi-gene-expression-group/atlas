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

package uk.ac.ebi.atlas.model.differential;

import com.google.common.base.Objects;
import com.google.common.primitives.Booleans;
import org.apache.commons.lang3.StringUtils;

public class ContrastProperty implements Comparable<ContrastProperty> {

    public static enum ContrastPropertyType {
        FACTOR, SAMPLE
    }

    ;

    private String propertyName;
    private String testValue;
    private String referenceValue;
    private ContrastPropertyType contrastPropertyType;

    public ContrastProperty(String propertyName, String testValue, String referenceValue, ContrastPropertyType contrastPropertyType) {
        this.propertyName = propertyName;
        this.testValue = testValue;
        this.referenceValue = referenceValue;
        this.contrastPropertyType = contrastPropertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getTestValue() {
        return testValue;
    }

    public String getReferenceValue() {
        return referenceValue;
    }

    public ContrastPropertyType getContrastPropertyType() {
        return contrastPropertyType;
    }

    public boolean hasEqualValues() {
        return StringUtils.equals(testValue, referenceValue);
    }

    @Override
    public int compareTo(ContrastProperty otherProperty) {
        if (contrastPropertyType != otherProperty.contrastPropertyType) {
            if (contrastPropertyType == ContrastPropertyType.FACTOR) {
                return -1;
            } else {
                return 1;
            }
        } else if (contrastPropertyType == otherProperty.contrastPropertyType &&
                contrastPropertyType == ContrastPropertyType.FACTOR) {
            int groupComparison = Booleans.compare(otherProperty.hasEqualValues(), hasEqualValues());
            if (groupComparison != 0) {
                return groupComparison;
            }
        }
        return propertyName.compareTo(otherProperty.propertyName);
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
