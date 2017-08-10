package uk.ac.ebi.atlas.web;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.annotation.Nullable;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentPageRequestPreferencesPropertyNamesTest {


    List<String> commonProperties = ImmutableList.of(
            "selectedColumnIds",
            "geneQuery",
            "specific",
            "heatmapMatrixSize",
            "cutoff"

    );

    @Test
    public void testCommonProperties() throws  Exception {
        List<String> baselinePageProperties =
                ImmutableList.<String>builder()
                .addAll(commonProperties)
                .build();


        Set<String> baselineRequestPreferencesProperties =
                FluentIterable.from(Introspector.getBeanInfo(BaselineRequestPreferences.class ).getPropertyDescriptors()).transform(new Function<PropertyDescriptor, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable PropertyDescriptor propertyDescriptor) {
                        return propertyDescriptor.getName();
                    }
                }).toSet();

        for(String requiredProperty: baselinePageProperties){
            assertThat(baselineRequestPreferencesProperties, Matchers.hasItem(requiredProperty));
        }


        Set<String> proteomicsBaselineRequestPreferencesProperties =
                FluentIterable.from(Introspector.getBeanInfo( ProteomicsBaselineRequestPreferences.class ).getPropertyDescriptors()).transform(new Function<PropertyDescriptor, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable PropertyDescriptor propertyDescriptor) {
                        return propertyDescriptor.getName();
                    }
                }).toSet();

        for(String requiredProperty: baselinePageProperties){
            assertThat(proteomicsBaselineRequestPreferencesProperties, Matchers.hasItem(requiredProperty));
        }


        List<String> differentialPageProperties =
                ImmutableList.<String>builder()
                        .addAll(commonProperties)
                        .add("regulation")
                        .add("foldChangeCutoff")
                        .build();

        Set<String> differentialRequestPreferencesProperties =
                FluentIterable.from(Introspector.getBeanInfo(DifferentialRequestPreferences.class ).getPropertyDescriptors()).transform(new Function<PropertyDescriptor, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable PropertyDescriptor propertyDescriptor) {
                        return propertyDescriptor.getName();
                    }
                }).toSet();

        for(String requiredProperty: differentialPageProperties){
            assertThat(differentialRequestPreferencesProperties, Matchers.hasItem(requiredProperty));
        }

    }
}
