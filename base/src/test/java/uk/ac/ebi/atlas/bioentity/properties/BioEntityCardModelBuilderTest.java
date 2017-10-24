package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BioEntityCardModelBuilderTest {

    @Mock
    ArrayDesignDAO arrayDesignDAO;

    @Mock
    BioEntityPropertyService linkBuilder;

    BioEntityCardModelBuilder subject;

    String identifier = "identifier";

    Species species = new SpeciesFactory(null).createUnknownSpecies();

    @Before
    public void setUp() throws Exception {
        subject = new BioEntityCardModelBuilder(linkBuilder, arrayDesignDAO);
    }


    @Test
    public void nullCases() throws Exception {
        assertThat(
                subject.bioentityProperties(identifier, species, ImmutableList.of(), ImmutableMap.of()),
                is(new JsonArray())
        );
        assertThat(
                subject.bioentityProperties(identifier, species, ImmutableList.of(BioentityPropertyName.GO), ImmutableMap.of()),
                is(new JsonArray())
        );
        assertThat(
                subject.bioentityProperties(identifier, species, ImmutableList.of(BioentityPropertyName.GO), ImmutableMap.of(BioentityPropertyName.PATHWAYID, ImmutableSet.of())),
                is(new JsonArray())
        );
        assertThat(
                subject.bioentityProperties(identifier, species, ImmutableList.of(BioentityPropertyName.GO), ImmutableMap.of(BioentityPropertyName.GO, ImmutableSet.of())),
                is(new JsonArray())
        );
    }

    @Test
    public void outputLooksRight() throws Exception {
        when(linkBuilder.mapToLinkText(BioentityPropertyName.GO, ImmutableSet.of("value")))
                .thenReturn(ImmutableMap.of("value", "value"));

        JsonArray result =
                subject.bioentityProperties(
                        identifier,
                        species,
                        ImmutableList.of(BioentityPropertyName.GO),
                        ImmutableMap.of(BioentityPropertyName.GO, ImmutableSet.of("value")));

        JsonObject property = result.get(0).getAsJsonObject();

        assertThat(property.get("type").getAsString(), is("go"));
        assertThat(property.get("name").getAsString(), is("Gene Ontology"));

        JsonObject value = property.get("values").getAsJsonArray().get(0).getAsJsonObject();
        assertThat(value.get("text").getAsString(), is("value"));
        assertThat(value.get("url").getAsString(), is("http://www.ebi.ac.uk/ols/ontologies/go/terms?iri=http://purl.obolibrary.org/obo/value"));
        assertThat(value.get("relevance").getAsInt(), is (0));
    }

}