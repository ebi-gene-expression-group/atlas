package uk.ac.ebi.atlas.profiles.json;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class ExternallyViewableProfilesList<DataColumnDescriptor extends DescribesDataColumns,
        Prof extends Profile<DataColumnDescriptor, ? extends Expression>> {

    private final GeneProfilesList<Prof> profiles;

    private final Function<Prof, URI> provideLinkToProfile;

    private final List<DataColumnDescriptor> prescribedOrderOfColumns;

    public ExternallyViewableProfilesList(GeneProfilesList<Prof> profiles, Function<Prof, URI> provideLinkToProfile, List<DataColumnDescriptor> prescribedOrderOfColumns){
        this.profiles = profiles;
        this.provideLinkToProfile = provideLinkToProfile;
        this.prescribedOrderOfColumns = prescribedOrderOfColumns;
    }

    public JsonObject asJson(){
        JsonObject result = new JsonObject();
        for(Map.Entry<String, String> e: profiles.properties().entrySet()){
            result.addProperty(e.getKey(), e.getValue());
        }

        JsonArray rows = new JsonArray();
        for(Prof profile : profiles){
            rows.add(convert(profile));
        }
        result.add("rows", rows);

        return result;
    }

    private JsonObject convert(Prof profile){

        JsonObject result = new JsonObject();
        for(Map.Entry<String, String> e: profile.properties().entrySet()){
            result.addProperty(e.getKey(), e.getValue());
        }
        JsonArray expressions = new JsonArray();
        for(DataColumnDescriptor c: prescribedOrderOfColumns){
            if(profile.isExpressedOnAnyOf(ImmutableSet.of(c))){
                expressions.add(profile.getExpression(c).toJson());
            } else {
                expressions.add(new JsonObject());
            }
        }
        result.add("expressions", expressions);
        try {
            result.addProperty("uri", provideLinkToProfile.apply(profile).toURL().toExternalForm());
        } catch (MalformedURLException e){
            throw new IllegalStateException(e);
        }

        return result;

    }



}
