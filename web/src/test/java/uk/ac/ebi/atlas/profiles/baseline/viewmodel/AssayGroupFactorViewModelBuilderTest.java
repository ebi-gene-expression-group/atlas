package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.util.StringUtils;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.baseline.Factor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AssayGroupFactorViewModelBuilderTest {

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final Factor ADIPOSE = new Factor(ORGANISM_PART, "adipose", new OntologyTerm("ontologyTerm"));
    private static final Factor ADRENAL = new Factor(ORGANISM_PART, "adrenal");
    private static final Factor BRAIN = new Factor(ORGANISM_PART, "brain");
    private static final Factor BREAST = new Factor(ORGANISM_PART, "breast");

    private static final AssayGroupFactor G1_ADIPOSE = new AssayGroupFactor("g1", ADIPOSE);
    private static final AssayGroupFactor G2_ADRENAL = new AssayGroupFactor("g2", ADRENAL);
    private static final AssayGroupFactor G3_BRAIN = new AssayGroupFactor("g3", BRAIN);
    private static final AssayGroupFactor G4_BREAST = new AssayGroupFactor("g4", BREAST);

    private static final ImmutableSortedSet<AssayGroupFactor> ASSAY_GROUP_FACTORS = ImmutableSortedSet.of(G1_ADIPOSE, G2_ADRENAL, G3_BRAIN, G4_BREAST);

    private AssayGroupFactorViewModelBuilder subject = new AssayGroupFactorViewModelBuilder();

    @Test
    public void buildAssayGroupFactorsViewModel() {
        ImmutableList<AssayGroupFactorViewModel> viewModels = subject.build(ASSAY_GROUP_FACTORS);

        Gson gson = new Gson();
        String json = gson.toJson(viewModels);

        String expected = "[\n" +
                "  {\n" +
                "    \"assayGroupId\": \"g1\",\n" +
                "    \"factorValue\": \"adipose\",\n" +
                "    \"factorValueOntologyTermId\": \"ontologyTerm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"assayGroupId\": \"g2\",\n" +
                "    \"factorValue\": \"adrenal\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"assayGroupId\": \"g3\",\n" +
                "    \"factorValue\": \"brain\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"assayGroupId\": \"g4\",\n" +
                "    \"factorValue\": \"breast\"\n" +
                "  }\n" +
                "]";

        assertThat(json, is(StringUtils.trimAllWhitespace(expected)));
    }

}