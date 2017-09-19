package uk.ac.ebi.atlas.bioentity.properties;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BioEntityPropertyLinkBuilderIT {

    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private BioEntityPropertyLinkBuilder subject;

    @Test
    public void createValidReactomeLink() throws Exception {
        Optional<PropertyLink> propertyLink =
                subject.createLink("ENSG00000001", BioentityPropertyName.REACTOME, "R-HSA-15869",
                                   speciesFactory.create("Homo sapiens"), 0);
        assertThat(propertyLink.isPresent(), is(true));
    }

    @Test
    public void createInvalidReactomeLink() throws Exception {
        Optional<PropertyLink> propertyLink =
                subject.createLink("ENSG00000066279", BioentityPropertyName.REACTOME, "R-HSA-FOOBAR",
                                   speciesFactory.create("Homo sapiens"), 0);
        assertThat(propertyLink.isPresent(), is(false));
    }

    @Test
    public void createValidGoLink() throws Exception {
        Optional<PropertyLink> propertyLink =
                subject.createLink("ENSG00000066279", BioentityPropertyName.GO, "GO:0021873",
                                   speciesFactory.create("Homo sapiens"), 0);
        assertThat(propertyLink.isPresent(), is(true));
    }

    @Test
    public void createInvalidGoLink() throws Exception {
        Optional<PropertyLink> propertyLink =
                subject.createLink("ENSG00000066279", BioentityPropertyName.GO, "FO:OBAR",
                                   speciesFactory.create("Homo sapiens"), 0);
        assertThat(propertyLink.isPresent(), is(false));
    }

    @Test
    public void createValidPoLink() throws Exception {
        Optional<PropertyLink> propertyLink =
                subject.createLink("Sb09g020230", BioentityPropertyName.PO, "PO:0009030",
                                   speciesFactory.create("Sorghum bicolor"), 0);
        assertThat(propertyLink.isPresent(), is(true));
    }

    @Test
    public void createInvalidPoLink() throws Exception {
        Optional<PropertyLink> propertyLink =
                subject.createLink("Sb09g020230", BioentityPropertyName.PO, "PO:OBAR",
                        speciesFactory.create("Sorghum bicolor"), 0);
        assertThat(propertyLink.isPresent(), is(false));
    }

    @Test
    public void createValidInterproLink() throws Exception {
        Optional<PropertyLink> propertyLink =
                subject.createLink("Sb09g020230", BioentityPropertyName.INTERPRO, "IPR013094",
                        speciesFactory.create("Sorghum bicolor"), 0);
        assertThat(propertyLink.isPresent(), is(true));
    }

    @Test
    public void createInvalidInterproLink() throws Exception {
        Optional<PropertyLink> propertyLink =
                subject.createLink("Sb09g020230", BioentityPropertyName.INTERPRO, "IPRFOOBAR",
                        speciesFactory.create("Sorghum bicolor"), 0);
        assertThat(propertyLink.isPresent(), is(false));
    }

    @Ignore
    public void createValidOrthologLink() throws Exception {
        Optional<PropertyLink> propertyLink =
                subject.createLink("ENSG00000079263", BioentityPropertyName.ORTHOLOG, "ENSMUSG00000052477",
                        speciesFactory.create("Homo sapiens"), 0);
        assertThat(propertyLink.isPresent(), is(true));
    }

    @Ignore
    public void createInvalidOrthologLink() throws Exception {
        Optional<PropertyLink> propertyLink =
                subject.createLink("ENSG00000079263", BioentityPropertyName.ORTHOLOG, "ENSFOOBAR",
                        speciesFactory.create("Homo sapiens"), 0);
        // TODO (?) There’s no check for the validity of invalid ortholog links
        assertThat(propertyLink.isPresent(), is(true));
    }
}