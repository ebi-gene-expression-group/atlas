package uk.ac.ebi.atlas.model;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SpeciesTest {

    public static final Species HUMAN = new Species("Homo sapiens", "homo sapiens", "ensembldb","animals");

    @Test
    public void toStringWorks(){
        assertThat(new Species("homo sapiens","homo sapiens", "ensembldb","animals").toString(), is("homo sapiens " +
                "(ensemblDb: ensembldb,kingdom: animals)"));
        assertThat(new Species("HOMO SAPIENS","homo sapiens", "ensembldb","animals").toString(), is("HOMO " +
                "SAPIENS->homo sapiens (ensemblDb: ensembldb,kingdom: animals)"));
    }

    @Test
    public void equalsWorks(){
        assertThat(new Species("homo sapiens","homo sapiens", "e","k").equals(
                new Species("Homo Sapiens","homo sapiens", "e","k")),is(true));

        assertThat(new Species("homo sapiens","homo sapiens", "e","k").equals(
                new Species("Homo sapiens sapiens","homo sapiens", "e","k")),is(true));

        assertThat(new Species("homo sapiens","homo sapiens", "e","k").equals(
                new Species("homo sapiens","homo sapiens", "different_e","k")),is(true));

        assertThat(new Species("homo sapiens","homo sapiens", "e","k").equals(
                new Species("homo sapiens","homo sapiens", "e","different_k")),is(true));

        assertThat(new Species("homo sapiens","homo sapiens", "e","k").equals(
                new Species("mus musculus","mus musculus", "e","k")),is(false));

        assertThat(new Species("homo sapiens","homo sapiens", "e","k").equals(
                "homo sapiens"),is(false));
        assertThat(new Species("homo sapiens","homo sapiens", "e","k").equals(
                null),is(false));
    }
}