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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayDataWriterTest {

    public static final String ARRAY_DESIGN_ACC = "arrayDesign1";
    @Mock
    private TsvReaderUtils tsvReaderUtilsMock;

    @Mock
    private PrintWriter printWriterMock;

    @Mock
    private GeneNamesProvider geneNamesProviderMock;

    @Mock
    private DesignElementMappingProvider mappingProviderMock;

    @Mock
    private CSVReader csvReaderMock;

    private String[] header = {"DesignElementAccession", "C1", "C2", "C3"};
    private String[] line = {"de123", "1", "0", "10.5"};

    private MicroarrayDataWriter subject;

    @Before
    public void initSubject() throws Exception {
        when(tsvReaderUtilsMock.build(anyString())).thenReturn(csvReaderMock);
        when(csvReaderMock.readNext()).thenReturn(header)
                .thenReturn(line)
                .thenReturn(null);

        when(geneNamesProviderMock.getGeneName("ens1")).thenReturn("name1");
        when(mappingProviderMock.getEnsGeneId(ARRAY_DESIGN_ACC, "de123")).thenReturn("ens1");

        MicroarrayNormalizedDataHeaderBuilder headerBuilder = new MicroarrayNormalizedDataHeaderBuilder();

        subject = new MicroarrayDataWriter(tsvReaderUtilsMock, geneNamesProviderMock, mappingProviderMock);
        subject.setFileUrlTemplate("magetab/{0}/{0}-row-counts.tsv");
        subject.setHeaderBuilder(headerBuilder);
        subject.setResponseWriter(printWriterMock);
        subject.setArrayDesignAccession(ARRAY_DESIGN_ACC);
    }

    @Test
    public void testBuildHeader() throws Exception {
        String[] result = subject.buildHeader(header);
        assertThat(result, is(new String[]{HeaderBuilder.GENE_NAME_COLUMN_NAME, HeaderBuilder.DESIGN_ELEMENT, "C1", "C2", "C3"}));
    }

    @Test
    public void testWrite() throws Exception {
        subject.setExperimentAccession("Exp1");
        Long count = subject.write();

        verify(printWriterMock).write("Gene name\tDesign Element\tC1\tC2\tC3\n", 0, 34);

        verify(printWriterMock).write("name1\tde123\t1\t0\t10.5\n", 0, 21);

        assertThat(count, is(1L));
    }
}
