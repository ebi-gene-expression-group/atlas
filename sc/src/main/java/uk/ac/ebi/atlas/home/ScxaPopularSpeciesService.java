package uk.ac.ebi.atlas.home;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ScxaPopularSpeciesService extends PopularSpeciesService {

    @Inject
    public ScxaPopularSpeciesService(ScxaPopularSpeciesDao popularSpeciesDao) {
        super(popularSpeciesDao);
    }
}
