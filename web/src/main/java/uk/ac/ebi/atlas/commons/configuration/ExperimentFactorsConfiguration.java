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

package uk.ac.ebi.atlas.commons.configuration;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.model.Factor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExperimentFactorsConfiguration {

    private XMLConfiguration config;
    private String experimentAccession;

    public ExperimentFactorsConfiguration(XMLConfiguration config, String experimentAccession) {
        this.config = config;
        this.experimentAccession = experimentAccession;
    }

    public String getDisplayNameForExperiment() {

         String displayName = config.getString("landingPageDisplayName");
        if (StringUtils.isNotBlank(displayName)) {
            return displayName;
        }
        return experimentAccession;

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
}
