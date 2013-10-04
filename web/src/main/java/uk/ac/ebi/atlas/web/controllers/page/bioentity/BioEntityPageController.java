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
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public abstract class BioEntityPageController {

    protected static final String PROPERTY_TYPE_DESCRIPTION = "description";

    private SolrQueryService solrQueryService;

    private BioEntityCardProperties bioEntityCardProperties;

    private BioEntityPropertyService bioEntityPropertyService;

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

    public String showBioentityPage(String identifier, Model model) {

        initBioentityPropertyService(identifier);

        findEnsemblIDsForMirBaseID(identifier, model);

        model.addAttribute("entityIdentifier", identifier);

        model.addAttribute("singleBioentityPage", true);

        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        return "bioEntities";
    }

    private void findEnsemblIDsForMirBaseID(String mirBaseID, Model model) {
        Set<String> ensemblIDs = solrQueryService.fetchGeneIdentifiersFromSolr(mirBaseID, "ensgene", true, "mirbase_id");
        if (ensemblIDs.size() > 0) {
            model.addAttribute("ensemblIdentifiersForMiRNA", "+" + Joiner.on("+").join(ensemblIDs));
        }
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
}