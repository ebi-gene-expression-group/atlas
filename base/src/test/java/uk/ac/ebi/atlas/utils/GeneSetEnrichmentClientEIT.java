package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class GeneSetEnrichmentClientEIT {

    @Inject
    GeneSetEnrichmentClient subject;

    @Inject
    SpeciesFactory speciesFactory;

    @Test
    public void getAnythingBack(){
        Object response = subject.fetchEnrichedGenes(new Species("", SpeciesProperties.UNKNOWN), Collections.EMPTY_SET);

        Assert.assertThat(response.toString(), not(isEmptyOrNullString()));
    }

    @Test
    public void getSomethingSuccessfulBack() {
        List<String> arabidopsisGenes = Arrays.asList((
                 "AT1G48030 AT1G53240 AT2G17130 " +
                 "AT2G20420 AT2G44350 AT2G47510 " +
                 "AT3G15020 AT3G17240 AT3G27380 " +
                 "AT3G55410 AT3G60100 AT4G26910").split(" "));
        Pair<Optional<String>, Optional<JsonArray>> result =
                subject.fetchEnrichedGenes(speciesFactory.create
                ("arabidopsis thaliana"), arabidopsisGenes);

        assertThat(GeneSetEnrichmentClient.isSuccess(result), is(true));

        Pair<Optional<String>, Optional<JsonArray>> resultForReversedList =
                subject.fetchEnrichedGenes(speciesFactory.create
                        ("arabidopsis thaliana"), Lists.reverse(arabidopsisGenes));

        assertThat(GeneSetEnrichmentClient.isSuccess(resultForReversedList), is(true));

        assertEquals(result.getRight().get().size(), resultForReversedList.getRight().get().size());
        for(int i =0 ; i< result.getRight().get().size(); i++){
            assertEquals(result.getRight().get().get(i),
                    resultForReversedList.getRight().get().get(i));
        }
    }
}