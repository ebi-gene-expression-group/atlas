package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;

import javax.inject.Named;

@Named
@Scope("singleton")
public class AssayGroupFactorViewModelBuilder {

    public AssayGroupFactorViewModel build(AssayGroupFactor assayGroupFactor) {
        return new AssayGroupFactorViewModel(assayGroupFactor.getAssayGroupId(), assayGroupFactor.getValue(), assayGroupFactor.getValueOntologyTermId());
    }

    public ImmutableList<AssayGroupFactorViewModel> build(Iterable<AssayGroupFactor> assayGroupFactors) {
        ImmutableList.Builder<AssayGroupFactorViewModel> builder = ImmutableList.builder();
        for (AssayGroupFactor assayGroupFactor : assayGroupFactors) {
            AssayGroupFactorViewModel viewModel = build(assayGroupFactor);
            builder.add(viewModel);

        }
        return builder.build();
    }

}
