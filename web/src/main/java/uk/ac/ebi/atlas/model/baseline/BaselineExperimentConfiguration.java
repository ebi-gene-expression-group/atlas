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

package uk.ac.ebi.atlas.model.baseline;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.util.*;

public class BaselineExperimentConfiguration {

    private XMLConfiguration config;

    public BaselineExperimentConfiguration(XMLConfiguration config) {
        this.config = config;
    }

    public String getExperimentDisplayName() {
        return config.getString("landingPageDisplayName");
    }

    public String getDataProviderURL() {
        return config.getString("dataProviderURL");
    }

    public String getDataProviderDescription() {
        return config.getString("dataProviderDescription");
    }

    public Set<Factor> getDefaultFilterFactors() {

        Set<Factor> defaultFilterFactors = new HashSet<>();
        List<HierarchicalConfiguration> fields =
                config.configurationsAt("defaultFilterFactors.filterFactor");
        for (HierarchicalConfiguration sub : fields) {
            String factorType = sub.getString("type");
            String factorValue = sub.getString("value");
            defaultFilterFactors.add(new Factor(factorType, factorValue));
        }

        return defaultFilterFactors;
    }

    public String getDefaultQueryFactorType() {

        String defaultQueryFactorType = config.getString("defaultQueryFactorType");
        if (defaultQueryFactorType == null || defaultQueryFactorType.trim().length() == 0) {
            throw new IllegalStateException("No defaultQueryFactorType found in factors file.");
        }

        return defaultQueryFactorType;
    }

    public Set<String> getMenuFilterFactorTypes() {

        Set<String> results = new HashSet<>();
        List<Object> menuFilterFactorTypes = config.getList("menuFilterFactorTypes");
        for (Object o : menuFilterFactorTypes) {
            String filterFactorType = (String) o;
            if (filterFactorType.trim().length() > 0) {
                results.add(filterFactorType);
            }
        }

        return results;
    }

    public Map<String, String> getSpeciesMapping() {

        Map<String, String> mapping = new HashMap<>();
        List<HierarchicalConfiguration> fields =
                config.configurationsAt("speciesMapping.mapping");
        for (HierarchicalConfiguration sub : fields) {
            String samples = sub.getString("samples").toLowerCase();
            String genes = sub.getString("genes").toLowerCase();
            mapping.put(samples, genes);
        }

        return mapping;
    }
}
