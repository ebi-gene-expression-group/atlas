package uk.ac.ebi.atlas.search;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class SemanticQueryTermTest {
    @Test
    public void termsWithoutCategory() {
        String randomValue = random(128);
        assertThat(SemanticQueryTerm.create(randomValue))
                .isEqualTo(SemanticQueryTerm.create(randomValue, null))
                .isEqualTo(SemanticQueryTerm.create(randomValue, ""))
                .isEqualTo(SemanticQueryTerm.create(randomValue, " "))
                .isEqualTo(SemanticQueryTerm.create(randomValue, " \t"))
                .hasFieldOrPropertyWithValue("category", Optional.empty());
    }

    @Test
    public void hasValue() {
        assertThat(SemanticQueryTerm.create("").hasValue())
                .isEqualTo(SemanticQueryTerm.create(" ").hasValue())
                .isEqualTo(SemanticQueryTerm.create(" \t").hasValue())
                .isFalse();
    }

    @Test
    public void description() {
        String randomValue = random(128);
        String randomCategory = random(128);
        assertThat(
                SemanticQueryTerm.create(
                        randomValue, randomCategory).description())
                .isEqualTo(randomValue + " (" + randomCategory + ")");
        assertThat(SemanticQueryTerm.create(randomValue, "").description())
                .isEqualTo(SemanticQueryTerm.create(randomValue).description())
                .isEqualTo(randomValue);
    }

    @Test
    public void valueCannotBeNull() {
        assertThatNullPointerException().isThrownBy(() -> SemanticQueryTerm.create(null, "Some category"));
    }

    @Test
    public void toJson() {
        String randomValue = random(128);
        String randomCategory = random(128);
        Gson gson =
                new GsonBuilder()
                        .registerTypeAdapter(
                                SemanticQueryTerm.create("").getClass(),
                                SemanticQueryTerm.getGsonTypeAdapter())
                        .create();

        assertThat(
                gson.fromJson(
                        gson.toJson(SemanticQueryTerm.create(randomValue, randomCategory)),
                        SemanticQueryTerm.create("").getClass()))
                .isEqualTo(SemanticQueryTerm.create(randomValue, randomCategory));

        assertThat(
                gson.fromJson(
                        gson.toJson(SemanticQueryTerm.create(randomValue)),
                        SemanticQueryTerm.create("").getClass()))
                .isEqualTo(SemanticQueryTerm.create(randomValue));
    }
}
