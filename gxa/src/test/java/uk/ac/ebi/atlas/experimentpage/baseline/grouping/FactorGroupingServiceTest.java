package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FactorGroupingServiceTest {

    @Mock
    OrganismPartGroupingService organismPartGroupingService;


    FactorGroupingService subject;

    @Before
    public void setUp() {
        subject = new FactorGroupingService(organismPartGroupingService);
    }

    @Test
    public void hasOntologyTerms() {

        assertThat(
                subject.hasOntologyTerms(ImmutableMap.of()),
                is(false)
        );

        assertThat(
                subject.hasOntologyTerms(ImmutableMap.of(ColumnGroup.create("", ""), ImmutableSet.of())),
                is(false)
        );

        assertThat(
                subject.hasOntologyTerms(ImmutableMap.of(ColumnGroup.create("", ""), ImmutableSet.of(OntologyTerm.create("")))),
                is(true)
        );

    }

    @Test
    public void noOrganismPartNoFactorGrouping() {
        assertThat(
                subject.group("DEVELOPMENTAL_STAGE", null),
                is(new JsonArray())
        );
    }

    @Test
    public void noResultsNoFactorGrouping() {

        Collection<OntologyTerm> ontologyTermsAcrossExperiments = mock(Collection.class);
        when(organismPartGroupingService.getAnatomicalSystemsGrouping(ontologyTermsAcrossExperiments)).thenReturn(ImmutableMap.of());
        when(organismPartGroupingService.getOrgansGrouping(ontologyTermsAcrossExperiments)).thenReturn(ImmutableMap.of());
        assertThat(
                subject.groupOrganismPartOntologyTerms(ontologyTermsAcrossExperiments),
                is(new JsonArray())
        );
    }

    @Test
    public void noOntologyTermsNoFactorGrouping() {

        Collection<OntologyTerm> ontologyTermsAcrossExperiments = mock(Collection.class);
        when(organismPartGroupingService.getAnatomicalSystemsGrouping(ontologyTermsAcrossExperiments)).thenReturn(ImmutableMap.of(ColumnGroup.create("", ""), ImmutableSet.of()));
        when(organismPartGroupingService.getOrgansGrouping(ontologyTermsAcrossExperiments)).thenReturn(ImmutableMap.of());
        assertThat(
                subject.groupOrganismPartOntologyTerms(ontologyTermsAcrossExperiments),
                is(new JsonArray())
        );
    }

    @Test
    public void ontologyTermsGivesFactorGrouping() {

        Collection<OntologyTerm> ontologyTermsAcrossExperiments = mock(Collection.class);
        when(organismPartGroupingService.getAnatomicalSystemsGrouping(ontologyTermsAcrossExperiments)).thenReturn(ImmutableMap.of(ColumnGroup.create("", ""), ImmutableSet.of(OntologyTerm.create(""))));
        when(organismPartGroupingService.getOrgansGrouping(ontologyTermsAcrossExperiments)).thenReturn(ImmutableMap.of());
        assertThat(
                subject.groupOrganismPartOntologyTerms(ontologyTermsAcrossExperiments).size(),
                is(1)
        );
    }

    @Test
    public void ontologyTermsGivesFactorGrouping2() {

        Collection<OntologyTerm> ontologyTermsAcrossExperiments = mock(Collection.class);
        when(organismPartGroupingService.getAnatomicalSystemsGrouping(ontologyTermsAcrossExperiments)).thenReturn(ImmutableMap.of(ColumnGroup.create("", ""), ImmutableSet.of(OntologyTerm.create(""))));
        when(organismPartGroupingService.getOrgansGrouping(ontologyTermsAcrossExperiments)).thenReturn(ImmutableMap.of(ColumnGroup.create("", ""), ImmutableSet.of(OntologyTerm.create(""))));
        assertThat(
                subject.groupOrganismPartOntologyTerms(ontologyTermsAcrossExperiments).size(),
                is(2)
        );
    }

}