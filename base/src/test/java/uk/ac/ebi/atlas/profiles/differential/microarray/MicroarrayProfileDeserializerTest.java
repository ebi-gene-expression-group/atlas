package uk.ac.ebi.atlas.profiles.differential.microarray;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(value = MockitoJUnitRunner.class)
public class MicroarrayProfileDeserializerTest {

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

    private static final String LINE1 = Joiner.on("\t").join(new String[] {GENE_ID, GENE_NAME, DESIGN_ELEMENT, P_VALUE, T_STAT, FOLD_CHANGE});
    private static final String LINE_2_CONTRASTS = Joiner.on("\t").join(new String[] {GENE_ID, GENE_NAME, DESIGN_ELEMENT, P_VALUE, T_STAT, FOLD_CHANGE, P_VALUE_2, T_STAT_2, FOLD_CHANGE_2});
    private static final String LINE2 = Joiner.on("\t").join(new String[] {GENE_ID_2, GENE_NAME_2, DESIGN_ELEMENT_2, P_VALUE_2, T_STAT_2, FOLD_CHANGE_2});
    private static final String[] LINES = new String[] {LINE1, LINE2};

    @Mock
    private Contrast contrastMock1;

    @Mock
    private Contrast contrastMock2;


    @Mock
    private Predicate<DifferentialExpression> anyExpression;

    @Before
    public void setUp() {
        when(anyExpression.apply(any(DifferentialExpression.class))).thenReturn(true);
    }

    @Test
    public void oneContrast() {

        MicroarrayProfileDeserializer builder = new MicroarrayProfileDeserializer(ImmutableList.of(contrastMock1), anyExpression);
        MicroarrayProfile df = builder.create(LINE1).get();

        assertThat(df.getId(), is(GENE_ID));
        assertThat(df.getName(), is(GENE_NAME));
        assertThat(df.getDesignElementName(), is(DESIGN_ELEMENT));

        MicroarrayExpression expression = df.getExpression(contrastMock1);
        assertThat(expression.getPValue(), is(Double.parseDouble(P_VALUE)));
        assertThat(expression.getFoldChange(), is(Double.parseDouble(FOLD_CHANGE)));
        assertThat(expression.getTstatistic(), is(Double.parseDouble(T_STAT)));

    }

    @Test
    public void twoContrasts() {

        MicroarrayProfileDeserializer builder = new MicroarrayProfileDeserializer(ImmutableList.of(contrastMock1, contrastMock2), anyExpression);
        MicroarrayProfile df = builder.create(LINE_2_CONTRASTS).get();

        assertThat(df.getId(), is(GENE_ID));
        assertThat(df.getName(), is(GENE_NAME));
        assertThat(df.getDesignElementName(), is(DESIGN_ELEMENT));

        MicroarrayExpression expression = df.getExpression(contrastMock1);
        assertThat(expression.getPValue(), is(Double.parseDouble(P_VALUE)));
        assertThat(expression.getFoldChange(), is(Double.parseDouble(FOLD_CHANGE)));
        assertThat(expression.getTstatistic(), is(Double.parseDouble(T_STAT)));

        MicroarrayExpression expression2 = df.getExpression(contrastMock2);
        assertThat(expression2.getPValue(), is(Double.parseDouble(P_VALUE_2)));
        assertThat(expression2.getFoldChange(), is(Double.parseDouble(FOLD_CHANGE_2)));
        assertThat(expression2.getTstatistic(), is(Double.parseDouble(T_STAT_2)));

    }

    @Test
    public void twoProfiles() {

        MicroarrayProfileDeserializer builder = new MicroarrayProfileDeserializer(ImmutableList.of(contrastMock1), anyExpression);
        ImmutableList<MicroarrayProfile> profiles = builder.create(LINES);

        MicroarrayProfile profile = profiles.get(0);
        assertThat(profile.getId(), is(GENE_ID));
        assertThat(profile.getName(), is(GENE_NAME));
        assertThat(profile.getDesignElementName(), is(DESIGN_ELEMENT));

        MicroarrayProfile profile2 = profiles.get(1);
        assertThat(profile2.getId(), is(GENE_ID_2));
        assertThat(profile2.getName(), is(GENE_NAME_2));
        assertThat(profile2.getDesignElementName(), is(DESIGN_ELEMENT_2));

    }

}
