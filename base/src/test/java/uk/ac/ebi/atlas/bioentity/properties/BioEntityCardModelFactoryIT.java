package uk.ac.ebi.atlas.bioentity.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties.BIOENTITY_PROPERTY_NAMES;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.DESCRIPTION;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.SYMBOL;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class BioEntityCardModelFactoryIT {

    @Inject
    private BioEntityCardModelFactory subject;

    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private BioEntityPropertyDao bioentityPropertyDao;

    @Test
    public void useIdForTitleIfNoNameIsAvailable() {
        String identifier = "OS07G0213600";
        String name =
                bioentityPropertyDao.fetchPropertyValuesForGeneId(identifier, SYMBOL).stream().collect(joining("/"));

        Map<BioentityPropertyName, Set<String>> propertyValuesByType =
                bioentityPropertyDao.fetchGenePageProperties(identifier);

        Map<String, Object> bioentityCardModel = subject.modelAttributes(
                identifier, speciesFactory.create("oryza sativa"),
                BIOENTITY_PROPERTY_NAMES, name, propertyValuesByType);

        assertThat(bioentityCardModel, hasEntry("entityBriefName", "OS07G0213600"));
        assertThat(bioentityCardModel, hasEntry("entityFullName", "OS07G0213600"));
    }

    @Test
    public void descriptionIsCleanedUp() {
        String identifier = "BRADI5G11000";

        Map<BioentityPropertyName, Set<String>> propertyValuesByType =
                bioentityPropertyDao.fetchGenePageProperties(identifier);

        Map<String, Object> bioentityCardModel = subject.modelAttributes(
                identifier, speciesFactory.create("brachypodium distachyon"),
                BIOENTITY_PROPERTY_NAMES, "", propertyValuesByType);

        assertThat(propertyValuesByType.get(DESCRIPTION).iterator().next().contains("["), is(true));
        assertThat(((String) bioentityCardModel.get("bioEntityDescription")).contains("["), is(false));
    }

}
