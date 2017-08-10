package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentTest {

    static final String ARRAY_DESIGN_ACCESSIONS = "arrayDesignAccessions";
    static final String PUBMEDID = "PUBMEDID";

    AssayGroup g1 = new AssayGroup("id", "assay 1","assay 2");
    AssayGroup g2 = new AssayGroup("test","assay 1");
    Contrast contrast = new Contrast("g1_g2", ARRAY_DESIGN_ACCESSIONS, g1, g2, "contrast");

    MicroarrayExperiment subject;

    public static MicroarrayExperiment get(
            String accession, ExperimentType type, List<Contrast> contrasts, SortedSet<String> arrayDesignAccessions) {

        ImmutableMap<String, String> genomeBrowser =
                ImmutableMap.of("type", "genome_browser", "name", "Ensembl",
                                "url", "http://www.ensembl.org/Homo_sapiens");

        SpeciesProperties speciesProperties =
                SpeciesProperties.create("Homo_sapiens", "ORGANISM_TYPE", "animals", ImmutableList.of(genomeBrowser));

        return new MicroarrayExperiment(type, accession, new Date(), FluentIterable.from(contrasts).transform(contrast1 -> Pair.of(contrast1, true)).toList(), "description",
                new Species("Homo sapiens", speciesProperties), arrayDesignAccessions, new TreeSet<String>(),
                mock(ExperimentDesign.class),
                Sets.newHashSet(PUBMEDID));
    }

    @Before
    public void setUp() throws Exception {
        subject =
                get("accession", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, ImmutableList.of(contrast),
                        Sets.newTreeSet(Sets.newHashSet(ARRAY_DESIGN_ACCESSIONS)));
    }

    @Test
    public void testGetArrayDesignAccessions() throws Exception {
        assertThat(subject.getArrayDesignAccessions(), hasItem(ARRAY_DESIGN_ACCESSIONS));
    }

    @Test
    public void testGetPubMedIds() throws Exception {
        assertThat((Iterable<String>) subject.getAttributes().get("pubMedIds"), contains(PUBMEDID));
    }

    @Test
    public void microRnaExperimentsHaveNoGenomeBrowsers() throws Exception {
        MicroarrayExperiment miRnaSubject =
                get("accession", ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL, ImmutableList.of(contrast),
                        Sets.newTreeSet(Sets.newHashSet(ARRAY_DESIGN_ACCESSIONS)));

        assertThat(subject.getGenomeBrowserNames(), hasSize(1));
        assertThat(miRnaSubject.getGenomeBrowserNames(), hasSize(0));
    }
}