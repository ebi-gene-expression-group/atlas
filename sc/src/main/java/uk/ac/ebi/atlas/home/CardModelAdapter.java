package uk.ac.ebi.atlas.home;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class CardModelAdapter {

    public static JsonArray serialize(List<CardModel> cards) {
        JsonArray result = new JsonArray();

        cards.forEach(card -> {
             result.add(getIconObject(
                     card.iconType(),
                     card.iconSrc(),
                     card.iconDescription(),
                     card.content()));
        });

        return result;
    }

    private static JsonObject getIconObject(
            String iconType,
            String iconSrc,
            Optional<String> iconDescription,
            List<Pair<String, Optional<String>>> content) {
        JsonObject result = new JsonObject();
        result.addProperty("iconType", iconType);
        result.addProperty("iconSrc", iconSrc);

        iconDescription.ifPresent(description -> result.addProperty("iconDescription", description));

        result.add("content", getContent(content));

        return result;
    }


    private static JsonArray getContent(List<Pair<String, Optional<String>>> textAndUrls) {
        JsonArray content = new JsonArray();

        textAndUrls.forEach(textAndUrl -> content.add(
                getTextAndUrlAsJson(
                        textAndUrl.getLeft(),
                        textAndUrl.getRight()
                )));

        return content;
    }

    private static JsonObject getTextAndUrlAsJson(String text, Optional<String> url) {
        JsonObject textAndUrl = new JsonObject();
        textAndUrl.addProperty("text", text);
        url.ifPresent(s -> textAndUrl.addProperty("url", s));

        return textAndUrl;
    }
}
