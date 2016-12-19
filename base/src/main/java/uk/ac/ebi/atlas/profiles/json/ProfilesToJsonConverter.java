package uk.ac.ebi.atlas.profiles.json;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class ProfilesToJsonConverter<Condition, Expr extends Expression> {

    private final Function<Profile<Condition, Expr>, URI> provideLinkToProfile;

    public ProfilesToJsonConverter createForCrossExperimentResults(final URI experimentsExternalLocation,
                                                                   final Map<String, String> params){
        return new ProfilesToJsonConverter(new Function<Profile<?,?>, URI>() {
            @Nullable
            @Override
            public URI apply(@Nullable Profile profile) {
                return experimentsExternalLocation.resolve(profile.getId()+"?"+ Joiner.on("&").withKeyValueSeparator
                        ("=").join(params.entrySet()));
            }
        });
    }

    public ProfilesToJsonConverter(Function<Profile<Condition, Expr>, URI> provideLinkToProfile){
        this.provideLinkToProfile = provideLinkToProfile;
    }

    public JsonObject convert(GeneProfilesList<? extends Profile<Condition, Expr>> profiles,
                              List<Condition> prescribedOrderOfRows){
        JsonObject result = new JsonObject();
        result.addProperty("searchResultTotal", profiles.getTotalResultCount());

        JsonArray rows = new JsonArray();
        for(Profile<Condition, Expr> profile : profiles){
            rows.add(convert(profile, prescribedOrderOfRows));
        }
        result.add("rows", rows);

        return result;
    }

    public JsonObject convert(Profile<Condition, Expr> profile,
                            List<Condition> prescribedOrderOfRows){

        JsonObject result = new JsonObject();
        for(Map.Entry<String, String> e: profile.properties().entrySet()){
            result.addProperty(e.getKey(), e.getValue());
        }
        JsonArray expressions = new JsonArray();
        for(Condition c: prescribedOrderOfRows){
            expressions.add(profile.getExpression(c).toJson());
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
