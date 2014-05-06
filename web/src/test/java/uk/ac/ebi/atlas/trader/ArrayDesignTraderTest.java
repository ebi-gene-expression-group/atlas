package uk.ac.ebi.atlas.trader;


import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static  org.junit.Assert.assertThat;
import javax.inject.Inject;
import java.util.Map;
import java.util.SortedSet;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ArrayDesignTraderTest {

    private static final String AD_ACC1 = "A-AFFY-106";
    private static final String AD_ACC2 = "A-AFFY-128";
    private static final String AD_ACC3 = "A-AFFY-60";

    private SortedSet<String> arrayDesignAccessions;

    @Inject
    private ArrayDesignTrader subject;

    @Before
    public void setUp() throws Exception {
        arrayDesignAccessions = Sets.newTreeSet();
        arrayDesignAccessions.add(AD_ACC1);
        arrayDesignAccessions.add(AD_ACC2);
        arrayDesignAccessions.add(AD_ACC3);

    }

    @Test
    public void testGetArrayDesignsNames() throws Exception {

        SortedSet<String> arrayDesignNames = subject.getArrayDesignNames(arrayDesignAccessions);

        assertThat(arrayDesignAccessions, hasItems("A-AFFY-106", "A-AFFY-128", "A-AFFY-60"));

        assertThat(arrayDesignNames, hasItems("Affymetrix Custom Array - Ciona intestinalis", "Affymetrix GeneChip Bovine Genome Array [Bovine]", "Affymetrix GeneChip C. elegans Genome Array [Celegans]"));
    }

    @Test
    public void testGetArrayDesignMap() throws Exception {

        Map<String, String> arrayDesignMap = subject.getArrayDesignMap();

        assertThat(arrayDesignMap.size(), is(72));

        assertThat(arrayDesignMap.get(AD_ACC1), is("Affymetrix Custom Array - Ciona intestinalis"));

    }

}
