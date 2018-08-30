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
import uk.ac.ebi.atlas.dao.ArrayDesignDao;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BioEntityCardModelFactoryTest {
    private static final BioentityPropertyName BIOENTITY_PROPERTY_NAME_GO = BioentityPropertyName.GO;
    private static final String GENE_ID = "identifier";
    private static final Species SPECIES = new SpeciesFactory(null).createUnknownSpecies();

    @Mock
    private ArrayDesignDao arrayDesignDaoMock;

    @Mock
    private BioEntityPropertyService linkBuilderMock;

    private BioEntityCardModelFactory subject;

    @Before
    public void setUp() throws Exception {
        subject = new BioEntityCardModelFactory(linkBuilderMock, arrayDesignDaoMock);
    }


    @Test
    public void nullCases() {
        assertThat(subject.bioentityProperties(GENE_ID, SPECIES, ImmutableList.of(), ImmutableMap.of()))
                .isEqualTo(new JsonArray());

        assertThat(
                subject.bioentityProperties(
                        GENE_ID, SPECIES,
                        ImmutableList.of(BioentityPropertyName.GO),
                        ImmutableMap.of()))
                .isEqualTo(new JsonArray());

        assertThat(
                subject.bioentityProperties(
                        GENE_ID,
                        SPECIES,
                        ImmutableList.of(BioentityPropertyName.GO),
                        ImmutableMap.of(BioentityPropertyName.PATHWAYID, ImmutableSet.of())))
                .isEqualTo(new JsonArray());

        assertThat(
                subject.bioentityProperties(
                        GENE_ID,
                        SPECIES,
                        ImmutableList.of(BioentityPropertyName.GO),
                        ImmutableMap.of(BioentityPropertyName.GO, ImmutableSet.of()))).
                isEqualTo(new JsonArray());
    }

    @Test
    public void outputLooksRight() {
        when(linkBuilderMock.mapToLinkText(BIOENTITY_PROPERTY_NAME_GO, ImmutableSet.of("value"), false))
                .thenReturn(ImmutableMap.of("value", "value"));

        JsonArray result =
                subject.bioentityProperties(
                        GENE_ID,
                        SPECIES,
                        ImmutableList.of(BIOENTITY_PROPERTY_NAME_GO),
                        ImmutableMap.of(BIOENTITY_PROPERTY_NAME_GO, ImmutableSet.of("value")));

        JsonObject property = result.get(0).getAsJsonObject();

        assertThat(property.get("type").getAsString()).isEqualTo(BIOENTITY_PROPERTY_NAME_GO.name);
        assertThat(property.get("name").getAsString()).isEqualTo(BIOENTITY_PROPERTY_NAME_GO.label);

        JsonObject value = property.get("values").getAsJsonArray().get(0).getAsJsonObject();
        assertThat(value.get("text").getAsString()).isEqualTo("value");
        assertThat(value.get("url").getAsString())
                .isEqualTo("https://www.ebi.ac.uk/ols/ontologies/go/terms?iri=http://purl.obolibrary.org/obo/value");
        assertThat(value.get("relevance").getAsInt()).isEqualTo(0);
    }

}
