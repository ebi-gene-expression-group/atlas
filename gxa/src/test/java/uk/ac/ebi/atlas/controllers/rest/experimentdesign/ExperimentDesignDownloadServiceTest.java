package uk.ac.ebi.atlas.controllers.rest.experimentdesign;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExperimentDesignDownloadServiceTest {

    ExperimentDesignDownloadService subject = new ExperimentDesignDownloadService(null);

    @Test
    public void test(){
        String ASSAY_ACCESSION = "assayAccession";
        String OTHER_ACCESSION = "otherAccession";
        Set<String> assayAccessions = Sets.newHashSet(ASSAY_ACCESSION);
        List<String[]> designs = Lists.newArrayList();
        designs.add(new String[]{"header1", "header2", "header3"});
        designs.add(new String[]{ASSAY_ACCESSION, "value2", "value3"});
        designs.add(new String[]{OTHER_ACCESSION, "value5", "value6"});

        List<String[]> expected = ImmutableList.of(
               new String[] {"header1", "header2", "header3", "Analysed"},
                new String[] {ASSAY_ACCESSION, "value2", "value3", "Yes"},
                    new String[] {OTHER_ACCESSION, "value5", "value6", "No"}
        );

        List<String[]> result = subject.getLines(assayAccessions,designs);

        assertEquals(expected.size(), result.size());
        for(int i = 0; i< expected.size(); i++){
            assertTrue("Line wrong:" +i, Arrays.deepEquals(expected.get(i), result.get(i)));
        }
    }
}
