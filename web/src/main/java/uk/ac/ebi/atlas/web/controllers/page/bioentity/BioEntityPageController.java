/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.web.controllers.page.bioentity;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.SortedSetMultimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.commands.BaselineProfilesHeatMap;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineGeneQueryRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public abstract class BioEntityPageController {

    protected static final String PROPERTY_TYPE_DESCRIPTION = "description";

    protected SolrQueryService solrQueryService;

    private BioEntityCardProperties bioEntityCardProperties;

    private BioEntityPropertyService bioEntityPropertyService;

    private ApplicationProperties applicationProperties;

    private BaselineRequestContext requestContext;
    private BaselineProfilesHeatMap baselineProfilesHeatMap;
    private BaselineRequestContextBuilder baselineRequestContextBuilder;
    private FilterFactorsConverter filterFactorsConverter;

    private ExperimentTrader experimentTrader;

    @Inject
    private void setExperimentTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @Inject
    public void setBaselineRequestContextBuilder(BaselineRequestContextBuilder baselineRequestContextBuilder) {
        this.baselineRequestContextBuilder = baselineRequestContextBuilder;
    }

    @Inject
    public void setFilterFactorsConverter(FilterFactorsConverter filterFactorsConverter) {
        this.filterFactorsConverter = filterFactorsConverter;
    }

    @Inject
    public void setRequestContext(BaselineRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Inject
    public void setBaselineProfilesHeatMap(BaselineProfilesHeatMap baselineProfilesHeatMap) {
        this.baselineProfilesHeatMap = baselineProfilesHeatMap;
    }

    @Inject
    public void setBioEntityCardProperties(BioEntityCardProperties bioEntityCardProperties) {
        this.bioEntityCardProperties = bioEntityCardProperties;
    }

    @Inject
    public void setBioEntityPropertyService(BioEntityPropertyService bioEntityPropertyService) {
        this.bioEntityPropertyService = bioEntityPropertyService;
    }

    @Inject
    public void setSolrQueryService(SolrQueryService solrQueryService) {
        this.solrQueryService = solrQueryService;
    }

    @Inject
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public String showBioentityPage(String identifier, Model model) {

        initBioentityPropertyService(identifier);

        model.addAttribute("entityIdentifier", identifier);

        //if it is false the bioentity property panel will not be shown and the
        //differential heatmap will visualize an extra column for design element (not required when the heatmap displays a single bioentity)
        model.addAttribute("singleBioentityPage", true);

        //if all geneIds and geneNames in the BioentityPage are the same we don't want to display in the heatmap the columns Genes and Organism
        model.addAttribute("bioentitySameIdentifier", true);

        String experimentAccession = hasBaselineExperimentForSpecies(identifier);
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, null);

        //to check if the widget contains the identifier or not and inform properly in the results gene pages
        model.addAttribute("hasBaselineExperimentForSpecies", StringUtils.isNotEmpty(experimentAccession));

        try {

            model.addAttribute("hasGeneProfiles", hasGeneProfilesExperiment(experiment, identifier));

        } catch (GenesNotFoundException e) {
             e.printStackTrace();
        }

        //bioentity properties panel data
        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        return "bioEntities";
    }

    protected Map<String, String> buildPropertyNamesByTypeMap() {
        LinkedHashMap<String, String> result = Maps.newLinkedHashMap();
        for (String propertyType : getPagePropertyTypes()) {
            if (isDisplayedInPropertyList(propertyType)) {
                result.put(propertyType, bioEntityCardProperties.getPropertyName(propertyType));
            }
        }
        return result;
    }

    protected abstract boolean isDisplayedInPropertyList(String propertyType);

    abstract String[] getPagePropertyTypes();

    abstract String getBioentityPropertyName();

    protected void initBioentityPropertyService(String identifier) {
        String species = solrQueryService.findSpeciesForBioentityId(identifier);

        SortedSetMultimap<String, String> propertyValuesByType = solrQueryService.fetchGenePageProperties(identifier, getPagePropertyTypes());
        SortedSet<String> entityNames = propertyValuesByType.get(getBioentityPropertyName());
        if (entityNames.isEmpty()) {
            entityNames.add(identifier);
        }
        bioEntityPropertyService.init(species, propertyValuesByType, entityNames, identifier);
    }

    protected String hasBaselineExperimentForSpecies(String identifier){
        String species = solrQueryService.findSpeciesForBioentityId(identifier);
        String experimentAccession = applicationProperties.getBaselineWidgetExperimentAccessionBySpecies(species);

        return experimentAccession;
    }

    //TODO: CLEAN THIS MESS
    protected boolean hasGeneProfilesExperiment(BaselineExperiment experiment, String identifier) throws GenesNotFoundException {

        BaselineGeneQueryRequestPreferences preferences = new BaselineGeneQueryRequestPreferences();
        preferences.setEntityIdentifier(identifier);
        preferences.setGeneQuery(identifier);

        Set<String> ensemblIDs = solrQueryService.fetchGeneIdentifiersFromSolr(identifier, "ensgene", true, "mirbase_id");
        if(ensemblIDs.size() > 0) {
            preferences.setEnsemblIdentifiersForMiRNA("+" + Joiner.on("+").join(ensemblIDs));
        }

        initializeContext(preferences, experiment);

        BaselineProfilesList baselineProfiles = baselineProfilesHeatMap.fetch(requestContext);

        return (baselineProfiles.size() > 0) ? true : false;
    }

    //TODO: CLEAN THIS MESS
    private void initializeContext(BaselineGeneQueryRequestPreferences preferences, BaselineExperiment experiment) {

        setPreferenceDefaults(preferences, experiment);

        requestContext = buildRequestContext(experiment, preferences);
    }

    //TODO: CLEAN THIS MESS copied from baselineExperimentController
    private void setPreferenceDefaults(BaselineGeneQueryRequestPreferences preferences, BaselineExperiment baselineExperiment) {

        if (org.apache.commons.lang3.StringUtils.isBlank(preferences.getQueryFactorType())) {
            preferences.setQueryFactorType(baselineExperiment.getExperimentalFactors().getDefaultQueryFactorType());
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(preferences.getSerializedFilterFactors())) {
            preferences.setSerializedFilterFactors(filterFactorsConverter.serialize(baselineExperiment.getExperimentalFactors().getDefaultFilterFactors()));
        }

        if(CollectionUtils.isNotEmpty(preferences.getQueryFactorValues()) && CollectionUtils.isNotEmpty(baselineExperiment.getAssayGroups().getAssayGroupIds())) {
            if (preferences.getQueryFactorValues().size() == baselineExperiment.getAssayGroups().getAssayGroupIds().size()) {
                preferences.setSpecific(false);
            }
        }
    }

    //TODO: CLEAN THIS MESS copied from baselineExperimentController
    private BaselineRequestContext buildRequestContext(BaselineExperiment experiment, BaselineGeneQueryRequestPreferences preferences) {

        return baselineRequestContextBuilder.forExperiment(experiment)
                .withGeneQueryRequestPreferences(preferences)
                .geneQueryRequestBuild();
    }

}