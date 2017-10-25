package uk.ac.ebi.atlas.bioentity.geneset;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.bioentity.go.GoPoTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import java.util.Map;
import java.util.Optional;
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

    private final static OntologyTerm GO_TERM = OntologyTerm.create("GO:FOOBAR", "some GO term name");
    private final static OntologyTerm PO_TERM = OntologyTerm.create("PO:FOOBAR", "some PO term name");
    private final static OntologyTerm INTERPRO_TERM = OntologyTerm.create("IPR3117", "some InterPro term name");

    @Mock
    GoPoTrader goPoTermTraderMock;

    @Mock
    InterProTrader interProTermTraderMock;

    @Mock
    ReactomeClient reactomeClientMock;

    GeneSetPropertyService subject;

    @Before
    public void setUp() throws Exception {
        when(reactomeClientMock.getPathwayName(startsWith("R-"))).thenReturn(Optional.of("some pathway name"));
        when(goPoTermTraderMock.get(startsWith("GO:"))).thenReturn(Optional.of(GO_TERM));
        when(goPoTermTraderMock.get(startsWith("PO:"))).thenReturn(Optional.of(PO_TERM));
        when(interProTermTraderMock.get(startsWith("IPR"))).thenReturn(Optional.of(INTERPRO_TERM));
        subject = new GeneSetPropertyService(goPoTermTraderMock, interProTermTraderMock, reactomeClientMock);
    }

    @Test
    public void propertyValuesByTypeReactome() throws Exception {
        assertThat(
                subject.propertyValuesByType("R-HSA-0000000"),
                hasEntry(is(BioentityPropertyName.PATHWAYID), isA(Set.class)));

        verify(reactomeClientMock).getPathwayName(anyString());
        verifyZeroInteractions(goPoTermTraderMock, interProTermTraderMock);
    }

    @Test
    public void propertyValuesByTypeGeneOntology() throws Exception {
        assertThat(
                subject.propertyValuesByType("GO:0000000"),
                hasEntry(is(BioentityPropertyName.GO), isA(Set.class)));

        verify(goPoTermTraderMock).get("GO:0000000");
        verifyZeroInteractions(reactomeClientMock, interProTermTraderMock);
    }

    @Test
    public void propertyValuesByTypePlantOntology() throws Exception {
        assertThat(
                subject.propertyValuesByType("PO:0000000"),
                hasEntry(is(BioentityPropertyName.PO), isA(Set.class)));

        verify(goPoTermTraderMock).get("PO:0000000");
        verifyZeroInteractions(reactomeClientMock, interProTermTraderMock);
    }

    @Test
    public void propertyValuesByTypeInterPro() throws Exception {
        assertThat(
                subject.propertyValuesByType("IPR0000000"),
                hasEntry(is(BioentityPropertyName.INTERPRO), isA(Set.class)));

        verify(interProTermTraderMock).get("IPR0000000");
        verifyZeroInteractions(reactomeClientMock, goPoTermTraderMock);
    }

    @Test
    public void typeIsCaseInsensitive() throws Exception {
        assertThat(
                subject.propertyValuesByType("r-hsa-0000000"),
                hasEntry(is(BioentityPropertyName.PATHWAYID), isA(Set.class)));
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