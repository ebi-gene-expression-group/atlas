package uk.ac.ebi.atlas.bioentity.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BioEntityCardModelBuilderIT {

    @Inject
    BioEntityCardModelBuilder subject;

    @Inject
    SpeciesFactory speciesFactory;

    @Inject
    BioEntityPropertyDao bioentityPropertyDao;

    @Test
    public void useIdForTitleIfNoNameIsAvailable() throws Exception {
        String identifier = "OS07G0213600";
        String name = bioentityPropertyDao.fetchPropertyValuesForGeneId(identifier, BioentityPropertyName.SYMBOL).stream()
                .collect(Collectors.joining("/"));

        Map<BioentityPropertyName, Set<String>> propertyValuesByType =
                bioentityPropertyDao.fetchGenePageProperties(identifier);

        Map<String, Object> bioentityCardModel = subject.modelAttributes(
                identifier, speciesFactory.create("oryza sativa"),
                BioEntityCardProperties.bioentityPropertyNames, name, propertyValuesByType);

        assertThat(bioentityCardModel, hasEntry("entityBriefName", "OS07G0213600"));
        assertThat(bioentityCardModel, hasEntry("entityFullName", "OS07G0213600"));
    }

    @Test
    public void descriptionIsCleanedUp() throws Exception {
        String identifier = "BRADI5G11000";

        Map<BioentityPropertyName, Set<String>> propertyValuesByType =
                bioentityPropertyDao.fetchGenePageProperties(identifier);

        Map<String, Object> bioentityCardModel = subject.modelAttributes(
                identifier, speciesFactory.create("brachypodium distachyon"),
                BioEntityCardProperties.bioentityPropertyNames, "", propertyValuesByType);

        assertThat(propertyValuesByType.get(BioentityPropertyName.DESCRIPTION).iterator().next().contains("["), is(true));
        assertThat(((String)bioentityCardModel.get("bioEntityDescription")).contains("["), is(false));
    }

}