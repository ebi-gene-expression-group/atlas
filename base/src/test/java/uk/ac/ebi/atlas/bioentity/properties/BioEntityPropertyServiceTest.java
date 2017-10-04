package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.bioentity.go.GoPoTrader;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.utils.UniProtClient;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BioEntityPropertyServiceTest {


    @Mock
    UniProtClient uniProtClient;
    @Mock
    ArrayDesignDAO arrayDesignDAO;
    @Mock
    BioEntityPropertyLinkBuilder linkBuilder;
    @Mock GoPoTrader goPoTermTrader;

    BioEntityPropertyService subject;

    String identifier = "identifier";

    Species species = new SpeciesFactory(null).createUnknownSpecies();

    @Before
    public void setUp(){
        subject = new BioEntityPropertyService(linkBuilder, arrayDesignDAO, goPoTermTrader);
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

    /*
    won't work because BioEntityPropertyLinkBuilder is mocked out.
    in actual code: maybe will work.
    We want BioEntityPropertyLinkBuilder not to make more queries, though!
     */
    @Ignore
    @Test
    public void somethingAboutProperties() throws Exception {
        assertThat(
                subject.bioentityProperties(identifier, species, ImmutableList.of(BioentityPropertyName.GO), ImmutableMap.of(BioentityPropertyName.GO, ImmutableSet.of("value"))),
                not(is(new JsonArray()))
        );
    }

}