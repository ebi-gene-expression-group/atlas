package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.bioentity.go.GoPoTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BioEntityPropertyServiceTest {
    private static final OntologyTerm GO_0000001 =
            OntologyTerm.create("GO:0000001", "mitochondrion inheritance", "", 6);

    private static final Collection<BioentityPropertyName> UNMAPPED_PROPERTY_NAMES =
            ImmutableSet.of(
                    BioentityPropertyName.ORTHOLOG,
                    BioentityPropertyName.PATHWAYID,
                    BioentityPropertyName.GO,
                    BioentityPropertyName.PO,
                    BioentityPropertyName.INTERPRO);

    @Mock
    private SpeciesInferrer speciesInferrerMock;

    @Mock
    private BioEntityPropertyDao bioEntityPropertyDaoMock;

    @Mock
    private ReactomeClient reactomeClientMock;

    @Mock
    private GoPoTrader goPoTermTraderMock;

    @Mock
    private InterProTrader interProTermTraderMock;

    private BioEntityPropertyService subject;

    @Before
    public void setUp() throws Exception {
        subject =
                new BioEntityPropertyService(
                        speciesInferrerMock, bioEntityPropertyDaoMock, reactomeClientMock, goPoTermTraderMock,
                        interProTermTraderMock);
    }

    @Test
    public void unmappedPropertiesAreReturnedUnchanged() {
        for (BioentityPropertyName bioentityPropertyName : BioentityPropertyName.values()) {
            if (!UNMAPPED_PROPERTY_NAMES.contains(bioentityPropertyName)) {
                assertThat(
                        subject.mapToLinkText(bioentityPropertyName, ImmutableSet.of("foobar"), false),
                        hasEntry(is("foobar"), is("foobar")));
            }
        }
    }


    @Test
    public void reactomeTermsAreMapped() {
        when(reactomeClientMock.getPathwayNames(any())).thenReturn(ImmutableMap.of("R-HSA-31337", "foobar"));
        subject.mapToLinkText(BioentityPropertyName.PATHWAYID, ImmutableSet.of("R-HSA-31337"), false);

        verify(reactomeClientMock, times(1)).getPathwayNames(eq(ImmutableSet.of("R-HSA-31337")));
    }

    @Test
    public void goTermsAreMapped() {
        when(goPoTermTraderMock.get(anyString())).thenReturn(Optional.of(OntologyTerm.create("foobar")));
        subject.mapToLinkText(BioentityPropertyName.GO, ImmutableSet.of("foobar"), false);

        verify(goPoTermTraderMock, times(1)).get(eq("foobar"));
    }

    @Test
    public void poTermsAreMapped() {
        when(goPoTermTraderMock.get(anyString())).thenReturn(Optional.of(OntologyTerm.create("foobar")));
        subject.mapToLinkText(BioentityPropertyName.PO, ImmutableSet.of("foobar"), false);

        verify(goPoTermTraderMock, times(1)).get(eq("foobar"));
    }

    @Test
    public void interproTermsAreMapped() {
        when(interProTermTraderMock.get(anyString())).thenReturn(Optional.of(OntologyTerm.create("foobar")));
        subject.mapToLinkText(BioentityPropertyName.INTERPRO, ImmutableSet.of("foobar"), false);

        verify(interProTermTraderMock, times(1)).get(eq("foobar"));
    }

    @Test
    public void unknownSpeciesAreOmittedFromOrthologs() {
        // Unknown species are species not found in the analytics core, i.e. with no genes above default threshold
        when(speciesInferrerMock.inferSpeciesForGeneQuery(
                argThat(semanticQuery -> semanticQuery.iterator().next().value().startsWith("ENSCING"))))
                .thenReturn(new Species("Ciona intestinalis", SpeciesProperties.UNKNOWN));

        // ENSCING00000014543 is an ortholog of e.g. ENSPANG00000000529
        assertThat(
                subject.mapToLinkText(BioentityPropertyName.ORTHOLOG, ImmutableSet.of("ENSCING00000014543"), false),
                hasEntry("ENSCING00000014543", "ENSCING00000014543"));
        verifyZeroInteractions(bioEntityPropertyDaoMock);
    }

    @Test
    public void useGeneIdIfNoGeneNameAvailable() {
        when(speciesInferrerMock.inferSpeciesForGeneQuery(
                argThat(semanticQuery -> semanticQuery.iterator().next().value().startsWith("VIT"))))
                .thenReturn(new Species("Vitis vinifera",
                        SpeciesProperties.create(
                                "vitis_vinifera", "DEVELOPMENTAL_STAGE", "plants", ImmutableSet.of())));

        when(bioEntityPropertyDaoMock.fetchPropertyValuesForGeneId(
                eq("VIT_01s0026g00140"), eq(BioentityPropertyName.SYMBOL)))
                .thenReturn(ImmutableSet.of());

        assertThat(
                subject.mapToLinkText(BioentityPropertyName.ORTHOLOG, ImmutableSet.of("VIT_01s0026g00140"), false),
                hasEntry("VIT_01s0026g00140", "VIT_01s0026g00140 (Vitis vinifera)"));
    }

    @Test
    public void onlyGoTermsHaveDepth() {
        when(goPoTermTraderMock.get("GO:0000001")).thenReturn(Optional.of(GO_0000001));

        for (BioentityPropertyName bioentityPropertyName : BioentityPropertyName.values()) {
            if (bioentityPropertyName != BioentityPropertyName.GO) {
                assertThat(subject.assessRelevance(bioentityPropertyName, "foobar"), is(0));
            }
        }

        assertThat(subject.assessRelevance(BioentityPropertyName.GO, "GO:0000001"), is(GO_0000001.depth()));
    }

}
