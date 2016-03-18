package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
public class CoexpressedGenesService {

    @Inject
    CoexpressedGenesDao coexpressedGenesDao;

    public String coexpressedGenesFor(String experiment, String identifier){
        return new Gson().toJson(coexpressedGenesDao.coexpressedGenesFor(experiment,identifier), Set.class);
    }
}
