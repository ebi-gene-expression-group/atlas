package uk.ac.ebi.atlas.geneannotation.arraydesign;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.geneannotation.AnnotationEnvironment;
import uk.ac.ebi.atlas.utils.DesignElementKeyGenerator;

import javax.inject.Inject;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DesignElementGeneMappingLoaderIT {

    public static final String AD_ACCESSION = "A-AFFY-44";

    @Inject
    private DesignElementGeneMappingLoader subject;

    @Inject
    private AnnotationEnvironment annotationEnvironment;


    @Test
    public void testLoadDesignElements() throws Exception {
        subject.loadMappings(AD_ACCESSION);

        ConcurrentMap<String,String> map = annotationEnvironment.geneDesignElementsToGeneNames();
        assertThat(map.size(), is(greaterThan(20000)));
        assertThat(map.get(DesignElementKeyGenerator.getKey(AD_ACCESSION, "209575_at")), is("ENSG00000243646"));
    }
}
