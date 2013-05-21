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
public class TwoColourExpDesignWriterIT {

    private static final String E_GEOD_43049 = "E-GEOD-43049";
    private static final String[] EXPECTED_HEADER = new String[]{"Assay", "Array", "Sample Characteristics[Organism]", "Sample Characteristics[cell line]", "Sample Characteristics[culture condition]", "Factor Values[CULTURE CONDITION]"};
    private static final String[] EXPECTED_FIRST_LINE = new String[]{"GSM1055612.Cy3", "A-AGIL-28", "Homo sapiens", "Caco-2", "Conventional", "Conventional"};
    private static final String[] EXPECTED_LAST_LINE = new String[]{"GSM1055617.Cy5", "A-AGIL-28", "Homo sapiens", "Caco-2", "Apical anaerobic", "Apical anaerobic"};

    private CSVWriter csvWriter;

    @Inject
    private TwoColourExpDesignWriter subject;

    @Before
    public void setUp() throws Exception {
        csvWriter = Mockito.mock(CSVWriter.class);
    }

    @Test
    public void testTwoColourDataFromSDRF() throws Exception {
        subject.forExperimentAccession(E_GEOD_43049, csvWriter);

        verify(csvWriter).writeNext(EXPECTED_HEADER);
        verify(csvWriter).writeNext(EXPECTED_FIRST_LINE);
        verify(csvWriter).writeNext(EXPECTED_LAST_LINE);
    }
}