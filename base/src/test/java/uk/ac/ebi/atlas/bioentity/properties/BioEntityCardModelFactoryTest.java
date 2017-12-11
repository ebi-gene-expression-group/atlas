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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BioEntityCardModelFactoryTest {

    @Mock
    ArrayDesignDAO arrayDesignDAO;

    @Mock
    BioEntityPropertyService linkBuilder;

    BioEntityCardModelFactory subject;

    String identifier = "identifier";

    Species species = new SpeciesFactory(null).createUnknownSpecies();

    @Before
    public void setUp() throws Exception {
        subject = new BioEntityCardModelFactory(linkBuilder, arrayDesignDAO);
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
    public void nullAttributes() throws Exception {
        Map<String, Object> map = subject.modelAttributes(identifier, species, ImmutableList.of(), "", ImmutableMap.of());

        assertThat(map.size(), is(5));
        assertThat(map.get("bioentityProperties"), is(ImmutableMap.of()));
        assertThat(map.get("propertyNames"), is(ImmutableList.of()));
        assertThat(map.get("entityBriefName"), is(identifier));
        assertNull(map.get("bioentityDescription"));
        assertThat(map.get("entityFullName"), is(identifier));
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

    @Test
    public void outputLooksRight2() throws Exception {
        when(linkBuilder.mapToLinkText(BioentityPropertyName.GO, ImmutableSet.of("value")))
                .thenReturn(ImmutableMap.of("value", "value"));

        Map<String, Object> result = subject.modelAttributes(identifier,
                species, ImmutableList.of(BioentityPropertyName.GO), "",
                ImmutableMap.of(BioentityPropertyName.GO, ImmutableSet.of("value")));

        PropertyLink propertyLink =
                new PropertyLink("value",
                        "http://www.ebi.ac.uk/ols/ontologies/go/terms?iri=http://purl.obolibrary.org/obo/value",
                        0);

        for (Map.Entry<String, Object> entry : result.entrySet()) {
            String key = entry.getKey();

            if (key.equals("bioentityProperties")) {
                Map<BioentityPropertyName, List<PropertyLink>> v =
                        (Map<BioentityPropertyName, List<PropertyLink>>) entry.getValue();
                assertThat(v.size(), is(1));
                assertTrue(v.containsKey(BioentityPropertyName.GO));
                assertThat(v.values().size(), is(1));

                assertThat(v.get(BioentityPropertyName.GO).get(0).getText(), is(propertyLink.getText()));
                assertThat(v.get(BioentityPropertyName.GO).get(0).getUrl(), is(propertyLink.getUrl()));

            }
        }

    }
}