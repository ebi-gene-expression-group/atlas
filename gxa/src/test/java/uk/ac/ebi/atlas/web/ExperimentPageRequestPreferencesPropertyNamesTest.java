package uk.ac.ebi.atlas.web;

import com.google.common.collect.ImmutableList;
import org.apache.commons.beanutils.BeanUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentPageRequestPreferencesPropertyNamesTest {
    private static final List<String> COMMON_PROPERTIES = ImmutableList.of(
            "selectedColumnIds",
            "geneQuery",
            "specific",
            "heatmapMatrixSize",
            "cutoff"
    );

    @Test
    public void testCommonProperties() throws  Exception {
        List<String> baselinePageProperties = ImmutableList.copyOf(COMMON_PROPERTIES);

        Set<String> baselineRequestPreferencesProperties =
                Arrays.stream(Introspector.getBeanInfo(BaselineRequestPreferences.class).getPropertyDescriptors())
                        .map(PropertyDescriptor::getName)
                        .collect(toSet());

        for (String requiredProperty: baselinePageProperties) {
            assertThat(baselineRequestPreferencesProperties, Matchers.hasItem(requiredProperty));
        }
        for (String requiredProperty: BeanUtils.describe(new RnaSeqBaselineRequestPreferences()).keySet()) {
            if (!requiredProperty.contains("default")) {
                assertThat(baselineRequestPreferencesProperties, Matchers.hasItem(requiredProperty));
            }
        }

        Set<String> proteomicsBaselineRequestPreferencesProperties =
                Arrays.stream(Introspector.getBeanInfo(
                        ProteomicsBaselineRequestPreferences.class).getPropertyDescriptors())
                        .map(PropertyDescriptor::getName)
                        .collect(toSet());

        for (String requiredProperty: baselinePageProperties) {
            assertThat(proteomicsBaselineRequestPreferencesProperties, Matchers.hasItem(requiredProperty));
        }
        for (String requiredProperty: BeanUtils.describe(new ProteomicsBaselineRequestPreferences()).keySet()) {
            assertThat(proteomicsBaselineRequestPreferencesProperties, Matchers.hasItem(requiredProperty));
        }

        List<String> differentialPageProperties =
                ImmutableList.<String>builder()
                        .addAll(COMMON_PROPERTIES)
                        .add("regulation")
                        .add("foldChangeCutoff")
                        .build();

        Set<String> differentialRequestPreferencesProperties =
                Arrays.stream(Introspector.getBeanInfo(DifferentialRequestPreferences.class).getPropertyDescriptors())
                        .map(PropertyDescriptor::getName)
                        .collect(toSet());

        for (String requiredProperty: differentialPageProperties) {
            assertThat(differentialRequestPreferencesProperties, Matchers.hasItem(requiredProperty));
        }

        for (String requiredProperty:  BeanUtils.describe(new DifferentialRequestPreferences()).keySet()) {
            assertThat(differentialRequestPreferencesProperties, Matchers.hasItem(requiredProperty));
        }
    }
}
