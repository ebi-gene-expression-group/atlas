package uk.ac.ebi.atlas.model;

import com.google.gson.JsonObject;

public interface Expression {
    double getLevel();
    boolean isKnown();
    JsonObject toJson();
}
