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

package uk.ac.ebi.atlas.web.controllers.page;

import com.google.common.collect.Maps;
import com.google.common.collect.SortedSetMultimap;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.web.BioEntityCardProperties;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.util.*;

public abstract class BioEntityPageController {

    protected static final String PROPERTY_TYPE_DESCRIPTION = "description";

    private SolrClient solrClient;

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
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public String showGenePage(String identifier, Model model) {

        initBioEntityPropertyService(identifier);

        model.addAttribute("entityIdentifier", identifier);

        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        return "bioEntity";
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

    abstract List<String> getPagePropertyTypes();

    abstract String getEntityNamePropertyType();

    protected void initBioEntityPropertyService(String identifier) {
        Collection<String> species = solrClient.findSpeciesForGeneId(identifier);

        if (species.size() != 1) {
            throw new ResourceNotFoundException("No unambiguous species could be determined.");
        }
        String specie = species.iterator().next();

        List<String> queryPropertyTypes = getPagePropertyTypes();

        SortedSetMultimap<String, String> propertyValuesByType = solrClient.fetchGenePageProperties(identifier, queryPropertyTypes);
        SortedSet<String> entityNames = propertyValuesByType.get(getEntityNamePropertyType());
        if (entityNames.isEmpty()) {
            entityNames.add(identifier);
        }
        bioEntityPropertyService.init(specie, propertyValuesByType, entityNames);
    }
}