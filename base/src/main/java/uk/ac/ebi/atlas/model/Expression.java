package uk.ac.ebi.atlas.model;

import com.google.gson.JsonObject;

public interface Expression {
    String getDataColumnDescriptorId();
    double getLevel();
    JsonObject toJson();
}
