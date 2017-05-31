package uk.ac.ebi.atlas.controllers;

import com.google.gson.JsonObject;

public class ReturnsJsonErrors {

    protected JsonObject jsonError(String message){
        JsonObject result = new JsonObject();
        result.addProperty("error", message);
        return result;
    }
}
