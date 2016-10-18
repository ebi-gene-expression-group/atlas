
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