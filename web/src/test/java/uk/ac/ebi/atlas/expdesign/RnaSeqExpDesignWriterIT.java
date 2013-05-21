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

package uk.ac.ebi.atlas.expdesign;

import au.com.bytecode.opencsv.CSVWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RnaSeqExpDesignWriterIT {

    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String[] EXPECTED_HEADER = new String[]{"Run", "Sample Characteristics[Organism]", "Sample Characteristics[age]", "Sample Characteristics[ethnic group]", "Sample Characteristics[organism part]", "Sample Characteristics[sex]", "Factor Values[organism part]"};
    private static final String[] EXPECTED_FIRST_LINE = new String[]{"ERR030856", "Homo sapiens", "", "", "16 tissues mixture", "", "16 tissues mixture"};
    private static final String[] EXPECTED_LAST_LINE = new String[]{"ERR030903", "Homo sapiens", "60", "Caucasian", "thyroid", "female", "thyroid"};

    private CSVWriter csvWriter;

    @Inject
    private RnaSeqExpDesignWriter subject;

    @Before
    public void setUp() throws Exception {
        csvWriter = Mockito.mock(CSVWriter.class);
    }

    @Test
    public void testRnaSeqDataFromSDRF() throws Exception {
        subject.forExperimentAccession(E_MTAB_513, csvWriter);

        verify(csvWriter).writeNext(EXPECTED_HEADER);
        verify(csvWriter).writeNext(EXPECTED_FIRST_LINE);
        verify(csvWriter).writeNext(EXPECTED_LAST_LINE);
    }
}