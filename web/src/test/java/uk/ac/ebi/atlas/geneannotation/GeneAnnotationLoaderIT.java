package uk.ac.ebi.atlas.geneannotation;

import com.sleepycat.collections.StoredMap;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.utils.biomart.BioMartGeneNameStream;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeneAnnotationLoaderIT {

    private GeneAnnotationLoader subject;

    private static String[] gene1 = {"ens1", "g1"};
    private static String[] gene2 = {"ens2", "g2"};

    private ObjectInputStream<String[]> inputStream;

    private AnnotationEnvironment annotationEnvironment;

    private final static String TEST_ENV_PATH = "test-env";


    @Before
    public void initializeSubject() {
        inputStream = mock(ObjectInputStream.class);

        when(inputStream.readNext()).thenReturn(gene1).thenReturn(gene2).thenReturn(null);

        BioMartGeneNameStream.Builder builder = mock(BioMartGeneNameStream.Builder.class);
        when(builder.create()).thenReturn(inputStream);

        annotationEnvironment = new AnnotationEnvironment(TEST_ENV_PATH);
        annotationEnvironment.setup();


        subject = new GeneAnnotationLoader(annotationEnvironment, builder);

    }

    @Test
    public void testLoadGeneNames() throws Exception {

        //given
        subject.loadGeneNames();
        StoredMap<String,String> map = annotationEnvironment.geneNames();

        //then
        assertThat(map.size(), is(2));
        assertThat(map.get(gene1[0]), is(gene1[1]));
        assertThat(map.get(gene2[0]), is(gene2[1]));
    }

    @After
    public void cleanup() throws Exception {
        annotationEnvironment.close();
        FileUtils.deleteDirectory(new File(TEST_ENV_PATH));

    }


}
