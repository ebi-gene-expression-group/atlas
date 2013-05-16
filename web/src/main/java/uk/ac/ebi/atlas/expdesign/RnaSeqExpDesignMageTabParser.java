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
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;

import javax.inject.Named;
import java.util.*;

@Named
@Scope("prototype")
public class RnaSeqExpDesignMageTabParser extends ExpDesignMageTabParser {

    private static final String ENA_RUN = "ENA_RUN";

    Set<String> extractRunAccessions() {

        Set<String> runs = Sets.newHashSet();

        for (ScanNode scanNode : scanNodes) {
            runs.add(scanNode.comments.get(ENA_RUN));
        }

        return runs;
    }

    Set<String> extractFactors() {

        Set<String> factors = Sets.newHashSet();

        for (ScanNode scanNode : scanNodes) {
            Collection<AssayNode> assayNodes = GraphUtils.findUpstreamNodes(scanNode, AssayNode.class);
            if (assayNodes.size() != 1) {
                throw new IllegalStateException("No assay corresponds to ENA run " + scanNode.comments.get(ENA_RUN));
            }

            AssayNode assayNode = assayNodes.iterator().next();
            for (FactorValueAttribute factorValueAttribute : assayNode.factorValues) {
                factors.add(factorValueAttribute.type);
            }
        }

        return factors;
    }

    ScanNode getScanNodeForRunAccession(String runAccession) {

        for (ScanNode scanNode : scanNodes) {
            if (scanNode.comments.get(ENA_RUN).equals(runAccession)) {
                return scanNode;
            }
        }

        return null;
    }

    List<String> findFactorValueForScanNode(ScanNode scanNode, String factor) {

        Collection<AssayNode> assayNodes = GraphUtils.findUpstreamNodes(scanNode, AssayNode.class);
        if (assayNodes.size() != 1) {
            throw new IllegalStateException("There is no one to one mapping between scanNode and assayNode. " + scanNode);
        }

        AssayNode assayNode = assayNodes.iterator().next();
        for (FactorValueAttribute factorValueAttribute : assayNode.factorValues) {
            if (factorValueAttribute.type.equals(factor)) {
                return Arrays.asList(factorValueAttribute.values());
            }
        }

        return Collections.emptyList();
    }
}