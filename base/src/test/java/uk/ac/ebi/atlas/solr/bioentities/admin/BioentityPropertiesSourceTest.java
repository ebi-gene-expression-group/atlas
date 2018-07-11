package uk.ac.ebi.atlas.solr.bioentities.admin;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.resource.BioentityPropertyFile;
import uk.ac.ebi.atlas.solr.bioentities.BioentityProperty;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.species.SpeciesPropertiesDao;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.DESIGN_ELEMENT;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.PATHWAYID;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.PATHWAYNAME;

public class BioentityPropertiesSourceTest {
    private Path bioentityPropertiesDirectoryLocation;
    private SpeciesProperties HUMAN =
            SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.of());

    private BioentityPropertiesSource subject;

    @Before
    public void setUp() throws Exception {
        bioentityPropertiesDirectoryLocation = Files.createTempDirectory("");
        bioentityPropertiesDirectoryLocation.toFile().deleteOnExit();

        SpeciesPropertiesDao speciesPropertiesDao = mock(SpeciesPropertiesDao.class);
        when(speciesPropertiesDao.fetchAll()).thenReturn(ImmutableList.of(HUMAN));

        SpeciesPropertiesTrader speciesPropertiesTrader = new SpeciesPropertiesTrader();
        speciesPropertiesTrader.setSpeciesPropertiesDao(speciesPropertiesDao);
        speciesPropertiesTrader.refresh();

        subject =
                new BioentityPropertiesSource(
                        Files.createDirectories(bioentityPropertiesDirectoryLocation.resolve("annotations")),
                        Files.createDirectories(bioentityPropertiesDirectoryLocation.resolve("array_designs")),
                        Files.createDirectories(bioentityPropertiesDirectoryLocation.resolve("reactome")),
                        new SpeciesFactory(speciesPropertiesTrader));
    }

    private void addTemporaryFile(String where, String fileName, Collection<String[]> lines) throws IOException {
        Path p = Files.createFile(bioentityPropertiesDirectoryLocation.resolve(where).resolve(fileName));
        p.toFile().deleteOnExit();
        Files.write(p, lines.stream().map(l -> Joiner.on("\t").join(l)).collect(toList()), Charset.forName("UTF-8"));
    }

    @Test
    public void noFilesMeansEmptyStreams() {
        assertThat(subject.getAnnotationFiles().count(), is(0L));
        assertThat(subject.getArrayDesignMappingFiles().count(), is(0L));
        assertThat(subject.getReactomePropertyFiles().count(), is(0L));
    }

    @Test
    public void oddFilesSkipped() throws Exception {
        addTemporaryFile(
                "annotations",
                "not-a-right-name.tsv",
                ImmutableList.of());

        addTemporaryFile(
                "annotations",
                "Homo_sapiens.ensprotein.tsv",
                ImmutableList.of());

        addTemporaryFile(
                "annotations",
                "Homo_sapiens.enstranscript.tsv",
                ImmutableList.of());

        assertThat(subject.getAnnotationFiles().count(), is(0L));

        addTemporaryFile(
                "annotations",
                "Homo_sapiens.ensgene.tsv",
                ImmutableList.of());

        assertThat(subject.getAnnotationFiles().count(), is(1L));

        addTemporaryFile(
                "annotations",
                "Sleazy_worm.wbpsgene.tsv",
                ImmutableList.of());

        assertThat(subject.getAnnotationFiles().count(), is(2L));
    }

    private void assertHasOneGoodResource(Stream<? extends BioentityPropertyFile> s){
        List<BioentityPropertyFile> l = s.collect(toList());
        assertThat(l.size(), is(1));
        assertThat(l.get(0).existsAndIsNonEmpty(), is(true));
    }

    @Test
    public void goodButEmptyAnnotationFile() throws Exception {
        addTemporaryFile(
                "annotations",
                "Homo_sapiens.ensgene.tsv",
                ImmutableList.of(
                        new String[]{"ensgene", "synonym", "ensfamily_description", "goterm"}));

        assertHasOneGoodResource(subject.getAnnotationFiles());

        assertThat(
                subject.getAnnotationFiles()
                       .findFirst()
                       .orElseThrow(RuntimeException::new)
                       .species
                       .getEnsemblName(),
                is(HUMAN.ensemblName()));
    }

    @Test
    public void goodButEmptyAnnotationFileWithUnknownSpeciesIsGonnaBeABlankFieldInSolrButIsOtherwiseOkayForNow() throws Exception {
        addTemporaryFile(
                "annotations",
                "Unknown_species.ensgene.tsv",
                ImmutableList.of(
                        new String[]{"ensgene", "synonym", "ensfamily_description", "goterm"}));

        assertHasOneGoodResource(subject.getAnnotationFiles());

        assertThat(
                subject.getAnnotationFiles()
                       .findFirst()
                       .orElseThrow(RuntimeException::new)
                       .species
                       .getEnsemblName(),
                is(SpeciesProperties.UNKNOWN.ensemblName()));
    }

    @Test
    public void goodBasicFile() throws Exception {
        addTemporaryFile(
                "annotations",
                "Homo_sapiens.ensgene.tsv",
                ImmutableList.of(
                        new String[]{"ensgene", "synonym"},
                        new String[]{"id", "synonym_value"}));

        assertHasOneGoodResource(subject.getAnnotationFiles());

        assertThat(
                subject.getAnnotationFiles()
                       .findFirst()
                       .orElseThrow(RuntimeException::new)
                       .get()
                       .collect(toList()),
                containsInAnyOrder(
                        new BioentityProperty("id", "Homo_sapiens", "ensgene", "id"),
                        new BioentityProperty("id", "Homo_sapiens", "synonym", "synonym_value")));
    }

    @Test
    public void goodMirnaFile() throws Exception {
        addTemporaryFile(
                "annotations",
                "Homo_sapiens.mature_mirna.tsv",
                ImmutableList.of(
                        new String[]{"mirbase_accession", "symbol", "mirbase_name", "mirbase_sequence"},
                        new String[]{"MIMAT0001535", "cfa-miR-448", "miR-448", "UUGCAUAUGUAGGAUGUCCCAU"}));

        assertHasOneGoodResource(subject.getAnnotationFiles());

        assertThat(
                subject.getAnnotationFiles()
                       .findFirst()
                       .orElseThrow(RuntimeException::new)
                       .get()
                       .collect(toList()),
                containsInAnyOrder(
                        new BioentityProperty("MIMAT0001535", "Homo_sapiens", "mirbase_accession", "MIMAT0001535"),
                        new BioentityProperty("MIMAT0001535", "Homo_sapiens", "symbol", "cfa-miR-448"),
                        new BioentityProperty("MIMAT0001535", "Homo_sapiens", "mirbase_name", "miR-448"),
                        new BioentityProperty("MIMAT0001535", "Homo_sapiens", "mirbase_sequence", "UUGCAUAUGUAGGAUGUCCCAU")));
    }

    @Test
    public void goodMultipleFiles() throws Exception {
        addTemporaryFile(
                "annotations",
                "Homo_sapiens.ensgene.tsv",
                ImmutableList.of(
                        new String[]{"ensgene", "synonym"},
                        new String[]{"id", "synonym_value"}));

        addTemporaryFile(
                "annotations",
                "Unknown_species.ensgene.tsv",
                ImmutableList.of(
                        new String[]{"ensgene", "synonym", "other"},
                        new String[]{"id", "synonym_value", "other_value"}));

        assertThat(
                subject.getAnnotationFiles()
                       .flatMap(BioentityPropertiesSource.AnnotationFile::get)
                       .collect(toList()),
                containsInAnyOrder(
                        new BioentityProperty("id", "Homo_sapiens", "ensgene", "id"),
                        new BioentityProperty("id", "Homo_sapiens", "synonym", "synonym_value"),
                        new BioentityProperty("id", "", "ensgene", "id"),
                        new BioentityProperty("id", "", "synonym", "synonym_value"),
                        new BioentityProperty("id", "", "other", "other_value")));
    }


    @Test
    public void annotationFileSkipsEmptyProperties() throws Exception {
        addTemporaryFile(
                "annotations",
                "Homo_sapiens.ensgene.tsv",
                ImmutableList.of(
                        new String[]{"ensgene", "synonym", "other"},
                        new String[]{"id", "synonym_value", ""}));

        assertThat(
                subject.getAnnotationFiles()
                       .flatMap(BioentityPropertiesSource.AnnotationFile::get)
                       .collect(toList()),
                containsInAnyOrder(
                        new BioentityProperty("id", "Homo_sapiens", "ensgene", "id"),
                        new BioentityProperty("id", "Homo_sapiens", "synonym", "synonym_value")));
    }

    @Test
    public void annotationFileSplitsPropertiesOnSeparator() throws Exception {
        addTemporaryFile("annotations", "Homo_sapiens.ensgene.tsv", ImmutableList.of(
                new String[]{"ensgene", "synonym"},
                new String[]{"id", "v1@@v2"}));

        assertThat(
                subject.getAnnotationFiles()
                       .flatMap(BioentityPropertiesSource.AnnotationFile::get)
                       .collect(toList()),
                containsInAnyOrder(
                        new BioentityProperty("id", "Homo_sapiens", "ensgene", "id"),
                        new BioentityProperty("id", "Homo_sapiens", "synonym", "v1"),
                        new BioentityProperty("id", "Homo_sapiens", "synonym", "v2")));
    }


    @Test
    public void arrayDesignsHaveNoHeader() throws Exception {
        addTemporaryFile(
                "array_designs",
                "Homo_sapiens.A-AFFY-1.tsv",
                ImmutableList.of(new String[]{"ENSG00000000003", "39361_f_at"}));

        assertThat(
                subject.getArrayDesignMappingFiles()
                       .flatMap(BioentityPropertiesSource.ArrayDesignMappingFile::get)
                       .collect(toList()),
                containsInAnyOrder(new BioentityProperty("ENSG00000000003", "Homo_sapiens", DESIGN_ELEMENT.name, "39361_f_at")));
    }

    @Test
    public void arrayDesignsCanHaveEmptyProperties() throws Exception {
        addTemporaryFile(
                "array_designs",
                "Homo_sapiens.A-AFFY-1.tsv",
                ImmutableList.of(new String[]{"ENSG00000000003", ""}));

        assertThat(
                subject.getArrayDesignMappingFiles()
                       .flatMap(BioentityPropertiesSource.ArrayDesignMappingFile::get)
                       .collect(toList()),
                is(empty()));
    }

    @Test
    public void arrayDesignsCanComeFromDifferentFiles() throws Exception {
        addTemporaryFile(
                "array_designs",
                "Homo_sapiens.A-AFFY-1.tsv",
                ImmutableList.of(new String[]{"ENSG00000000003", "39361_f_at"}));

        addTemporaryFile(
                "array_designs",
                "Homo_sapiens.A-AFFY-2.tsv",
                ImmutableList.of(new String[]{"ENSG00000000003", "something_different_at"}));

        assertThat(
                subject.getArrayDesignMappingFiles()
                       .flatMap(BioentityPropertiesSource.ArrayDesignMappingFile::get)
                       .collect(toList()),
                containsInAnyOrder(
                        new BioentityProperty(
                                "ENSG00000000003", "Homo_sapiens", DESIGN_ELEMENT.name, "39361_f_at"),
                        new BioentityProperty(
                                "ENSG00000000003", "Homo_sapiens", DESIGN_ELEMENT.name, "something_different_at")));
    }

    @Test
    public void reactomeTypicalFile() throws Exception {
        addTemporaryFile(
                "reactome",
                "Homo_sapiens.reactome.tsv",
                ImmutableList.of(
                        new String[]{"ensgene", "pathwayid", "pathwayname"},
                        new String[]{"ENSG00000000419", "R-HSA-162699", "Synthesis of dolichyl-phosphate mannose"}));

        assertThat(
                subject.getReactomePropertyFiles()
                       .flatMap(BioentityPropertiesSource.ReactomePropertyFile::get)
                       .collect(toList()),
                containsInAnyOrder(
                        new BioentityProperty(
                                "ENSG00000000419", "Homo_sapiens", PATHWAYID.name, "R-HSA-162699" ),
                        new BioentityProperty(
                                "ENSG00000000419", "Homo_sapiens", PATHWAYNAME.name, "Synthesis of dolichyl-phosphate mannose")));
    }

    @Test
    public void reactomeHeaderIsIgnored() throws Exception {
        addTemporaryFile(
                "reactome",
                "Homo_sapiens.reactome.tsv",
                ImmutableList.of(
                        new String[]{"header", "is", "ignored"},
                        new String[]{"ENSG00000000419", "R-HSA-162699", "Synthesis of dolichyl-phosphate mannose"}));

        assertThat(
                subject.getReactomePropertyFiles()
                       .flatMap(BioentityPropertiesSource.ReactomePropertyFile::get)
                       .collect(toList()),
                containsInAnyOrder(
                        new BioentityProperty(
                                "ENSG00000000419", "Homo_sapiens", PATHWAYID.name, "R-HSA-162699" ),
                        new BioentityProperty(
                                "ENSG00000000419", "Homo_sapiens", PATHWAYNAME.name, "Synthesis of dolichyl-phosphate mannose")));
    }
}