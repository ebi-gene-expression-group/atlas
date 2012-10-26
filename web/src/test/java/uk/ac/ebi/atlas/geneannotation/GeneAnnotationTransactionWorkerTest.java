package uk.ac.ebi.atlas.geneannotation;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GeneAnnotationTransactionWorkerTest {

    private GeneAnnotationLoader.GeneAnnotationTransactionWorker<String> subject;

    ConcurrentMap<String, String> map;

    private static String[] gene1 = {"ens1", "g1"};
    private static String[] gene2 = {"ens2", "g2"};
    private static String[] gene3 = {"ens2", "g5"};

    @Before
    public void initializeSubject() {

        map = new ConcurrentHashMap<String, String>();

        subject = new GeneAnnotationLoader.GeneAnnotationTransactionWorker<String>(map) {
            @Override
            protected String getValue() {
                return line[1];
            }

            @Override
            protected String getKey() {
                return line[0];
            }
        };
    }

    @Test
    public void testAddValue() throws Exception {
        //given
        subject.setLine(gene1);
        subject.doWork();

        //then
        assertThat(map.size(), is(1));
        assertThat(map.get(gene1[0]), is(gene1[1]));
    }

    @Test
    public void testDuplicateValueNotAdded() throws Exception {
        //given
        subject.setLine(gene1);
        subject.doWork();

        subject.setLine(gene1);
        subject.doWork();

        //then
        assertThat(map.size(), is(1));
        assertThat(map.get(gene1[0]), is(gene1[1]));
    }

    @Test
    public void testAddDuplicateKeyReplacesValue() throws Exception {
        //given
        subject.setLine(gene2);
        subject.doWork();

        subject.setLine(gene3);
        subject.doWork();

        //then
        assertThat(map.size(), is(1));
        assertThat(map.get(gene2[0]), is(gene3[1]));
    }

}
