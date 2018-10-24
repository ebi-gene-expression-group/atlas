package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.testutils.SolrUtils;

import javax.inject.Inject;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties.BIOENTITY_PROPERTY_NAMES;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.DESCRIPTION;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.SYMBOL;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.IDENTIFIER_SEARCH;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BioEntityCardModelFactoryIT {
    @Inject
    private SolrUtils solrUtils;

    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private BioEntityPropertyDao bioentityPropertyDao;

    @Inject
    private BioEntityCardModelFactory subject;

    @ParameterizedTest
    @MethodSource("geneIdWithoutSymbolProvider")
    void useIdAsTitleIfNoNameIsAvailable(String geneId) {
        String name =
                bioentityPropertyDao.fetchPropertyValuesForGeneId(geneId, SYMBOL).stream().collect(joining("/"));

        Map<BioentityPropertyName, Set<String>> propertyValuesByType =
                bioentityPropertyDao.fetchGenePageProperties(geneId);

        Map<String, Object> bioentityCardModel = subject.modelAttributes(
                geneId, speciesFactory.create("Crocubot"), BIOENTITY_PROPERTY_NAMES, name, propertyValuesByType);

        assertThat(bioentityCardModel)
            .containsAllEntriesOf(
                    ImmutableMap.of(
                            "entityBriefName", geneId,
                            "entityFullName", geneId));
    }

    @ParameterizedTest
    @MethodSource("geneIdProviderWithSourceInDescriptionProvider")
    void descriptionIsCleanedUp(String geneId) {
        Map<BioentityPropertyName, Set<String>> propertyValuesByType =
                bioentityPropertyDao.fetchGenePageProperties(geneId);

        Map<String, Object> bioentityCardModel = subject.modelAttributes(
                geneId, speciesFactory.create("Crocubot"),
                BIOENTITY_PROPERTY_NAMES, "", propertyValuesByType);

        assertThat(propertyValuesByType.get(DESCRIPTION).iterator().next()).contains("[");
        assertThat(((String) bioentityCardModel.get("bioEntityDescription"))).doesNotContain("[");
    }

    private Stream<String> geneIdWithoutSymbolProvider() {
        return Stream.of(solrUtils.fetchRandomGeneWithoutSymbolFromAnalytics());
    }

    private Stream<String> geneIdProviderWithSourceInDescriptionProvider() {
        return Stream.of(solrUtils.fetchRandomGeneIdFromAnalytics(IDENTIFIER_SEARCH, "[Source:"));
    }
}
