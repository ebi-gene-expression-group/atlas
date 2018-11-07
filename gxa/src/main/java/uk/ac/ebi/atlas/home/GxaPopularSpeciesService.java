package uk.ac.ebi.atlas.home;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class GxaPopularSpeciesService extends PopularSpeciesService {

    @Inject
    public GxaPopularSpeciesService(GxaPopularSpeciesDao popularSpeciesDao) {
        super(popularSpeciesDao);
    }
}
