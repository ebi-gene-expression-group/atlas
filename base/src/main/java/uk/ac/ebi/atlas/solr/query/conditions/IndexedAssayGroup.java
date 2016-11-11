package uk.ac.ebi.atlas.solr.query.conditions;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class IndexedAssayGroup {

    private String experimentAccession;

    private String assayGroupOrContrastId;

    public IndexedAssayGroup(String experimentAccession, String assayGroupOrContrastId) {
        this.experimentAccession = experimentAccession;
        this.assayGroupOrContrastId = assayGroupOrContrastId;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public String getAssayGroupOrContrastId() {
        return assayGroupOrContrastId;
    }

    @Override
    public int hashCode() {return Objects.hash(experimentAccession, assayGroupOrContrastId);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final IndexedAssayGroup other = (IndexedAssayGroup) obj;
        return Objects.equals(this.experimentAccession, other.experimentAccession) && Objects.equals(this.assayGroupOrContrastId, other.assayGroupOrContrastId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("experimentAccession", experimentAccession)
                .add("assayGroupOrContrastId", assayGroupOrContrastId)
                .toString();
    }
}

