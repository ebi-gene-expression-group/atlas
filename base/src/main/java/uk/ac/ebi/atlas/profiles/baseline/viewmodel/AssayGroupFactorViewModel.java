package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import uk.ac.ebi.atlas.model.experiment.baseline.AssayGroupFactor;
import com.google.common.collect.ImmutableList;

public class AssayGroupFactorViewModel {

    private final String assayGroupId;
    private final String factorValue;
    private final String factorValueOntologyTermId;

    public AssayGroupFactorViewModel(String assayGroupId, String factorValue, String factorValueOntologyTermId) {
        this.assayGroupId = assayGroupId;
        this.factorValue = factorValue;
        this.factorValueOntologyTermId = factorValueOntologyTermId;
    }

    public AssayGroupFactorViewModel(AssayGroupFactor assayGroupFactor){
        this(assayGroupFactor.getAssayGroupId(), assayGroupFactor.getValue(),
                assayGroupFactor.getValueOntologyTermId());
    }

    public static ImmutableList<AssayGroupFactorViewModel> createList(Iterable<AssayGroupFactor> assayGroupFactors) {
        ImmutableList.Builder<AssayGroupFactorViewModel> builder = ImmutableList.builder();
        for (AssayGroupFactor assayGroupFactor : assayGroupFactors) {
            AssayGroupFactorViewModel viewModel = new AssayGroupFactorViewModel((assayGroupFactor));
            builder.add(viewModel);

        }
        return builder.build();
    }
}
