package uk.ac.ebi.atlas.bioentity.properties;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BioEntityPropertyServiceIT {

    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private BioEntityPropertyService subject;

    @Test
    public void textIsFetchedFromReactomeWhenValid() throws Exception {

        assertThat(
                subject.createLink("ENSG00000001", BioentityPropertyName.PATHWAYID, "R-HSA-15869",
                speciesFactory.create("Homo sapiens"), 0).toJson().get("text").getAsString(),
                allOf(
                        not(Matchers.isEmptyOrNullString()),
                        not(is("R-HSA-15869"))
                        )
        );
        assertThat(
                subject.createLink("ENSG00000001", BioentityPropertyName.PATHWAYID, "INVALID_PATHWAY",
                        speciesFactory.create("Homo sapiens"), 0).toJson().get("text").getAsString(),
                allOf(
                        not(Matchers.isEmptyOrNullString()),
                        is("INVALID_PATHWAY")
                )
        );
    }

    @Test
    public void goPropertyIsFetchedWhenValid() throws Exception {

        assertThat(
                subject.createLink("ENSG00000066279", BioentityPropertyName.GO, "GO:0021873",
                        speciesFactory.create("Homo sapiens"), 0).toJson().get("text").getAsString(),
                allOf(
                        not(Matchers.isEmptyOrNullString()),
                        not(is("GO:0021873"))
                )
        );
        assertThat(
                subject.createLink("ENSG00000066279", BioentityPropertyName.GO, "GO:INVALID",
                        speciesFactory.create("Homo sapiens"), 0).toJson().get("text").getAsString(),
                allOf(
                        not(Matchers.isEmptyOrNullString()),
                        is("GO:INVALID")
                )
        );
    }

    @Test
    public void interproPropertyIsFetchedWhenValid() throws Exception {

        assertThat(
                subject.createLink("Sb09g020230", BioentityPropertyName.INTERPRO, "IPR013094",
                        speciesFactory.create("Homo sapiens"), 0).toJson().get("text").getAsString(),
                allOf(
                        not(Matchers.isEmptyOrNullString()),
                        not(is("GO:0021873"))
                )
        );
        assertThat(
                subject.createLink("Sb09g020230", BioentityPropertyName.INTERPRO, "IPR_INVALID",
                        speciesFactory.create("Homo sapiens"), 0).toJson().get("text").getAsString(),
                allOf(
                        not(Matchers.isEmptyOrNullString()),
                        is("IPR_INVALID")
                )
        );
    }
}