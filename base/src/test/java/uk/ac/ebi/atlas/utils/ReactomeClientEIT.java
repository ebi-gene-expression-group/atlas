package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ReactomeClientEIT {
    private static final Set<String> SET_OF_45_STABLE_IDS = ImmutableSet.of(
            "R-HSA-15869", "R-HSA-37001", "R-HSA-43124", "R-HSA-48888", "R-HSA-49155",
            "R-HSA-49189", "R-HSA-49191", "R-HSA-49291", "R-HSA-49335", "R-HSA-49459",
            "R-HSA-49489", "R-HSA-49491", "R-HSA-49493", "R-HSA-49495", "R-HSA-49699",
            "R-HSA-49701", "R-HSA-49725", "R-HSA-49741", "R-HSA-49743", "R-HSA-49745",
            "R-HSA-49859", "R-HSA-49865", "R-HSA-49925", "R-HSA-49927", "R-HSA-49929",
            "R-HSA-50099", "R-HSA-50119", "R-HSA-50171", "R-HSA-50270", "R-HSA-50320",
            "R-HSA-50498", "R-HSA-50500", "R-HSA-50508", "R-HSA-50514", "R-HSA-50516",
            "R-HSA-50518", "R-HSA-50522", "R-HSA-50662", "R-HSA-50690", "R-HSA-50757",
            "R-HSA-50825", "R-HSA-50845", "R-HSA-50847", "R-HSA-50849", "R-HSA-50851");

    @Inject
    private ReactomeClient subject;

    @Test
    public void getMoreThanMaxQuerySizeIds() {
        assertThat(subject.getPathwayNames(SET_OF_45_STABLE_IDS).entrySet(), hasSize(SET_OF_45_STABLE_IDS.size()));
    }

    @Test
    public void getNonExistingIds() {
        assertThat(subject.getPathwayNames(ImmutableSet.of("R-HSA-FOOBAR")).entrySet(), hasSize(0));
        assertThat(subject.getPathwayName("R-HSA-FOOBAR"), is(Optional.empty()));
    }
}
