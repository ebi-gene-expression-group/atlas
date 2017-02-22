package uk.ac.ebi.atlas.model;

import com.google.gson.JsonObject;

public interface Expression {
    double getLevel();
    JsonObject toJson();
}
