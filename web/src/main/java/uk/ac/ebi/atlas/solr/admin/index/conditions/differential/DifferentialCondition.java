
package uk.ac.ebi.atlas.solr.admin.index.conditions.differential;

import org.apache.solr.client.solrj.beans.Field;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;

import java.util.Collection;
import java.util.Objects;

public class DifferentialCondition extends Condition {

    @Field("contrast_id")
    private String contrastId;

    //Required by solr
    public DifferentialCondition() {
    }

    public DifferentialCondition(String experimentAccession, String assayGroupId, String contrastId, Collection<String> values) {
        super(experimentAccession, assayGroupId, values);
        this.contrastId = contrastId;
    }

    public String getContrastId() {
        return contrastId;
    }

    public void setContrastId(String contrastId) {
        this.contrastId = contrastId;
    }

    @Override
    public int hashCode() {return Objects.hash(super.hashCode(), contrastId);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final DifferentialCondition other = (DifferentialCondition) obj;
        return Objects.equals(this.getExperimentAccession(), other.getExperimentAccession())
                && Objects.equals(this.getAssayGroupId(), other.getAssayGroupId())
                && Objects.equals(this.contrastId, other.contrastId)
                && Objects.equals(this.getValues(), other.getValues());
    }

}
