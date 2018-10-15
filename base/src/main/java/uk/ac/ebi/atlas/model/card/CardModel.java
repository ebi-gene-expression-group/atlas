package uk.ac.ebi.atlas.model.card;

import com.google.auto.value.AutoValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

@AutoValue
public abstract class CardModel {
    public abstract String iconType();
    public abstract String iconSrc();
    public abstract Optional<String> iconDescription();
    public abstract List<Pair<String, Optional<String>>> content();

    public static CardModel create(String iconType,
                                   String iconSrc,
                                   List<Pair<String, Optional<String>>> content) {
        return new AutoValue_CardModel(
                iconType,
                iconSrc,
                Optional.empty(),
                content
        );
    }

    public static CardModel create(String iconType,
                                   String iconSrc,
                                   String iconDescription,
                                   List<Pair<String, Optional<String>>> content) {
        return new AutoValue_CardModel(
                iconType,
                iconSrc,
                Optional.of(iconDescription),
                content
        );
    }
}
