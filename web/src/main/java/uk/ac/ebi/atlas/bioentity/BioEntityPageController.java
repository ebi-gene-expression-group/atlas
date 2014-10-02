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

package uk.ac.ebi.atlas.bioentity;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineProfilesHeatMap;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptionsWidgetQuery;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentAssayGroup;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentAssayGroupSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfileSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineTissueExperimentSearchResult;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsList;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsSearchService;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.util.*;

public abstract class BioEntityPageController {

    protected static final String PROPERTY_TYPE_DESCRIPTION = "description";

    protected SolrQueryService solrQueryService;

    protected SpeciesLookupService speciesLookupService;

    private BioEntityCardProperties bioEntityCardProperties;

    private BioEntityPropertyService bioEntityPropertyService;

    private ApplicationProperties applicationProperties;

    private BaselineProfilesHeatMap baselineProfilesHeatMap;

    private ExperimentTrader experimentTrader;

    private DiffAnalyticsSearchService diffAnalyticsSearchService;

    private BaselineExperimentAssayGroupSearchService baselineExperimentAssayGroupSearchService;

    private BaselineExperimentProfileSearchService baselineExperimentProfileSearchService;

    @Inject
    public void setBaselineExperimentProfileSearchService(BaselineExperimentProfileSearchService baselineExperimentProfileSearchService) {
        this.baselineExperimentProfileSearchService = baselineExperimentProfileSearchService;
    }

    @Inject
    public void setBaselineExperimentAssayGroupSearchService(BaselineExperimentAssayGroupSearchService baselineExperimentAssayGroupSearchService) {
        this.baselineExperimentAssayGroupSearchService = baselineExperimentAssayGroupSearchService;
    }

    @Inject
    public void setSpeciesLookupService(SpeciesLookupService speciesLookupService) {
        this.speciesLookupService = speciesLookupService;
    }

    @Inject
    public void setExperimentTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
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

    @Inject
    void setDifferentialBioentityExpressionBuilder(DiffAnalyticsSearchService diffAnalyticsSearchService) {
        this.diffAnalyticsSearchService = diffAnalyticsSearchService;
    }

    void loadDifferentialResults(String identifier, Model model) {
        loadDifferentialResults(Sets.newHashSet(identifier), model);
    }

    void loadDifferentialResults(Collection<String> geneIdentifiers, Model model) {
        DiffAnalyticsList diffAnalyticsList =
                diffAnalyticsSearchService.fetchTopAnySpecies(geneIdentifiers);

        model.addAttribute("bioentities", diffAnalyticsList);

        // setting FDR as cutoff
        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

        model.addAttribute("preferences", requestPreferences);
    }


    public String showBioentityPage(String identifier, Model model, boolean singleGeneDiffHeatmap) {

        initBioentityPropertyService(identifier);

        model.addAttribute("searchTerm", identifier);

        model.addAttribute("entityIdentifier", identifier);

        model.addAttribute("exactMatch", true);

        model.addAttribute("showBioentityPropertiesPane", true);

        //toggle display of multi-gene columns (gene name, and organism) in the differential heatmap
        model.addAttribute("singleGeneDiffHeatmap", singleGeneDiffHeatmap);

        //bioentity properties panel data
        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        return "bioEntities";
    }

    void addWidgetHasBaselineProfiles(String identifier, Model model) {
        String species = fetchSpecies(identifier);
        String referenceExperimentAccession = applicationProperties.getBaselineWidgetExperimentAccessionBySpecies(species);

        try {
            //to check if the widget contains the identifier or not and inform properly in the results gene pages
            if (referenceExperimentAccession != null) {

                String ensemblIdentifiersForMiRNA = (String) model.asMap().get("ensemblIdentifiersForMiRNA");
                ImmutableSet<String> identifiers = (ensemblIdentifiersForMiRNA == null) ? ImmutableSet.of(identifier) : ImmutableSet.<String>builder().add(identifier).addAll(Splitter.on("+").split(ensemblIdentifiersForMiRNA)).build();

                model.addAttribute("widgetHasBaselineProfiles", widgetHasBaselineProfiles(referenceExperimentAccession, species, identifiers));
            } else {
                model.addAttribute("widgetHasBaselineProfiles", false);
            }

        } catch (GenesNotFoundException e) {
            throw new ResourceNotFoundException("No gene profiles with identifier " + identifier);
        }
    }

    void addBaselineResults(String identifier, Model model, String species) {
        BaselineTissueExperimentSearchResult tissueResults = baselineExperimentProfileSearchService.query(identifier, species, true);

        if (tissueResults.isEmpty()) {
            addBaselineCounts(identifier, model);
        } else {
            addWidget(model, species);
        }
    }

    void addWidget(Model model, String species) {
        model.addAttribute("widgetHasBaselineProfiles", true);
        model.addAttribute("species", species);
    }

    void addBaselineCounts(String identifier, Model model) {
        String specie = "";
        Set<BaselineExperimentAssayGroup> baselineExperimentAssayGroups = baselineExperimentAssayGroupSearchService.query(identifier, null, specie, true);
        model.addAttribute("baselineCounts", baselineExperimentAssayGroups);
    }


    String fetchSpecies(String identifier) {
        return speciesLookupService.fetchSpeciesForBioentityId(identifier);
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
        String species = speciesLookupService.fetchSpeciesForBioentityId(identifier);

        SortedSetMultimap<String, String> propertyValuesByType = solrQueryService.fetchGenePageProperties(identifier, getPagePropertyTypes());
        SortedSet<String> entityNames = propertyValuesByType.get(getBioentityPropertyName());
        if (entityNames.isEmpty()) {
            entityNames.add(identifier);
        }
        bioEntityPropertyService.init(species, propertyValuesByType, entityNames, identifier);
    }

    protected boolean widgetHasBaselineProfiles(String experimentAccession, String species, Set<String> identifiers) throws GenesNotFoundException {
        BaselineExperiment experiment;
        try {
            experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, null);
        } catch (ResourceNotFoundException e) {
            // if we don't have the baseline reference experiment, then no gene profiles
            return false;
        }

        // convert enstranscript, ensgene, and geneset ID to individual gene ids
        Set<String> geneIds = solrQueryService.fetchGeneIds(Joiner.on(" ").join(identifiers), true, species);

        BaselineProfileStreamOptionsWidgetQuery options = new BaselineProfileStreamOptionsWidgetQuery(experiment, species, geneIds);

        BaselineProfilesList baselineProfiles = baselineProfilesHeatMap.fetch(options);

        return (baselineProfiles.size() > 0);
    }

}