package uk.ac.ebi.atlas.profiles.json;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.annotation.Nullable;
import javax.annotation.Untainted;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class ProfilesToJsonConverter<Condition,P extends Profile<Condition, ? extends Expression>> {

    private final Function<P, URI> provideLinkToProfile;

    public static ProfilesToJsonConverter<AssayGroup, BaselineExperimentProfile>
        createForCrossExperimentResults(final URI experimentsExternalLocation, final Map<String, String> params){
        return new ProfilesToJsonConverter<>(new Function<BaselineExperimentProfile, URI>() {
            @Nullable
            @Override
            public URI apply(@Nullable BaselineExperimentProfile profile) {
                Map<String, String> m = new HashMap<>(params);
                /*
                TODO
                This should link to the columns that are shown in the slice
                For that you need to decide how to link to them :)
                 */
                return experimentsExternalLocation.resolve(profile.getId()+"?"+ Joiner.on("&").withKeyValueSeparator
                        ("=").join(m.entrySet()));
            }
        });
    }

    public ProfilesToJsonConverter(Function<P, URI> provideLinkToProfile){
        this.provideLinkToProfile = provideLinkToProfile;
    }

    public JsonObject convert(GeneProfilesList<P> profiles,
                              List<Condition> prescribedOrderOfColumns){
        JsonObject result = new JsonObject();
        for(Map.Entry<String, String> e: profiles.properties().entrySet()){
            result.addProperty(e.getKey(), e.getValue());
        }

        JsonArray rows = new JsonArray();
        for(P profile : profiles){
            rows.add(convert(profile, prescribedOrderOfColumns));
        }
        result.add("rows", rows);

        return result;
    }

    public JsonObject convert(P profile, List<Condition> prescribedOrderOfColumns){

        JsonObject result = new JsonObject();
        for(Map.Entry<String, String> e: profile.properties().entrySet()){
            result.addProperty(e.getKey(), e.getValue());
        }
        JsonArray expressions = new JsonArray();
        for(Condition c: prescribedOrderOfColumns){
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
