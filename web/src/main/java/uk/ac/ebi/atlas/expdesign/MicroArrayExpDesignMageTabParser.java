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
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.ArrayDesignAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;

import javax.inject.Named;
import java.util.*;

@Named
@Scope("prototype")
public class MicroArrayExpDesignMageTabParser extends ExpDesignMageTabParser {

    Set<String> extractAssays() {

        Set<String> assays = Sets.newHashSet();

        for (ScanNode scanNode : getScanNodes()) {
            assays.add(scanNode.getNodeName());
        }

        return assays;
    }

    Set<String> extractFactors() {

        Set<String> factors = Sets.newHashSet();

        for (ScanNode scanNode : getScanNodes()) {
            Collection<HybridizationNode> hybridizationNodes = GraphUtils.findUpstreamNodes(scanNode, HybridizationNode.class);
            if (hybridizationNodes.size() != 1) {
                throw new IllegalStateException("There is no one to one mapping between scanNode and hybridizationNode. " + scanNode);
            }

            HybridizationNode hybridizationNode = hybridizationNodes.iterator().next();
            for (FactorValueAttribute factorValueAttribute : hybridizationNode.factorValues) {
                factors.add(factorValueAttribute.type);
            }
        }

        return factors;
    }

    ScanNode getScanNodeForAssay(String assay) {

        for (ScanNode scanNode : getScanNodes()) {
            if (scanNode.getNodeName().equals(assay)) {
                return scanNode;
            }
        }

        throw new IllegalStateException("Assay has not been found in SDRF: " + assay);
    }

    List<String> findFactorValueForScanNode(ScanNode scanNode, String factor) {

        Collection<HybridizationNode> hybridizationNodes = GraphUtils.findUpstreamNodes(scanNode, HybridizationNode.class);
        if (hybridizationNodes.size() != 1) {
            throw new IllegalStateException("There is no one to one mapping between scanNode and hybridizationNode. " + scanNode);
        }

        HybridizationNode hybridizationNode = hybridizationNodes.iterator().next();
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

    String findArrayForScanNode(ScanNode scanNode) {

        Collection<HybridizationNode> hybridizationNodes = GraphUtils.findUpstreamNodes(scanNode, HybridizationNode.class);
        if (hybridizationNodes.size() != 1) {
            throw new IllegalStateException("There is no one to one mapping between scanNode and hybridizationNode. " + scanNode);
        }

        HybridizationNode hybridizationNode = hybridizationNodes.iterator().next();
        if (hybridizationNode.arrayDesigns.size() > 1) {
            throw new IllegalStateException("Assays with multiple array designs not supported.");
        }

        ArrayDesignAttribute arrayDesignAttribute = hybridizationNode.arrayDesigns.get(0);
        return arrayDesignAttribute.getAttributeValue();
    }

}