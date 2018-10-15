package uk.ac.ebi.atlas.model.card;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;


public class CardModelAdapterTest {

    private final static Random RANDOM = new Random();


    @Test
    void emptyListReturnsEmptyJsonArray() {
        assertThat(CardModelAdapter.serialize(Collections.emptyList()))
                .isEmpty();
    }

    @Test
    void cardModelWithoutDescriptionOrContent() {
        CardModel cardModel = getRandomCardModel(false, 0, false);

        JsonObject result = CardModelAdapter.serialize(cardModel);

        assertThat(result.has("iconType")).isTrue();
        assertThat(result.has("iconSrc")).isTrue();
        assertThat(result.has("iconDescription")).isFalse();
        assertThat(result.has("content")).isFalse();
    }

    @Test
    void cardModelWithContentWithoutUrls() {
        CardModel cardModel = getRandomCardModel(true, 3, false);

        JsonObject result = CardModelAdapter.serialize(cardModel);

        assertThat(result.has("iconDescription")).isTrue();

        JsonArray contentResult = result.get("content").getAsJsonArray();
        assertThat(contentResult.size()).isEqualTo(3);

        contentResult.forEach(x -> {
            assertThat(x.getAsJsonObject().has("text"))
                    .isTrue();
            assertThat(x.getAsJsonObject().has("url"))
                    .isFalse();
        });
    }

    @Test
    void cardModelWithFullContent() {
        CardModel cardModel = getRandomCardModel(true, 2, true);

        JsonObject result = CardModelAdapter.serialize(cardModel);

        JsonArray contentResult = result.get("content").getAsJsonArray();
        assertThat(contentResult.size()).isEqualTo(2);

        contentResult.forEach(x -> {
            assertThat(x.getAsJsonObject().has("text"))
                    .isTrue();
            assertThat(x.getAsJsonObject().has("url"))
                    .isTrue();
        });
    }

    @Test
    void cardModelsWithRandomContent() {
        int numberOfCardModels = RANDOM.nextInt(200);

        List<CardModel> cardModels = getRandomCardModels(numberOfCardModels);

        JsonArray result = CardModelAdapter.serialize(cardModels);

        assertThat(result.size()).isEqualTo(numberOfCardModels);
        result.forEach(x -> {
            assertThat(x.getAsJsonObject().has("iconType")).isTrue();
            assertThat(x.getAsJsonObject().has("iconSrc")).isTrue();
        });
    }

    private List<CardModel> getRandomCardModels(int howMany) {
        List<CardModel> cardModels = new ArrayList<>();

        while (howMany > 0) {
            cardModels.add(getRandomCardModel(RANDOM.nextBoolean(), RANDOM.nextInt(100), RANDOM.nextBoolean()));
            howMany--;
        }

        return cardModels;
    }

    private CardModel getRandomCardModel(boolean hasDescription, int numberOfContentItems, boolean contentHasUrls) {
        String iconType = randomAlphanumeric(5);
        String iconSrc = randomAlphanumeric(10);

        List<Pair<String, Optional<String>>> content = new ArrayList<>();

        if (numberOfContentItems > 0) {
            while (numberOfContentItems > 0) {
                if (contentHasUrls) {
                    content.add(Pair.of(randomAlphabetic(10), Optional.of(randomAlphanumeric(20))));
                }
                else {
                    content.add(Pair.of(randomAlphabetic(10), Optional.empty()));
                }
                numberOfContentItems--;
            }
        }

        if (hasDescription) {
            return CardModel.create(iconType, iconSrc, randomAlphabetic(10), content);
        }
        else {
            return CardModel.create(iconType, iconSrc, content);
        }
    }
}
