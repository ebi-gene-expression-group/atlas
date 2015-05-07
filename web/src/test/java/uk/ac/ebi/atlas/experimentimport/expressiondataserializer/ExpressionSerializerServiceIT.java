package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 05/05/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ExpressionSerializerServiceIT {

    private static final String E_GEOD_26284 = "E-GEOD-26284";

    @Inject
    private ExpressionSerializerService subject;

    @Test
    public void serializedDataMatchesTSVData() {
        subject.serializeExpressionData(E_GEOD_26284);
        //subject.deserializeExpressionData(E_GEOD_26284);
    }

}