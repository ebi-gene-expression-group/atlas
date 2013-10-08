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

package uk.ac.ebi.atlas.commands.download;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionsWriterImplTest {

    @Mock
    private TsvReaderUtils tsvReaderUtilsMock;

    @Mock
    private CSVWriter csvWriterMock;

    @Mock
    private CSVReader csvReaderMock;

    private String[] header = {"GeneId", "GeneName", "SRR057596", "SRR057597", "SRR057598"};
    private String[] line1 = {"ens1", "name1", "1", "0", "10.5"};
    private String[] line2 = {"ens2", "name2", "1", "0", "10.\"5"};

    private ExpressionsWriterImpl subject;

    @Before
    public void initSubject() throws Exception {
        when(tsvReaderUtilsMock.build(anyString())).thenReturn(csvReaderMock);
        when(csvReaderMock.readNext()).thenReturn(header)
                .thenReturn(line1)
                .thenReturn(line2)
                .thenReturn(null);

        AnalyticsDataHeaderBuilder headerBuilder = new AnalyticsDataHeaderBuilder();

        subject = new ExpressionsWriterImpl(tsvReaderUtilsMock);
        subject.setFileUrlTemplate("magetab/{0}/{0}-row-counts.tsv");
        subject.setHeaderBuilder(null);
        subject.setResponseWriter(csvWriterMock);
    }

    @Test
    public void testBuildHeader() throws Exception {
        String[] result = subject.buildHeader(header);
        assertThat(result, is(new String[]{"GeneId", "GeneName", "SRR057596", "SRR057597", "SRR057598"}));
    }

    @Test
    public void testWrite() throws Exception {
        subject.setExperimentAccession("Exp1");
        Long count = subject.write();

        verify(csvWriterMock).writeNext(new String[]{"GeneId","GeneName","SRR057596","SRR057597","SRR057598"});

        verify(csvWriterMock).writeNext(new String[]{"ens1","name1","1","0","10.5"});

        verify(csvWriterMock).writeNext(new String[]{"ens2","name2","1","0","10.\"5"});

        assertThat(count, is(2L));
    }
}
