
package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer.joinQuotingPhrases;
import static uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer.splitBySpacePreservingQuotes;

public class BioentityPropertyValueTokenizerTest {

    @Test
    public void split() throws Exception {
        assertThat(splitBySpacePreservingQuotes("").size(), is(0));
        assertThat(splitBySpacePreservingQuotes("test1 test2"), contains("test1", "test2"));
        assertThat(splitBySpacePreservingQuotes("test1 test2\ntest3"), contains("test1", "test2", "test3"));
        assertThat(splitBySpacePreservingQuotes("\"test1 test2\""), contains("\"test1 test2\""));
        assertThat(splitBySpacePreservingQuotes("\"test1\" \"test2\""), contains("\"test1\"", "\"test2\""));
        assertThat(splitBySpacePreservingQuotes("\"mt-at\""), contains("\"mt-at\""));
        assertThat(splitBySpacePreservingQuotes("mt-at6"), contains("mt-at6"));
        assertThat(splitBySpacePreservingQuotes("protein_c"), contains("protein_c"));
        assertThat(splitBySpacePreservingQuotes("GO:0016"), contains("GO:0016"));
        assertThat(splitBySpacePreservingQuotes("hs2affx.1.41.s1_3p_s_at"), contains("hs2affx.1.41.s1_3p_s_at"));
        assertThat(splitBySpacePreservingQuotes("mitochondrial enco"), contains("mitochondrial", "enco"));
        assertThat(splitBySpacePreservingQuotes("GO:0008134 \"p53 binding\""), contains("GO:0008134", "\"p53 binding\""));
        assertThat(splitBySpacePreservingQuotes("ENSG00000131759 \"mRNA splicing, via spliceosome\""), contains("ENSG00000131759", "\"mRNA splicing, via spliceosome\""));
    }

    @Test
    public void splitNull() {
        assertThat(splitBySpacePreservingQuotes(null), is(emptyCollectionOf(String.class)));
    }

    @Test
    public void splitEmptyString() {
        assertThat(splitBySpacePreservingQuotes(""), is(emptyCollectionOf(String.class)));
    }

    @Test
    public void splitStringOfSpaces() {
        assertThat(splitBySpacePreservingQuotes(" "), is(emptyCollectionOf(String.class)));
        assertThat(splitBySpacePreservingQuotes("  "), is(emptyCollectionOf(String.class)));
    }

    @Test
    public void join() {
        assertThat(joinQuotingPhrases(ImmutableList.of("one", "two words", "three")), is("one \"two words\" three"));
    }

}