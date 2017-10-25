package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import javax.inject.Inject;

import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BioEntityPropertyServiceIT {

    @Inject
    BioEntityPropertyService subject;

    @Test
    public void knownSpeciesAreAddedToOrthologs() throws Exception {
        Map<String, String> result =
                subject.mapToLinkText(
                        BioentityPropertyName.ORTHOLOG, ImmutableSet.of("ENSMUSG00000019082", "FBgn0260743"));

        assertThat(result, hasEntry("ENSMUSG00000019082", "Slc25a22 (Mus musculus)"));
        assertThat(result, hasEntry("FBgn0260743", "GC1 (Drosophila melanogaster)"));

    }

}