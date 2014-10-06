package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

public class AssayGroupFactorViewModel {

    private final String assayGroupId;
    private final String factorValue;
    private final String factorValueOntologyTermId;

    public AssayGroupFactorViewModel(String assayGroupId, String factorValue, String factorValueOntologyTermId) {
        this.assayGroupId = assayGroupId;
        this.factorValue = factorValue;
        this.factorValueOntologyTermId = factorValueOntologyTermId;
    }
}
