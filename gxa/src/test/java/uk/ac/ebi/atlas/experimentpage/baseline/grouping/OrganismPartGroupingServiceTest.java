package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class OrganismPartGroupingServiceTest {
    private Path tmpPath;
    private OrganismPartGroupingService subject;

    @Before
    public void setUp() throws Exception {
        tmpPath = Files.createTempDirectory("");
        subject =
                new OrganismPartGroupingService(
                        tmpPath.resolve("anatomical_systems.txt"),
                        tmpPath.resolve("organs.txt"));
    }

    @Test
    public void noTermsGivesEmptyMapping() {
        setAnatomicalSystems();
        setOrgans();
        assertThat(subject.getAnatomicalSystemsGrouping(ImmutableList.of()).entrySet(), empty());
        assertThat(subject.getOrgansGrouping(ImmutableList.of()).entrySet(), empty());
    }

    @Test
    public void noDataGivesEmptyMapping() {
        setAnatomicalSystems("UBERON_0000060\tanatomical wall\tUBERON_0003688\tomentum");
        setOrgans("UBERON_0000020\tsense organ\tUBERON_0000004\tnose");

        assertThat(
                subject.getAnatomicalSystemsGrouping(ImmutableList.of(OntologyTerm.create(""))).entrySet(),
                empty());
        assertThat(subject.getOrgansGrouping(ImmutableList.of(OntologyTerm.create(""))).entrySet(), empty());
    }

    @Test
    public void readAnatomicalSystems() {
        setAnatomicalSystems("UBERON_0000060\tanatomical wall\tUBERON_0003688\tomentum");

        assertThat(
                subject.getAnatomicalSystemsGrouping(ImmutableList.of(OntologyTerm.create(""))).entrySet(),
                empty());
        assertThat(
                subject.getAnatomicalSystemsGrouping(
                        ImmutableList.of(OntologyTerm.create("UBERON_0003688", "omentum"))).entrySet(),
                not(empty()));
    }

    @Test
    public void readOrgans() {
        setOrgans("UBERON_0000020\tsense organ\tUBERON_0000004\tnose");
        assertThat(subject.getOrgansGrouping(ImmutableList.of(OntologyTerm.create(""))).entrySet(), empty());
        assertThat(
                subject.getOrgansGrouping(ImmutableList.of(OntologyTerm.create("UBERON_0000004", "nose"))).entrySet(),
                not(empty()));
    }

    @Test
    public void ignoreCommentLines() {
        setOrgans("#organ id \t organ name \t tissue id \t tissue name",
                "UBERON_0000020\tsense organ\tUBERON_0000004\tnose");
        assertThat(subject.getOrgansGrouping(ImmutableList.of(OntologyTerm.create(""))).entrySet(), empty());
        assertThat(
                subject.getOrgansGrouping(ImmutableList.of(OntologyTerm.create("UBERON_0000004", "nose"))).entrySet(),
                not(empty()));
    }


    @Test
    public void storeValuesById() {
        setOrgans("UBERON_0000020\tsense organ\tUBERON_0000004\tnose");
        assertThat(
                subject.getOrgansGrouping(ImmutableList.of(OntologyTerm.create("UBERON_0000004"))).keySet(),
                is(
                        subject.getOrgansGrouping(ImmutableList.of(OntologyTerm.create("UBERON_0000004", "nose")))
                                .keySet()));
    }

    private void setAnatomicalSystems(String... lines) {
        setContent("anatomical_systems.txt", lines);
    }

    private void setOrgans(String... lines) {
        setContent("organs.txt", lines);
    }

    private void setContent(String fileName, String... lines) {
        File f = new File(tmpPath.toString(), fileName);
        f.deleteOnExit();
        try {
            f.createNewFile();
            Files.write(f.toPath(), Arrays.asList(lines), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
