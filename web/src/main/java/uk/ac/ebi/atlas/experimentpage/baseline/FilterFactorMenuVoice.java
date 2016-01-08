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

package uk.ac.ebi.atlas.experimentpage.baseline;

import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.SortedSet;

public class FilterFactorMenuVoice implements Comparable<FilterFactorMenuVoice> {

    private SortedSet<FilterFactorMenuVoice> children;
    private String displayName;
    private String type;
    private Factor factor;

    public FilterFactorMenuVoice(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public int compareTo(FilterFactorMenuVoice o) {
        return this.displayName.compareTo(o.displayName);
    }

    public SortedSet<FilterFactorMenuVoice> getChildren() {
        return children;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isLeaf() {
        return children == null || children.size() == 0;
    }

    public void setChildren(SortedSet<FilterFactorMenuVoice> set) {
        this.children = set;
    }

    public Factor getFactor() {
        return factor;
    }

    public void setFactor(Factor factor) {
        this.factor = factor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if (!isLeaf()) {
            return displayName + "\t" + getChildren().toString();
        }
        return displayName + "\n";
    }

    @Override
    public int hashCode() {
        return displayName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FilterFactorMenuVoice)) {
            return false;
        }
        return displayName.equals(((FilterFactorMenuVoice) obj).getDisplayName());
    }
}