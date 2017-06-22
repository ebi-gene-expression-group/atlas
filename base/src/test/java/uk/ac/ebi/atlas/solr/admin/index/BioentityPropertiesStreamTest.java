
package uk.ac.ebi.atlas.solr.admin.index;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BioentityPropertiesStreamTest {

    private static final String[] GENE_PROPERTY_HEADERS = new String[]{
        "gene", "description", "ensprotein", "enstranscript", "entrezgene", "go", "goterm", "interpro", "interproterm", "refseq", "symbol", "unigene", "uniprot", "synonym"
    };

    private static final String[] GENE_PROPERTY_VALUES = new String[]{
        "AGAP000002", "WW domain-containing protein [Source:VB Community Annotation;Acc:AGAP000002]", "AGAP000002-PA",
        "AGAP000002-RA", "1272272@@4576126@@5666737", "GO:0003674@@GO:0005515", "molecular_function@@protein binding",
        "IPR001202@@IPR008973", "C2 calcium/lipid-binding domain, CaLB@@WW/Rsp5/WWP", "XP_001237378@@XP_001687751@@XP_311158",
        "KIBRLG", "Aga.19012@@Aga.29661"
    };

    private static final String SPECIES = "anopheles_gambiae";
    private static final String BIOENTITY_TYPE = "gene";

    private BioentityPropertiesStream subject;

    @Mock
    private CSVReader csvReaderMock;

    @Mock
    private BioentityPropertiesBuilder bioentityPropertiesBuilderMock;

    @Before
    public void setUp() throws Exception {
        given(csvReaderMock.readNext()).willReturn(GENE_PROPERTY_HEADERS)
                                       .willReturn(GENE_PROPERTY_VALUES)
                                       .willReturn(null);

        given(bioentityPropertiesBuilderMock.forSpecies(SPECIES)).willReturn(bioentityPropertiesBuilderMock);
        given(bioentityPropertiesBuilderMock.forPropertyNames(anyList())).willReturn(bioentityPropertiesBuilderMock);
        given(bioentityPropertiesBuilderMock.withBioentityIdentifier(anyString())).willReturn(bioentityPropertiesBuilderMock);
        given(bioentityPropertiesBuilderMock.withPropertyValues(anyList())).willReturn(bioentityPropertiesBuilderMock);

        subject = new BioentityPropertiesStream(csvReaderMock, bioentityPropertiesBuilderMock, SPECIES);
    }

    @Test
    public void nextShouldUseThePropertiesBuilder() throws IOException {
        subject.next();
        verify(bioentityPropertiesBuilderMock).forSpecies(SPECIES);

        List<String> propertyNames = Lists.newArrayList(GENE_PROPERTY_HEADERS);
        propertyNames.remove(0);
        verify(bioentityPropertiesBuilderMock).forPropertyNames(propertyNames);

        verify(bioentityPropertiesBuilderMock).withBioentityIdentifier("AGAP000002");

        verify(bioentityPropertiesBuilderMock).build();

    }

    @Test
    public void nextShouldReturnNullWhenCsvReaderIsExhausted() throws IOException {
        given(csvReaderMock.readNext()).willReturn(null);
        assertThat(subject.next(), is(nullValue()));
    }

    @Test(expected=IOException.class)
    public void nextShouldThrowException() throws IOException {
        given(csvReaderMock.readNext()).willThrow(IOException.class);
        subject.next();
    }

    @Test
    public void closeShouldCloseTheCsvReader() throws IOException {
        subject.close();
        verify(csvReaderMock).close();
    }


}
