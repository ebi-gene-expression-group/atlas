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

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExperimentalFactorsBuilder {

    private String defaultQueryType;

    private Set<Factor> defaultFilterFactors;

    private Map<String, String> factorNamesByType = new HashMap<>();

    private List<FactorGroup> orderedFactorGroups;

    private Map<String, FactorGroup> orderedFactorGroupsByAssayGroupId;

    private Set<String> menuFilterFactorTypes;

    private List<String> factorTypes;

    public ExperimentalFactorsBuilder withDefaultQueryType(String defaultQueryType) {
        this.defaultQueryType = defaultQueryType;
        return this;
    }

    public ExperimentalFactorsBuilder withDefaultFilterFactors(Set<Factor> defaultFilterFactors) {
        this.defaultFilterFactors = defaultFilterFactors;
        return this;
    }

    public ExperimentalFactorsBuilder withFactorNamesByType(Map<String, String> factorNamesByType) {
        this.factorNamesByType = factorNamesByType;
        return this;
    }

    public ExperimentalFactorsBuilder withOrderedFactorGroups(List<FactorGroup> orderedFactorGroups) {
        this.orderedFactorGroups = orderedFactorGroups;
        return this;
    }

    public ExperimentalFactorsBuilder withOrderedFactorGroupsByAssayGroupId(Map<String, FactorGroup> orderedFactorGroupsByAssayGroup) {
        this.orderedFactorGroupsByAssayGroupId = orderedFactorGroupsByAssayGroup;
        return this;
    }

    public ExperimentalFactorsBuilder withMenuFilterFactorTypes(Set<String> menuFilterFactorTypes) {

        this.menuFilterFactorTypes = menuFilterFactorTypes;
        return this;
    }

    public ExperimentalFactorsBuilder withFactorTypes(List<String> factorTypes) {
        this.factorTypes = factorTypes;
        return this;
    }

    public ExperimentalFactors create() {
        checkState(menuFilterFactorTypes != null, "Please provide a set of menu filter factor types");
        checkState(StringUtils.isNotBlank(defaultQueryType), "Please provide a non blank defaultQueryType");
        checkState(defaultFilterFactors != null, "Please provide a set of filter factors");

        SortedSetMultimap<String, Factor> factorsByType = buildFactorsByType();
        SortedSetMultimap<Factor, Factor> coOccurringFactors = buildCoOccurringFactors();

        return new ExperimentalFactors(factorsByType, factorNamesByType, orderedFactorGroups,
                coOccurringFactors, menuFilterFactorTypes, factorTypes, orderedFactorGroupsByAssayGroupId, defaultQueryType, defaultFilterFactors);
    }

    public ExperimentalFactors createFromXML() {
        checkState(menuFilterFactorTypes != null, "Please provide a set of menu filter factor types");
        checkState(StringUtils.isNotBlank(defaultQueryType), "Please provide a non blank defaultQueryType");
        checkState(defaultFilterFactors != null, "Please provide a set of filter factors");

        LinkedHashMultimap<String, Factor> xmlFactorsByType = buildXmlFactorsByType();
        LinkedHashMultimap<Factor, Factor> coOccurringFactors = buildXmlCoOccurringFactors();

        return new ExperimentalFactors(xmlFactorsByType, factorNamesByType, orderedFactorGroups,
                coOccurringFactors, menuFilterFactorTypes, factorTypes, orderedFactorGroupsByAssayGroupId, defaultQueryType, defaultFilterFactors);
    }

    SortedSetMultimap<String, Factor> buildFactorsByType() {

        SortedSetMultimap<String, Factor> factorsByType = TreeMultimap.create();

        for (FactorGroup factorGroup : orderedFactorGroups) {

            for (Factor factor : factorGroup) {

                factorsByType.put(factor.getType(), factor);

            }
        }
        return factorsByType;
    }

    LinkedHashMultimap<String, Factor> buildXmlFactorsByType() {
        LinkedHashMultimap<String, Factor> xmlFactorsByType = LinkedHashMultimap.create();

        for(FactorGroup factorGroup : orderedFactorGroups) {
            for (Factor factor : factorGroup) {
                xmlFactorsByType.put(factor.getType(), factor);
            }
        }

        return xmlFactorsByType;

    }


    SortedSetMultimap<Factor, Factor> buildCoOccurringFactors() {

        SortedSetMultimap<Factor, Factor> coOccurringFactors = TreeMultimap.create();

        for (FactorGroup factorGroup : orderedFactorGroups) {

            for (Factor factor : factorGroup) {

                for (Factor value : factorGroup) {
                    if (!value.equals(factor)) {
                        coOccurringFactors.put(factor, value);
                    }
                }
            }
        }
        return coOccurringFactors;
    }

    LinkedHashMultimap<Factor, Factor> buildXmlCoOccurringFactors() {

        LinkedHashMultimap<Factor, Factor> xmlCoOccurringFactors = LinkedHashMultimap.create();

        for (FactorGroup factorGroup : orderedFactorGroups) {

            for (Factor factor : factorGroup) {

                for (Factor value : factorGroup) {
                    if (!value.equals(factor)) {
                        xmlCoOccurringFactors.put(factor, value);
                    }
                }
            }
        }
        return xmlCoOccurringFactors;
    }

}
