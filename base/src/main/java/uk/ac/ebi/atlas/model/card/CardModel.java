package uk.ac.ebi.atlas.model.card;

import com.google.auto.value.AutoValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

@AutoValue
public abstract class CardModel {
    public abstract CardIconType iconType();
    public abstract String iconSrc();
    public abstract Pair<Optional<String>, Optional<String>> description();
    public abstract List<Pair<String, Optional<String>>> content();

    public static CardModel create(CardIconType iconType,
                                   String iconSrc,
                                   List<Pair<String, Optional<String>>> content) {
        return new AutoValue_CardModel(
                iconType,
                iconSrc,
                Pair.of(Optional.empty(), Optional.empty()),
                content
        );
    }

    public static CardModel create(CardIconType iconType,
                                   String iconSrc,
                                   Pair<Optional<String>, Optional<String>> description,
                                   List<Pair<String, Optional<String>>> content) {
        return new AutoValue_CardModel(
                iconType,
                iconSrc,
                description,
                content
        );
    }
}
