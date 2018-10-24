package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;

import java.util.List;

public class ContrastTestUtils {
    protected ContrastTestUtils() {
        throw new UnsupportedOperationException();
    }

    public static List<Contrast> get(int num) {
        ImmutableList.Builder<Contrast> b = ImmutableList.builder();

        for (int i = 1; i < num + 1; i++) {
            b.add(
                    new Contrast(
                            "contrast_" + i,
                            "",
                            AssayGroupFactory.create(
                                    "reference_" + i,
                                    StringUtils.repeat("run", " ", RandomUtils.nextInt(1, 10)).split(" " + "")),
                            AssayGroupFactory.create(
                                    "test_" + i,
                                    StringUtils.repeat("run", " ", RandomUtils.nextInt(1, 10)).split(" ")),
                                    "contrast name " + i));
        }

        return b.build();
    }
}
