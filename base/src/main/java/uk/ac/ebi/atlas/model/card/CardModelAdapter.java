package uk.ac.ebi.atlas.model.card;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class CardModelAdapter {

    public static JsonObject serialize(CardModel cardModel) {
        JsonObject result = new JsonObject();
        result.addProperty("iconType", cardModel.iconType().name().toLowerCase());
        result.addProperty("iconSrc", cardModel.iconSrc());

        cardModel.description().ifPresent(
                description ->
                    result.add("description",
                            getTextAndUrl(description.getLeft(), description.getRight())));

        if (cardModel.content().size() != 0) {
            result.add("content", getContent(cardModel.content()));
        }

        return result;
    }

    public static JsonArray serialize(List<CardModel> cards) {
        JsonArray result = new JsonArray();

        cards.forEach(card -> result.add(serialize(card)));

        return result;
    }

    private static JsonArray getContent(List<Pair<String, Optional<String>>> textAndUrls) {
        JsonArray content = new JsonArray();

        textAndUrls.forEach(textAndUrl -> content.add(
                getTextAndUrl(
                        textAndUrl.getLeft(),
                        textAndUrl.getRight()
                )));

        return content;
    }

    private static JsonObject getTextAndUrl(String text, Optional<String> url) {
        JsonObject textAndUrl = new JsonObject();
        textAndUrl.addProperty("text", text);
        url.ifPresent(s -> textAndUrl.addProperty("url", s));

        return textAndUrl;
    }
}
