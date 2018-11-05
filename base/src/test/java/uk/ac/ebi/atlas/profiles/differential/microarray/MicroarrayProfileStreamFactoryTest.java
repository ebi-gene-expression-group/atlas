package uk.ac.ebi.atlas.profiles.differential.microarray;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.arraydesign.ArrayDesign;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.ContrastTestUtils;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperimentTest;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.profiles.SelectProfiles;
import uk.ac.ebi.atlas.profiles.stream.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(value = MockitoJUnitRunner.class)
public class MicroarrayProfileStreamFactoryTest {
    private static final String GENE_ID = "gene_id";
    private static final String GENE_NAME = "gene_name";
    private static final String DESIGN_ELEMENT = "design_element";
    private static final String GENE_ID_2 = "gene_id_2";
    private static final String GENE_NAME_2 = "gene_name_2";
    private static final String DESIGN_ELEMENT_2 = "design_element_2";
    private static final String P_VALUE = "6.4460598240872E-6";
    private static final String FOLD_CHANGE = "-1.68509426666667";
    private static final String T_STAT = "-20.5528971215852";

    private static final String P_VALUE_2 = "0.0004000391565989";
    private static final String T_STAT_2 = "-9.36995510274818";
    private static final String FOLD_CHANGE_2 = "-0.788061466666666";

    @Test
    public void twoRowsWithOneProfileEach() {
        String header =
                Joiner.on("\t").join(
                        new String[] {
                                "gene id", "gene name", "design element", "contrast_1.p-value", "contrast_1.t-stat",
                                "contrast_1.fold-change"});
        String line1 =
                Joiner.on("\t").join(
                        new String[] {GENE_ID, GENE_NAME, DESIGN_ELEMENT, P_VALUE, T_STAT, FOLD_CHANGE});
        String line2 =
                Joiner.on("\t").join(
                        new String[] {GENE_ID_2, GENE_NAME_2, DESIGN_ELEMENT_2, P_VALUE_2, T_STAT_2, FOLD_CHANGE_2});
        List<Contrast> contrasts = ContrastTestUtils.get(1);
        List<String> lines = ImmutableList.of(header, line1, line2);

        List<MicroarrayProfile> sequenceProfiles = loadProfiles(contrasts, lines);
        assertThat(sequenceProfiles.size(), is(2));

        assertThat(sequenceProfiles.get(0).getId(), is(GENE_ID));
        assertThat(sequenceProfiles.get(0).getName(), is(GENE_NAME));
        assertThat(sequenceProfiles.get(0).getSpecificity(), is(1L));

        MicroarrayExpression e00 = sequenceProfiles.get(0).getExpression(contrasts.get(0));
        assertThat(e00.getPValue(), is(Double.parseDouble(P_VALUE)));
        assertThat(e00.getFoldChange(), is(Double.parseDouble(FOLD_CHANGE)));
        assertThat(e00.getTstatistic(), is(Double.parseDouble(T_STAT)));

        assertThat(sequenceProfiles.get(1).getId(), is(GENE_ID_2));
        assertThat(sequenceProfiles.get(1).getName(), is(GENE_NAME_2));
        assertThat(sequenceProfiles.get(1).getSpecificity(), is(1L));
        MicroarrayExpression e10 = sequenceProfiles.get(1).getExpression(contrasts.get(0));
        assertThat(e10.getPValue(), is(Double.parseDouble(P_VALUE_2)));
        assertThat(e10.getFoldChange(), is(Double.parseDouble(FOLD_CHANGE_2)));
        assertThat(e10.getTstatistic(), is(Double.parseDouble(T_STAT_2)));
    }

    @Test
    public void oneRowWithTwoProfilesEach() {
        String header =
                Joiner.on("\t").join(
                        new String[] {
                                "gene id", "gene name", "design element", "contrast_1.p-value", "contrast_1.t-stat",
                                "contrast_1.fold-change", "contrast_2.p-value", "contrast_2.t-stat",
                                "contrast_2.fold-change"});
        String line2Contrasts =
                Joiner.on("\t").join(
                        new String[] {
                                GENE_ID, GENE_NAME, DESIGN_ELEMENT, P_VALUE, T_STAT, FOLD_CHANGE, P_VALUE_2, T_STAT_2,
                                FOLD_CHANGE_2});
        List<Contrast> contrasts = ContrastTestUtils.get(2);
        List<String> lines = ImmutableList.of(header, line2Contrasts);

        List<MicroarrayProfile> sequenceProfiles = loadProfiles(contrasts, lines);
        assertThat(sequenceProfiles.size(), is(1));
        assertThat(sequenceProfiles.get(0).getId(), is(GENE_ID));
        assertThat(sequenceProfiles.get(0).getName(), is(GENE_NAME));
        assertThat(sequenceProfiles.get(0).getSpecificity(), is(2L));

        MicroarrayExpression e00 = sequenceProfiles.get(0).getExpression(contrasts.get(0));
        assertThat(e00.getPValue(), is(Double.parseDouble(P_VALUE)));
        assertThat(e00.getFoldChange(), is(Double.parseDouble(FOLD_CHANGE)));
        assertThat(e00.getTstatistic(), is(Double.parseDouble(T_STAT)));

        MicroarrayExpression e01 = sequenceProfiles.get(0).getExpression(contrasts.get(1));
        assertThat(e01.getPValue(), is(Double.parseDouble(P_VALUE_2)));
        assertThat(e01.getFoldChange(), is(Double.parseDouble(FOLD_CHANGE_2)));
        assertThat(e01.getTstatistic(), is(Double.parseDouble(T_STAT_2)));
    }

    public static GeneProfilesList<MicroarrayProfile> loadProfiles(List<Contrast> contrasts,
                                                                   List<String> sequenceLines) {

        MockDataFileHub dataFileHub = MockDataFileHub.create();

        dataFileHub.addTemporaryFile(
                "magetab/accession/accession_array-design-accession-analytics.tsv", sequenceLines);
        MicroarrayProfileStreamFactory microarrayProfileStreamFactory =
                new MicroarrayProfileStreamFactory(dataFileHub);

        MicroarrayExperiment experiment =
                MicroarrayExperimentTest.get(
                        "accession",
                        ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                        contrasts,
                        ImmutableList.of(ArrayDesign.createForUnknownName("array-design-accession")));
        MicroarrayRequestPreferences microarrayRequestPreferences = new MicroarrayRequestPreferences();
        microarrayRequestPreferences.setFoldChangeCutoff(0.0);
        microarrayRequestPreferences.setCutoff(1.0);

        MicroarrayRequestContext microarrayRequestContext =
                new MicroarrayRequestContext(microarrayRequestPreferences, experiment);

        return microarrayProfileStreamFactory.select(experiment,
                microarrayRequestContext, Collections.emptySet(), x -> true,
                (SelectProfiles<MicroarrayProfile, GeneProfilesList<MicroarrayProfile>>)
                        (profiles, maxSize) ->
                                new DifferentialProfilesList<>(
                                        Lists.newArrayList(new IterableObjectInputStream<>(profiles))));
    }
}
