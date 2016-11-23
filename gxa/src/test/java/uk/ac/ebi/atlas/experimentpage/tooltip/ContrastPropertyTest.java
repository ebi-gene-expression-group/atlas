
package uk.ac.ebi.atlas.experimentpage.tooltip;

import com.google.common.collect.Sets;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.summary.AssayProperty;
import uk.ac.ebi.atlas.model.experiment.summary.ContrastProperty;
import uk.ac.ebi.atlas.model.experiment.summary.ContrastPropertyType;

import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class ContrastPropertyTest {

    private static final String PROPERTY_NAME = "propertyName";
    private static final String TEST_VALUE = "testValue";
    private static final String REFERENCE_VALUE = "referenceValue";
    private static final ContrastPropertyType CONTRAST_PROPERTY_TYPE = ContrastPropertyType.FACTOR;

    @Test
    public void testGetters() throws Exception {
        ContrastProperty subject = new ContrastProperty(PROPERTY_NAME, TEST_VALUE, REFERENCE_VALUE, CONTRAST_PROPERTY_TYPE);
        assertThat(subject.getContrastPropertyType(), is(CONTRAST_PROPERTY_TYPE));
        assertThat(subject.getPropertyName(), is(PROPERTY_NAME));
        assertThat(subject.getTestValue(), is(TEST_VALUE));
        assertThat(subject.getReferenceValue(), is(REFERENCE_VALUE));
        assertThat(subject.toString(), is("ContrastProperty{propertyName=" + PROPERTY_NAME + ", referenceValue=" + REFERENCE_VALUE + ", testValue=" + TEST_VALUE + "}"));
    }

    @Test
    public void testCompareTo() throws Exception {
        AssayProperty property1 = new ContrastProperty("b", "z", "a", CONTRAST_PROPERTY_TYPE);
        AssayProperty property2 = new ContrastProperty("a", "a", "a", CONTRAST_PROPERTY_TYPE);
        AssayProperty property3 = new ContrastProperty("c", null, "a", CONTRAST_PROPERTY_TYPE);
        AssayProperty property4 = new ContrastProperty("q", "a", "z", CONTRAST_PROPERTY_TYPE);
        AssayProperty property5 = new ContrastProperty("d", "a", "a", CONTRAST_PROPERTY_TYPE);

        SortedSet<AssayProperty> properties = Sets.newTreeSet(Sets.newHashSet(property1, property2, property3, property4, property5));

        assertThat(properties, contains(property2, property1, property3, property5, property4));
    }
}
