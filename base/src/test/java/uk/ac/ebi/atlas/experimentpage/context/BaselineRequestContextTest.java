package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaselineRequestContextTest {


    @Test
    public void singleFactorExperimentHasSimpleLabels() throws Exception {
        String defaultQueryFactorType = "defaultQueryFactorType";

        Collection<Factor> defaultFactorValues = ImmutableSet.of();
        List<String> prescribedOrderOfFilters = ImmutableList.of(defaultQueryFactorType);

        AssayGroup ag1 = new AssayGroup("g1", "run11");
        AssayGroup ag2 = new AssayGroup("g2", "run21");
        List<AssayGroup> dataColumnDescriptors = ImmutableList.of(ag1, ag2);

        ExperimentDesign experimentDesign = mock(ExperimentDesign.class);

        FactorSet factors1 = new FactorSet();
        factors1.add(new Factor(defaultQueryFactorType, "name for g1"));

        when(experimentDesign.getFactors("run11")).thenReturn(factors1);

        FactorSet factors2 = new FactorSet();
        factors2.add(new Factor(defaultQueryFactorType, "name for g2"));

        when(experimentDesign.getFactors("run21")).thenReturn(factors2);

        BaselineRequestContext subject = new BaselineRequestContext(new BaselineRequestPreferences(),
                BaselineExperimentTest.mockExperiment(experimentDesign, dataColumnDescriptors,
                        ExperimentDisplayDefaults.create(defaultFactorValues, prescribedOrderOfFilters),  "accession"));

        assertThat(subject.displayNameForColumn(ag1), (is("name for g1")));
        assertThat(subject.displayNameForColumn(ag2), (is("name for g2")));
    }

    @Test
    public void multiFactorExperimentWhereDisplayedColumnsShareAFactorShowsOnlyTheDifferentPart() throws Exception {
        String defaultQueryFactorType = "defaultQueryFactorType";
        String otherType = "otherQueryFactorType";

        Collection<Factor> defaultFactorValues = ImmutableSet.of(new Factor(otherType, "defaultValueForOtherType"));
        List<String> prescribedOrderOfFilters = ImmutableList.of(defaultQueryFactorType);

        AssayGroup ag1 = new AssayGroup("g1", "run11");
        AssayGroup ag2 = new AssayGroup("g2", "run21");
        List<AssayGroup> dataColumnDescriptors = ImmutableList.of(ag1, ag2);

        ExperimentDesign experimentDesign = mock(ExperimentDesign.class);

        FactorSet factors1 = new FactorSet();
        factors1.add(new Factor(defaultQueryFactorType, "name for g1"));
        factors1.add(new Factor(otherType, "otherTypeValue"));

        when(experimentDesign.getFactors("run11")).thenReturn(factors1);

        FactorSet factors2 = new FactorSet();
        factors2.add(new Factor(defaultQueryFactorType, "name for g2"));
        factors2.add(new Factor(otherType, "otherTypeValue"));

        when(experimentDesign.getFactors("run21")).thenReturn(factors2);

        BaselineRequestContext subject = new BaselineRequestContext(new BaselineRequestPreferences(),
                BaselineExperimentTest.mockExperiment(experimentDesign, dataColumnDescriptors,
                        ExperimentDisplayDefaults.create(defaultFactorValues, prescribedOrderOfFilters),  "accession"));

        assertThat(subject.displayNameForColumn(ag1), (is("name for g1")));
        assertThat(subject.displayNameForColumn(ag2), (is("name for g2")));
    }

    @Test
    public void whenViewIsAllFlatAndAllFactorsDifferWeShowThemInPrescribedOrder() throws Exception {
        String defaultQueryFactorType = "defaultQueryFactorType";
        String otherType = "otherQueryFactorType";

        Collection<Factor> defaultFactorValues = ImmutableSet.of(new Factor(otherType, "defaultValueForOtherType"));
        List<String> prescribedOrderOfFilters = ImmutableList.of(defaultQueryFactorType, otherType);

        AssayGroup ag1 = new AssayGroup("g1", "run11");
        AssayGroup ag2 = new AssayGroup("g2", "run21");
        List<AssayGroup> dataColumnDescriptors = ImmutableList.of(ag1, ag2);

        ExperimentDesign experimentDesign = mock(ExperimentDesign.class);

        FactorSet factors1 = new FactorSet();
        factors1.add(new Factor(defaultQueryFactorType, "name for g1"));
        factors1.add(new Factor(otherType, "other type value 1"));

        when(experimentDesign.getFactors("run11")).thenReturn(factors1);

        FactorSet factors2 = new FactorSet();
        factors2.add(new Factor(defaultQueryFactorType, "name for g2"));
        factors2.add(new Factor(otherType, "other type value 2"));

        when(experimentDesign.getFactors("run21")).thenReturn(factors2);

        BaselineRequestContext subject = new BaselineRequestContext(new BaselineRequestPreferences(),
                BaselineExperimentTest.mockExperiment(experimentDesign, dataColumnDescriptors,
                        ExperimentDisplayDefaults.create(defaultFactorValues, prescribedOrderOfFilters),  "accession"));

        assertThat(subject.displayNameForColumn(ag1), (is("name for g1, other type value 1")));
        assertThat(subject.displayNameForColumn(ag2), (is("name for g2, other type value 2")));

        subject = new BaselineRequestContext(new BaselineRequestPreferences(),
                BaselineExperimentTest.mockExperiment(experimentDesign, dataColumnDescriptors,
                        ExperimentDisplayDefaults.create(defaultFactorValues, Lists.reverse(prescribedOrderOfFilters)),  "accession"));

        assertThat(subject.displayNameForColumn(ag1), (is("other type value 1, name for g1")));
        assertThat(subject.displayNameForColumn(ag2), (is("other type value 2, name for g2")));
    }

    @Ignore
    public void whenSomeTypesAreTheSameAcrossTheSetTheyDoNotGoIntoTheName() throws Exception {
        String defaultQueryFactorType = "defaultQueryFactorType";
        String otherType = "otherQueryFactorType";

        Collection<Factor> defaultFactorValues = ImmutableSet.of(new Factor(otherType, "defaultValueForOtherType"));
        List<String> prescribedOrderOfFilters = ImmutableList.of(defaultQueryFactorType, otherType);

        AssayGroup ag1 = new AssayGroup("g1", "run11");
        AssayGroup ag2 = new AssayGroup("g2", "run21");
        List<AssayGroup> dataColumnDescriptors = ImmutableList.of(ag1, ag2);

        ExperimentDesign experimentDesign = mock(ExperimentDesign.class);

        FactorSet factors1 = new FactorSet();
        factors1.add(new Factor(defaultQueryFactorType, "name for g1"));
        factors1.add(new Factor(otherType, "other type value"));

        when(experimentDesign.getFactors("run11")).thenReturn(factors1);

        FactorSet factors2 = new FactorSet();
        factors2.add(new Factor(defaultQueryFactorType, "name for g2"));
        factors2.add(new Factor(otherType, "other type value"));

        when(experimentDesign.getFactors("run21")).thenReturn(factors2);

        BaselineRequestContext subject = new BaselineRequestContext(new BaselineRequestPreferences(),
                BaselineExperimentTest.mockExperiment(experimentDesign, dataColumnDescriptors,
                        ExperimentDisplayDefaults.create(defaultFactorValues, prescribedOrderOfFilters),  "accession"));

        assertThat(subject.displayNameForColumn(ag1), (is("name for g1")));
        assertThat(subject.displayNameForColumn(ag2), (is("name for g2")));

        subject = new BaselineRequestContext(new BaselineRequestPreferences(),
                BaselineExperimentTest.mockExperiment(experimentDesign, dataColumnDescriptors,
                        ExperimentDisplayDefaults.create(defaultFactorValues, Lists.reverse(prescribedOrderOfFilters)),  "accession"));

        assertThat(subject.displayNameForColumn(ag1), (is("name for g1")));
        assertThat(subject.displayNameForColumn(ag2), (is("name for g2")));
    }

}