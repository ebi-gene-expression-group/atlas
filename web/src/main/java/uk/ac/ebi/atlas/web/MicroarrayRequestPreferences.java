
package uk.ac.ebi.atlas.web;


import com.google.common.base.Objects;

public class MicroarrayRequestPreferences extends DifferentialRequestPreferences {

    private String arrayDesignAccession;

    public String getArrayDesignAccession() {
        return arrayDesignAccession;
    }

    public void setArrayDesignAccession(String arrayDesignAccession) {
        this.arrayDesignAccession = arrayDesignAccession;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(super.toString())
                .add("arrayDesignAccession", arrayDesignAccession)
                .toString();
    }
}
