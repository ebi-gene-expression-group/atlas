package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExpressionsQueueTest {

    @Mock
    FactorGroup factorGroup;

    @Test(expected = IllegalArgumentException.class)
    public void checkValuesLengthEqualsHeaderLength() {
        ImmutableList<FactorGroup> factorGroups = ImmutableList.of(factorGroup, factorGroup);

        BaselineExpressionsQueue baselineExpressionsQueue = new BaselineExpressionsQueue(factorGroups);

        baselineExpressionsQueue.reload("1");
    }
}
