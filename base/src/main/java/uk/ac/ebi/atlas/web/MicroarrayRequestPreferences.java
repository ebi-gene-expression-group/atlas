package uk.ac.ebi.atlas.web;

import com.google.common.base.MoreObjects;

public class MicroarrayRequestPreferences extends DifferentialRequestPreferences {
    private String arrayDesignAccession;

    public void setArrayDesignAccession(String arrayDesignAccession) {
        this.arrayDesignAccession = arrayDesignAccession;
    }

    public String getArrayDesignAccession() {
        return arrayDesignAccession;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(super.toString())
                .add("arrayDesignAccession", arrayDesignAccession)
                .toString();
    }
}
