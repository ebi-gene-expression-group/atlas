package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineProfileDeserializer;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class BaselineProfileDeserializerTest {


    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final Factor ADIPOSE = new Factor(ORGANISM_PART, "adipose");
    private static final Factor ADRENAL_GLAND = new Factor(ORGANISM_PART, "adrenal gland");
    private static final Factor BRAIN = new Factor(ORGANISM_PART, "brain");
    private static final Factor BREAST = new Factor(ORGANISM_PART, "breast");

    @Test
    public void buildProfiles() {

        String factorType = "organism part";
        String factorValues = "adipose, adrenal gland, brain, breast";
        String profileLines = "ENSG00000082258\tCCNT2\t3\t9\t5\t11\n" +
                               "ENSG00000047315\tPOLR2B\t28\t47\t0.3\t25";

        ImmutableList<BaselineProfile> baselineProfiles = BaselineProfileDeserializer.buildProfiles(factorType, factorValues, profileLines, 0.5D);

        assertThat(baselineProfiles, hasSize(2));

        BaselineProfile profile1 = baselineProfiles.get(0);
        BaselineProfile profile2 = baselineProfiles.get(1);

        assertThat(profile1.getId(), is("ENSG00000082258"));
        assertThat(profile1.getName(), is("CCNT2"));
        assertThat(profile1.getMinExpressionLevel(), is(3D));
        assertThat(profile1.getMaxExpressionLevel(), is(11D));
        assertThat(profile1.getKnownExpressionLevel(ADIPOSE), is(3D));
        assertThat(profile1.getKnownExpressionLevel(ADRENAL_GLAND), is(9D));
        assertThat(profile1.getKnownExpressionLevel(BRAIN), is(5D));
        assertThat(profile1.getKnownExpressionLevel(BREAST), is(11D));

        assertThat(profile2.getId(), is("ENSG00000047315"));
        assertThat(profile2.getName(), is("POLR2B"));
        assertThat(profile2.getMinExpressionLevel(), is(25D));
        assertThat(profile2.getMaxExpressionLevel(), is(47D));
        assertThat(profile2.getKnownExpressionLevel(ADIPOSE), is(28D));
        assertThat(profile2.getKnownExpressionLevel(ADRENAL_GLAND), is(47D));
        assertThat(profile2.getKnownExpressionLevel(BREAST), is(25D));
        assertThat(profile2.getKnownExpressionLevel(BRAIN), is(nullValue()));

    }


}
