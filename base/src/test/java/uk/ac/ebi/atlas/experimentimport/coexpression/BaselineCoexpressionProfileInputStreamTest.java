package uk.ac.ebi.atlas.experimentimport.coexpression;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.ReturnsElementsOf;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class BaselineCoexpressionProfileInputStreamTest {
    @Mock
    private CSVReader csvReader;

    private BaselineCoexpressionProfileInputStream subject;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSmallExample() throws Exception {

        List<String[]> data = new ArrayList<>();
        for (String line:
                new String[]{
                        "_  g0  g1  g2  g3  g4",
                        "g0 0   1.0 1.1 1.2 1.3",
                        "g1 1.0 0   1.4 1.5 1.6",
                        "g2 1.1 1.4 0   1.7 1.8",
                        "g3 1.2 1.5 1.7 0   1.9",
                        "g4 1.3 1.6 1.8 1.9 0"

                }) {
            data.add(line.split(" +"));
        }
        data.add(null);
        // a gene is not coexpressed with itself
        // pick three lowest values for each gene
        Map<String, ImmutableList<String>> expected = ImmutableMap.of(
                "g0", ImmutableList.of("g1", "g2", "g3"),
                "g1", ImmutableList.of("g0", "g2", "g3"),
                "g2", ImmutableList.of("g0", "g1", "g3"),
                "g3", ImmutableList.of("g0", "g1", "g2"),
                "g4", ImmutableList.of("g0", "g1", "g2")
        );

        test(data, expected, 3);
    }

    @Test
    public void testSmallExampleWithDataInDifferentOrder() throws Exception {

        List<String[]> data = new ArrayList<>();
        for (String line:
                new String[]{
                        "_  g0  g1  g2  g3  g4",
                        "g0 0   1.9 1.8 1.7 1.6",
                        "g1 1.9 0   1.7 1.6 1.5",
                        "g2 1.8 1.7 0   1.5 1.4",
                        "g3 1.7 1.6 1.5 0   1.3",
                        "g4 1.6 1.5 1.4 1.3 0"

                }) {
            data.add(line.split(" +"));
        }
        data.add(null);
        // a gene is not coexpressed with itself
        // pick three lowest values for each gene
        Map<String, ImmutableList<String>> expected = ImmutableMap.of(
                "g0", ImmutableList.of("g4", "g3", "g2"),
                "g1", ImmutableList.of("g4", "g3", "g2"),
                "g2", ImmutableList.of("g4", "g3", "g1"),
                "g3", ImmutableList.of("g4", "g2", "g1"),
                "g4", ImmutableList.of("g3", "g2", "g1")
        );

        test(data, expected, 3);
    }

    @Test
    public void testExampleWithOnlyOneRowSourced() throws Exception {

        List<String[]> data = new ArrayList<>();
        for (String line:
                new String[]{
                        "_  g0  g1  g2  g3  g4",
                        "g0 0   1.0 1.1 1.2 1.3",
                        "g1 1.0 0   1.4 1.5 1.6",
                        "g2 1.1 1.4 0   1.7 1.8",
                        "g3 1.2 1.5 1.7 0   1.9",
                        "g4 1.3 1.6 1.8 1.9 0"

                }) {
            data.add(line.split(" +"));
        }
        data.add(null);
        // a gene is not coexpressed with itself
        // pick three lowest values for each gene
        Map<String, ImmutableList<String>> expected = ImmutableMap.of(
                "g0", ImmutableList.of("g1"),
                "g1", ImmutableList.of("g0"),
                "g2", ImmutableList.of("g0"),
                "g3", ImmutableList.of("g0"),
                "g4", ImmutableList.of("g0")
        );

        test(data, expected, 1);
    }

    @Test
    public void testExampleThatAsksForTooManyGenes() throws Exception {

        List<String[]> data = new ArrayList<>();
        for (String line:
                new String[]{
                        "_  g0  g1  g2  g3  g4",
                        "g0 0   1.0 1.1 1.2 1.3",
                        "g1 1.0 0   1.4 1.5 1.6",
                        "g2 1.1 1.4 0   1.7 1.8",
                        "g3 1.2 1.5 1.7 0   1.9",
                        "g4 1.3 1.6 1.8 1.9 0"

                }) {
            data.add(line.split(" +"));
        }
        data.add(null);
        // If there is too much data we should get all rows
        Map<String, ImmutableList<String>> expected = ImmutableMap.of(
                "g0", ImmutableList.of("g1", "g2", "g3", "g4"),
                "g1", ImmutableList.of("g0", "g2", "g3", "g4"),
                "g2", ImmutableList.of("g0", "g1", "g3", "g4"),
                "g3", ImmutableList.of("g0", "g1", "g2", "g4"),
                "g4", ImmutableList.of("g0", "g1", "g2", "g3")
        );

        test(data, expected, 4);
        test(data, expected, 5);
        test(data, expected, 1337);
    }

    @Test
    public void testExampleThatHasTheSameValuesAndWantsAlphabeticalSorting() throws Exception {

        List<String[]> data = new ArrayList<>();
        for (String line:
                new String[]{
                        "_  c   o   e   x   p ",
                        "c  0   1.0 1.0 1.0 1.0",
                        "o  1.0 0   1.4 1.5 1.6",
                        "e  1.0 1.4 0   1.7 1.8",
                        "x  1.0 1.5 1.7 0   1.9",
                        "p  1.0 1.6 1.8 1.9 0"

                }) {
            data.add(line.split(" +"));
        }
        data.add(null);
        Map<String, ImmutableList<String>> expected = ImmutableMap.of(
                "c", ImmutableList.of("e", "o", "p"),
                "o", ImmutableList.of("c", "e", "x"),
                "e", ImmutableList.of("c", "o", "x"),
                "x", ImmutableList.of("c", "o", "e"),
                "p", ImmutableList.of("c", "o", "e")
        );

        test(data, expected, 3);
    }



    private void test(List<String[]> data, Map<String, ImmutableList<String>> expected, int sizeForTest) throws
            Exception {
        when(csvReader.readNext()).thenAnswer(new ReturnsElementsOf(data));
        subject = new BaselineCoexpressionProfileInputStream(csvReader, sizeForTest);

        List<BaselineCoexpressionProfile> actual = new ArrayList<>();
        for (BaselineCoexpressionProfile profile: new IterableObjectInputStream<>(subject)) {
            actual.add(profile);
        }

        assertEquals(expected.size(), actual.size());
        for (BaselineCoexpressionProfile profile: actual) {
            assertThat(profile.geneID(), profile.coexpressedGenesToList(), is(expected.get(profile.geneID())));
        }
    }
}
