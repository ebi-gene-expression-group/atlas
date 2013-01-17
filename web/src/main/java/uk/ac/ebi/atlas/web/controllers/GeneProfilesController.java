package uk.ac.ebi.atlas.web.controllers;

import uk.ac.ebi.atlas.streams.FilterParameters;
import uk.ac.ebi.atlas.web.RequestPreferences;

public class GeneProfilesController {
    protected FilterParameters.Builder filterParamenterBuilder;

    protected FilterParameters createFilterParameters(String experimentAccession, RequestPreferences preferences) {
        return filterParamenterBuilder.forExperimentAccession(experimentAccession)
                .withCutoff(preferences.getCutoff())
                .withFilterFactorValues(preferences.getFilterFactorValues())
                .withQueryFactorType(preferences.getQueryFactorType())
                .withQueryFactorValues(preferences.getQueryFactorValues())
                .withGeneQuery(preferences.getGeneQuery())
        .build();
    }
}
