
package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.base.Objects;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;

public class DiffAnalytics {

    private String bioentityId;

    private String bioentityName;

    private String experimentAccession;

    private DifferentialExpression expression;

    private String species;

    public DiffAnalytics(String bioentityId, String bioentityName, String experimentAccession, DifferentialExpression expression,
                         String species) {
        this.bioentityId = bioentityId;
        this.bioentityName = bioentityName;

        this.experimentAccession = experimentAccession;
        this.expression = expression;
        this.species = species;
    }

    public String getBioentityId() {
        return bioentityId;
    }

    public String getContrastDisplayName() {
        return expression.getContrast().getDisplayName();
    }

    public String getContrastId() {
        return expression.getContrast().getId();
    }

    public AssayGroup getContrastTestAssayGroup() {
        return expression.getContrast().getTestAssayGroup();
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public String getExperimentPageUrl() {
        return experimentAccession + "?geneQuery=" + bioentityId + "&queryFactorValues=" + getContrastId() + "&_specific=on";
    }

    public DifferentialExpression getExpression() {
        return expression;
    }

    public String getSpecies() {
        return species;
    }

    public String getBioentityName() {
        return StringUtils.isEmpty(bioentityName) ? bioentityId : bioentityName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiffAnalytics that = (DiffAnalytics) o;
        return Objects.equal(bioentityId, that.bioentityId) &&
                Objects.equal(bioentityName, that.bioentityName) &&
                Objects.equal(experimentAccession, that.experimentAccession) &&
                Objects.equal(species, that.species);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bioentityId, bioentityName, experimentAccession, species);
    }

    @Override
    public String toString() {
        return "DiffAnalytics{" +
                "bioentityId='" + bioentityId + '\'' +
                ", bioentityName='" + bioentityName + '\'' +
                ", experimentAccession='" + experimentAccession + '\'' +
                ", species='" + species + '\'' +
                '}';
    }
}