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

package uk.ac.ebi.atlas.expdesign;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.ArrayDesignAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;

import javax.inject.Named;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

//ToDo: (N) to be removed

@Named
@Scope("prototype")
public class MicroArrayExpDesignMageTabParser extends ExpDesignMageTabParser {

    Set<String> extractAssays() {

        Set<String> assays = Sets.newHashSet();

        for (HybridizationNode hybridizationNode : getHybridizationNodes()) {
            assays.add(hybridizationNode.getNodeName());
        }

        return assays;
    }

    Set<String> extractFactors() {

        Set<String> factors = Sets.newHashSet();

        for (HybridizationNode hybridizationNode : getHybridizationNodes()) {
            for (FactorValueAttribute factorValueAttribute : hybridizationNode.factorValues) {
                factors.add(factorValueAttribute.type);
            }
        }

        return factors;
    }

    HybridizationNode getHybridizationNodeForAssay(String assay) {

        for (HybridizationNode hybridizationNode : getHybridizationNodes()) {
            if (hybridizationNode.getNodeName().equals(assay)) {
                return hybridizationNode;
            }
        }

        throw new IllegalStateException("Assay has not been found in SDRF: " + assay);
    }

    List<String> findFactorValueForHybridizationNode(HybridizationNode hybridizationNode, String factor) {

        if (hybridizationNode.arrayDesigns.size() > 1) {
            throw new IllegalStateException("Assays with multiple array designs not supported.");
        }

        for (FactorValueAttribute factorValueAttribute : hybridizationNode.factorValues) {
            if (factorValueAttribute.type.equals(factor)) {
                return Arrays.asList(factorValueAttribute.values());
            }
        }

        return Collections.emptyList();
    }

    String findArrayForHybridizationNode(HybridizationNode hybridizationNode) {

        if (hybridizationNode.arrayDesigns.size() > 1) {
            throw new IllegalStateException("Assays with multiple array designs not supported.");
        }

        ArrayDesignAttribute arrayDesignAttribute = hybridizationNode.arrayDesigns.get(0);
        return arrayDesignAttribute.getAttributeValue();
    }

}