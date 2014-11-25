package uk.ac.ebi.atlas.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.arrayexpress.utils.efo.EFOLoader;
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;
import uk.ac.ebi.arrayexpress.utils.efo.IEFO;

import java.io.FileInputStream;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class EFOLoaderIT {

    private Map<String, EFONode> efoMap;

    @Value("#{configuration['efo.owl.file']}")
    private String efoOwlFilePath;

    private static String EFO_0000001 = "http://www.ebi.ac.uk/efo/EFO_0000001";
    private static String EFO_0002719 = "http://www.ebi.ac.uk/efo/EFO_0002719";

    private static String BFO_0000040 = "http://www.ifomis.org/bfo/1.1/snap#MaterialEntity";

    private static String EFO_0000469 = "http://www.ebi.ac.uk/efo/EFO_0000469";

    private static String EFO_0000273 = "http://www.ebi.ac.uk/efo/EFO_0000273";
    private static String EFO_0000534 = "http://www.ebi.ac.uk/efo/EFO_0000534";
    private static String EFO_0000568 = "http://www.ebi.ac.uk/efo/EFO_0000568";


    @Before
    public void setUp() throws Exception {
        IEFO efo = new EFOLoader().load(new FileInputStream(efoOwlFilePath));
        efoMap = efo.getMap();
    }

    @Test
    public void rootTermHasNoParents()
    {
        assertThat(efoMap.get(EFO_0000001).getParents().isEmpty(), is(true));
    }

    @Test
    public void leafTermHasNoChildren()
    {
        assertThat(efoMap.get(EFO_0002719).getChildren().isEmpty(), is(true));
    }

    @Test
    public void leafTermHasParents()
    {
        assertThat(efoMap.get(EFO_0002719).getParents().isEmpty(), is(false));
    }

    @Test
    public void termParents()
    {
        assertThat(efoMap.get(EFO_0000469).getParents(), contains(efoMap.get(BFO_0000040)));
    }

    @Test
    public void termChildren()
    {
        assertThat(efoMap.get(EFO_0000469).getChildren(), contains(efoMap.get(EFO_0000273), efoMap.get(EFO_0000534), efoMap.get(EFO_0000568)));
    }
}
