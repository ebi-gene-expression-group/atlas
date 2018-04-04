package uk.ac.ebi.atlas.bioentity.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
@ContextConfiguration("classpath:applicationContext.xml")
public class BioEntityPropertyDaoIT {

    @Inject
    private BioEntityPropertyDao subject;

    @Test
    public void fetchGenePageProperties() {
        // given
        Map<BioentityPropertyName, Set<String>> properties =
                subject.fetchGenePageProperties("ENSMUSG00000029816");

        assertThat(numberOfValues(properties), greaterThan(50));
        assertThat(properties.get(BioentityPropertyName.SYNONYM).size(), is(3));
        assertThat(properties.get(BioentityPropertyName.SYNONYM), containsInAnyOrder("DC-HIL", "Dchil", "Osteoactivin"));
        assertThat(properties.get(BioentityPropertyName.ORTHOLOG).size(), is(greaterThan(20)));
        assertThat(properties.get(BioentityPropertyName.GO).size(), is(greaterThan(20)));
        assertThat(properties.get(BioentityPropertyName.INTERPRO), containsInAnyOrder("IPR000601", "IPR022409"));
        assertThat(properties.get(BioentityPropertyName.ENSFAMILY_DESCRIPTION), containsInAnyOrder("PRECURSOR"));
        assertThat(properties.get(BioentityPropertyName.ENSGENE), containsInAnyOrder("ENSMUSG00000029816"));
        assertThat(properties.get(BioentityPropertyName.ENTREZGENE), containsInAnyOrder("93695"));
        assertThat(properties.get(BioentityPropertyName.UNIPROT), containsInAnyOrder("A0A0N4SVG5", "Q8BVA0", "Q99P91"));
        assertThat(properties.get(BioentityPropertyName.MGI_ID), containsInAnyOrder("MGI:1934765"));
        assertThat(properties.get(BioentityPropertyName.MGI_DESCRIPTION), containsInAnyOrder("glycoprotein (transmembrane) nmb"));
        assertThat(properties.get(BioentityPropertyName.GENE_BIOTYPE), containsInAnyOrder("protein_coding"));
        assertThat(properties.get(BioentityPropertyName.DESIGN_ELEMENT).size(), is(greaterThan(30)));
    }

    @Test
    public void findPropertyValuesForGeneId() {
        assertThat(subject.fetchPropertyValuesForGeneId("ENSG00000179218", BioentityPropertyName.SYMBOL), hasItem("CALR"));
        assertThat(subject.fetchPropertyValuesForGeneId("ENSMUSG00000029816", BioentityPropertyName.SYMBOL), hasItem("Gpnmb"));
    }


    public int numberOfValues(Map<?, ? extends Set<?>> map) {
        int n = 0;
        for (Set<?> value : map.values()) {
            n += value.size();
        }
        return n;
    }

}
