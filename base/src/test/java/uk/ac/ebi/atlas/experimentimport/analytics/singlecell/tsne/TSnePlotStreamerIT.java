package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.resource.DataFileHubFactory;

import javax.inject.Inject;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TSnePlotStreamerIT {
    private final static String E_MTAB_5061_ACCESSION = "E-MTAB-5061";

    @Inject
    private DataFileHubFactory dataFileHubFactory;

    private TSnePlotStreamer subject;

    @Before
    public void setUp() {
        subject =
                new TSnePlotStreamerFactory(dataFileHubFactory.getScxaDataFileHub()).create(E_MTAB_5061_ACCESSION);
    }

    @Test
    public void create() {
        assertThat(subject.availablePerplexities()).containsExactlyInAnyOrder(1, 5, 10, 15, 20);

        int blah = subject.stream(1).collect(Collectors.toList()).size();

        assertThat(subject.stream(5).collect(Collectors.toList())).hasSize(blah);
        assertThat(subject.stream(10).collect(Collectors.toList())).hasSize(blah);
        assertThat(subject.stream(15).collect(Collectors.toList())).hasSize(blah);
        assertThat(subject.stream(20).collect(Collectors.toList())).hasSize(blah);
    }

}