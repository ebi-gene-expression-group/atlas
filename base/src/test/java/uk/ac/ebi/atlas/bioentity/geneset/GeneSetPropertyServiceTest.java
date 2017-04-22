package uk.ac.ebi.atlas.bioentity.geneset;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.bioentity.go.GoPoTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneSetPropertyServiceTest {

    @Mock
    GoPoTrader goPoTermTraderMock;

    @Mock
    InterProTrader interProTermTraderMock;

    @Mock
    ReactomeClient reactomeClientMock;

    GeneSetPropertyService subject;

    @Before
    public void setUp() throws Exception {
        when(reactomeClientMock.fetchPathwayNameFailSafe(startsWith("R-"))).thenReturn("some pathway name");
        when(goPoTermTraderMock.getTermName(startsWith("GO:"))).thenReturn("some GO term name");
        when(goPoTermTraderMock.getTermName(startsWith("PO:"))).thenReturn("some PO term name");
        when(interProTermTraderMock.getTermName(startsWith("IPR"))).thenReturn("some InterPro term name");
        subject = new GeneSetPropertyService(goPoTermTraderMock, interProTermTraderMock, reactomeClientMock);
    }

    @Test
    public void propertyValuesByTypeReactome() throws Exception {
        assertThat(
                subject.propertyValuesByType("R-HSA-0000000"),
                hasEntry(is(BioentityPropertyName.REACTOME), isA(Set.class)));

        verify(reactomeClientMock).fetchPathwayNameFailSafe(anyString());
        verifyZeroInteractions(goPoTermTraderMock, interProTermTraderMock);
    }

    @Test
    public void propertyValuesByTypeGeneOntology() throws Exception {
        assertThat(
                subject.propertyValuesByType("GO:0000000"),
                hasEntry(is(BioentityPropertyName.GO), isA(Set.class)));

        verify(goPoTermTraderMock).getTermName("GO:0000000");
        verifyZeroInteractions(reactomeClientMock, interProTermTraderMock);
    }

    @Test
    public void propertyValuesByTypePlantOntology() throws Exception {
        assertThat(
                subject.propertyValuesByType("PO:0000000"),
                hasEntry(is(BioentityPropertyName.PO), isA(Set.class)));

        verify(goPoTermTraderMock).getTermName("PO:0000000");
        verifyZeroInteractions(reactomeClientMock, interProTermTraderMock);
    }

    @Test
    public void propertyValuesByTypeInterPro() throws Exception {
        assertThat(
                subject.propertyValuesByType("IPR0000000"),
                hasEntry(is(BioentityPropertyName.INTERPRO), isA(Set.class)));

        verify(interProTermTraderMock).getTermName("IPR0000000");
        verifyZeroInteractions(reactomeClientMock, goPoTermTraderMock);
    }

    @Test
    public void typeIsCaseInsensitive() throws Exception {
        assertThat(
                subject.propertyValuesByType("r-hsa-0000000"),
                hasEntry(is(BioentityPropertyName.REACTOME), isA(Set.class)));
        assertThat(
                subject.propertyValuesByType("go:0000000"),
                hasEntry(is(BioentityPropertyName.GO), isA(Set.class)));
        assertThat(
                subject.propertyValuesByType("po:0000000"),
                hasEntry(is(BioentityPropertyName.PO), isA(Set.class)));
        assertThat(
                subject.propertyValuesByType("ipr0000000"),
                hasEntry(is(BioentityPropertyName.INTERPRO), isA(Set.class)));
    }

    @Test
    public void propertyValuesByTypeUnknown() throws Exception {
        Map<BioentityPropertyName, Set<String>> emptyMapOfPropertyValuesByType = ImmutableMap.of();

        assertThat(subject.propertyValuesByType("foobar"), is(emptyMapOfPropertyValuesByType));

        verifyZeroInteractions(reactomeClientMock, goPoTermTraderMock, interProTermTraderMock);
    }

}