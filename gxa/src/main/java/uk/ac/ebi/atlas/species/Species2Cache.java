package uk.ac.ebi.atlas.species;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import uk.ac.ebi.atlas.model.Species2;

public class Species2Cache {

    private LoadingCache<String, Species2> species;
    private Species2DAO speciesDAO;

    public Species2Cache() {
        LoadingCache<String, Species2> species = CacheBuilder.newBuilder()
                .build(
                        new CacheLoader<String, Species2>() {
                            public Species2 load(String key)  {
                                return speciesDAO.fetchSpecies(key);
                            }
                        });

    }
}
