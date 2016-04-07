package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Named
public class CoexpressedGenesService {

    @Inject
    CoexpressedGenesDao coexpressedGenesDao;

    public String coexpressedGenesFor(String experiment, String identifier) {
        return new Gson().toJson(coexpressedGenesDao.coexpressedGenesFor(experiment, identifier), Set.class);
    }

}
