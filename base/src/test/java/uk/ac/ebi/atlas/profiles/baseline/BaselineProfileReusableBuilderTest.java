package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;

import javax.annotation.Nullable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfileReusableBuilderTest {


    private static final String QUERY_FACTOR_TYPE = "QUERY_FACTOR_TYPE";
    private static final Predicate<BaselineExpression> ALWAYS_FALSE = new Predicate<BaselineExpression>() {
        @Override
        public boolean apply(@Nullable BaselineExpression input) {
            return false;
        }
    };
    private static final String GENE_NAME = "GENE_NAME";
    private static final String GENE_ID = "GENE_ID";

    @Mock
    private FactorGroup factorGroup;

    @Test
    public void addedExpressionsAreFiltered() {
        BaselineProfileReusableBuilder subject = new BaselineProfileReusableBuilder(ALWAYS_FALSE, QUERY_FACTOR_TYPE);
        subject.beginNewInstance(GENE_ID, GENE_NAME);

        subject.addExpression(new BaselineExpression(0.5, factorGroup));

        assertThat(subject.create().isEmpty(), is(true));
    }
}
