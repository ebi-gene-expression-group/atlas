package uk.ac.ebi.atlas.experimentimport.coexpression;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BaselineCoexpression implements Comparable<BaselineCoexpression> {
    static BaselineCoexpression create( double ceStatistic, String ceGeneID) {
        return new AutoValue_BaselineCoexpression(ceStatistic, ceGeneID);
    }

    public abstract double ceStatistic();
    public abstract String ceGeneID();

    @Override
    public int compareTo(BaselineCoexpression other) {

        int c= Double.compare(this.ceStatistic(), other.ceStatistic());

        return c == 0
                ? this.ceGeneID().compareTo(other.ceGeneID())
                : c;
    }
}
