package uk.ac.ebi.atlas.bioentity.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;

import javax.inject.Inject;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class BioEntityPropertyDaoIT {
    private static final String MOUSE_GENE_ID = "ENSMUSG00000029816";
    private static final int MIN_NUMBER_OF_PROPERTIES = 50;
    private static final int MIN_NUMBER_OF_ORTHOLOGS = 20;
    private static final int MIN_NUMBER_OF_GO_TERMS = 20;
    private static final int MIN_NUMBER_OF_DESIGN_ELEMENTS = 30;

    @Inject
    private BioEntityPropertyDao subject;

    @Test
    public void fetchGenePageProperties() {
        Map<BioentityPropertyName, Set<String>> properties =
                subject.fetchGenePageProperties(MOUSE_GENE_ID);

        assertThat(
                numberOfValues(properties),
                greaterThan(MIN_NUMBER_OF_PROPERTIES));
        assertThat(
                properties.get(BioentityPropertyName.SYNONYM).size(),
                is(3));
        assertThat(
                properties.get(BioentityPropertyName.SYNONYM),
                containsInAnyOrder("DC-HIL", "Dchil", "Osteoactivin"));
        assertThat(
                properties.get(BioentityPropertyName.ORTHOLOG).size(),
                is(greaterThan(MIN_NUMBER_OF_ORTHOLOGS)));
        assertThat(
                properties.get(BioentityPropertyName.GO).size(),
                is(greaterThan(MIN_NUMBER_OF_GO_TERMS)));
        assertThat(
                properties.get(BioentityPropertyName.INTERPRO),
                containsInAnyOrder("IPR000601", "IPR022409", "IPR013783"));
        assertThat(
                properties.get(BioentityPropertyName.ENSFAMILY_DESCRIPTION),
                containsInAnyOrder("PRECURSOR"));
        assertThat(
                properties.get(BioentityPropertyName.ENSGENE),
                containsInAnyOrder("ENSMUSG00000029816"));
        assertThat(
                properties.get(BioentityPropertyName.ENTREZGENE),
                containsInAnyOrder("93695"));
        assertThat(
                properties.get(BioentityPropertyName.UNIPROT),
                containsInAnyOrder("A0A0N4SVG5", "Q8BVA0", "Q99P91"));
        assertThat(
                properties.get(BioentityPropertyName.MGI_ID),
                containsInAnyOrder("MGI:1934765"));
        assertThat(
                properties.get(BioentityPropertyName.MGI_DESCRIPTION),
                containsInAnyOrder("glycoprotein (transmembrane) nmb"));
        assertThat(
                properties.get(BioentityPropertyName.GENE_BIOTYPE),
                containsInAnyOrder("protein_coding"));
        assertThat(
                properties.get(BioentityPropertyName.DESIGN_ELEMENT).size(),
                is(greaterThan(MIN_NUMBER_OF_DESIGN_ELEMENTS)));
    }

    @Test
    public void findPropertyValuesForGeneId() {
        assertThat(
                subject.fetchPropertyValuesForGeneId("ENSG00000179218", BioentityPropertyName.SYMBOL),
                hasItem("CALR"));
        assertThat(
                subject.fetchPropertyValuesForGeneId("ENSMUSG00000029816", BioentityPropertyName.SYMBOL),
                hasItem("Gpnmb"));
    }

    private int numberOfValues(Map<?, ? extends Set<?>> map) {
        int n = 0;
        for (Set<?> value : map.values()) {
            n += value.size();
        }
        return n;
    }

}
