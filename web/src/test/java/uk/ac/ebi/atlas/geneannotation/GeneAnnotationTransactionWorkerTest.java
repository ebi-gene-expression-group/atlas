/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.geneannotation;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.commons.berkeley.StringValueTransactionWorker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GeneAnnotationTransactionWorkerTest {

    private StringValueTransactionWorker subject;

    ConcurrentMap<String, String> map;

    private static String[] gene1 = {"ens1", "g1"};
    private static String[] gene2 = {"ens2", "g2"};
    private static String[] gene3 = {"ens2", "g5"};

    @Before
    public void initializeSubject() {

        map = new ConcurrentHashMap<String, String>();

        subject = new StringValueTransactionWorker(map) {
            @Override
            protected String getValue() {
                return getRow()[1];
            }

            @Override
            protected String getKey() {
                return getRow()[0];
            }
        };
    }

    @Test
    public void testAddValue() throws Exception {
        //given
        subject.setRow(gene1);
        subject.doWork();

        //then
        assertThat(map.size(), is(1));
        assertThat(map.get(gene1[0]), is(gene1[1]));
    }

    @Test
    public void testDuplicateValueNotAdded() throws Exception {
        //given
        subject.setRow(gene1);
        subject.doWork();

        subject.setRow(gene1);
        subject.doWork();

        //then
        assertThat(map.size(), is(1));
        assertThat(map.get(gene1[0]), is(gene1[1]));
    }

    @Test
    public void testAddDuplicateKeyReplacesValue() throws Exception {
        //given
        subject.setRow(gene2);
        subject.doWork();

        subject.setRow(gene3);
        subject.doWork();

        //then
        assertThat(map.size(), is(1));
        assertThat(map.get(gene2[0]), is(gene3[1]));
    }

}
