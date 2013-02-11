package uk.ac.ebi.atlas.model.readers;

import com.google.common.collect.SortedSetMultimap;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AnalysisMethodsTsvReaderIT {

    private static final String PATH_TEMPLATE = "web/src/test/resources/magetab/{0}/{0}-analysis-methods.tsv";
    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private AnalysisMethodsTsvReader subject;

    @Before
    public void setUp() throws Exception {
        subject = new AnalysisMethodsTsvReader(PATH_TEMPLATE);
    }

    @Test
    public void readLibrariesTest(){
        Set<String> processedLibraries = subject.readProcessedLibraries(EXPERIMENT_ACCESSION);
        assertThat(processedLibraries.size(), is(greaterThan(1)));
        assertThat(processedLibraries.iterator().next(), startsWith("ERR"));
    }

    @Test
    public void readCommentsAsMap(){
        SortedSetMultimap<String, String> comments = subject.readAllCommentsAsMap(EXPERIMENT_ACCESSION);
        assertThat(comments.get("Libraries").first(), startsWith("ERR") );
    }




}
