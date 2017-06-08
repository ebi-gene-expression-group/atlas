package uk.ac.ebi.atlas.model;

import com.esotericsoftware.kryo.KryoSerializable;
import com.google.gson.JsonObject;

public interface Expression {
    String getDataColumnDescriptorId();
    double getLevel();
    boolean isKnown();
    JsonObject toJson();
}
