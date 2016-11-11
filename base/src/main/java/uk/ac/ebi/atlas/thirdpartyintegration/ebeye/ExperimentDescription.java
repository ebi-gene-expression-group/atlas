package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class ExperimentDescription {

    private String accession;

    private String description;

    public String getAccession() {
        return accession;
    }

    public String getDescription() {
        return description;
    }

    public ExperimentDescription(String accession, String description) {
        this.accession = accession;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExperimentDescription that = (ExperimentDescription) o;

        return accession.equals(that.accession) && description.equals(that.description);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accession, description);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accession", accession)
                .add("description", description)
                .toString();
    }
}
