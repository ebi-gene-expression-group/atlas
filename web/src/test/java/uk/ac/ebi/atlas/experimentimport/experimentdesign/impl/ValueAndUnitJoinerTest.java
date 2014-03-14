/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

public class ValueAndUnitJoinerTest {

    @Mock
    private OLSClient olsClientMock;

    private ValueAndUnitJoiner subject;
    
    @Before
    public void initSubject() {
        subject = new ValueAndUnitJoiner(olsClientMock);
    }
    
    @Test
    public void testMergeValueAndUnit() throws Exception {

    }

    @Test
    public void testPluraliseUnitIfApplicable() throws Exception {
        assertEquals("microgram", subject.pluraliseUnitIfApplicable("microgram", "1"));
        assertEquals("micrograms", subject.pluraliseUnitIfApplicable("microgram", "1.0"));
        assertEquals("other", subject.pluraliseUnitIfApplicable("other", " 5"));
        assertEquals("percent", subject.pluraliseUnitIfApplicable("percent", "5.0 "));
        assertEquals("volume percent", subject.pluraliseUnitIfApplicable("volume percent", "5 "));
        assertEquals("percent per volume", subject.pluraliseUnitIfApplicable("percent per volume", "5 "));
        assertEquals("nanograms per milliliter", subject.pluraliseUnitIfApplicable("nanogram per milliliter", "10"));
        assertEquals("nanograms per milliliter", subject.pluraliseUnitIfApplicable("nanograms per milliliter", "10"));
        assertEquals("nanogram per milliliter", subject.pluraliseUnitIfApplicable("nanogram per milliliter", "1"));
        assertEquals(null, subject.pluraliseUnitIfApplicable(null, "1"));
        assertEquals("nanogram per milliliter", subject.pluraliseUnitIfApplicable("nanogram per milliliter", null));
        assertEquals(null, subject.pluraliseUnitIfApplicable(null, null));
        assertEquals("cubic centimeters", subject.pluraliseUnitIfApplicable("cubic centimeter", "5"));
        assertEquals("parts per million", subject.pluraliseUnitIfApplicable("parts per million", "5"));
        assertEquals("degrees celsius", subject.pluraliseUnitIfApplicable("degree celsius", "5"));
        assertEquals("degrees", subject.pluraliseUnitIfApplicable("degree", "5"));
        assertEquals("degrees", subject.pluraliseUnitIfApplicable("degrees", "5"));
        assertEquals("degrees", subject.pluraliseUnitIfApplicable("degrees", "1"));
        assertEquals("International Units per mililiter", subject.pluraliseUnitIfApplicable("International Unit per mililiter", "5"));
        assertEquals("other", subject.pluraliseUnitIfApplicable("other", "5"));
        assertEquals("other", subject.pluraliseUnitIfApplicable("other", "5"));
        assertEquals("picomolar", subject.pluraliseUnitIfApplicable("picomolar", "5"));
        assertEquals("molar", subject.pluraliseUnitIfApplicable("molar", "5"));
        assertEquals("milligrams per kilogram", subject.pluraliseUnitIfApplicable("milligram per kilogram", "5"));
        assertEquals("inches", subject.pluraliseUnitIfApplicable("inch", "5"));
    }

    @Test
    public void testIsPluralisable() throws Exception {

    }

    @Test
    public void testPluralise() throws Exception {

    }

    @Test
    public void testIsValidEFOTerm() throws Exception {

    }
}
