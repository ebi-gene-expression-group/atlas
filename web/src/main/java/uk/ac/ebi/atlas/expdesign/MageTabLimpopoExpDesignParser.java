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
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.ArrayDesignAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.CharacteristicsAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;

import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class MageTabLimpopoExpDesignParser extends MageTabLimpopoUtils {

    private static final String ENA_RUN = "ENA_RUN";

    private String experimentAccession;

    private Collection<SourceNode> sourceNodes;

    private Collection<ScanNode> scanNodes;

    public MageTabLimpopoExpDesignParser forExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    public MageTabLimpopoExpDesignParser build() throws IOException, ParseException {
        checkState(experimentAccession != null, "Please invoke forExperimentAccession method to initialize the builder !");

        sourceNodes = extractSourceNodes(experimentAccession);

        scanNodes = extractScanNodes(experimentAccession);

        return this;
    }

    public String[] findFactorValueForScanNodeENARun(ScanNode scanNode, String factor) {

        Collection<AssayNode> assayNodes = GraphUtils.findUpstreamNodes(scanNode, AssayNode.class);
        if (assayNodes.size() != 1) {
            throw new IllegalStateException("There is no one to one mapping between scanNode and assayNode. " + scanNode);
        }

        AssayNode assayNode = assayNodes.iterator().next();
        for (FactorValueAttribute factorValueAttribute : assayNode.factorValues) {
            if (factorValueAttribute.type.equals(factor)) {
                return factorValueAttribute.values();
            }
        }

        return null;
    }

    public String[] findFactorValueForScanNodeAssay(ScanNode scanNode, String factor) {

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
                return factorValueAttribute.values();
            }
        }

        return null;
    }

    public String[] findCharacteristicValueForScanNode(ScanNode scanNode, String characteristic) {

        Collection<SourceNode> upstreamNodes = GraphUtils.findUpstreamNodes(scanNode, SourceNode.class);
        if (upstreamNodes.size() != 1) {
            throw new IllegalStateException("There is no one to one mapping between scanNode and sourceNode. " + scanNode);
        }

        SourceNode sourceNode = upstreamNodes.iterator().next();
        for (CharacteristicsAttribute characteristicsAttribute : sourceNode.characteristics) {
            if (characteristicsAttribute.type.equals(characteristic)) {
                return characteristicsAttribute.values();
            }
        }

        return null;
    }

    public ScanNode getScanNodeForRunAccession(String runAccession) {

        for (ScanNode scanNode : scanNodes) {
            if (scanNode.comments.get(ENA_RUN).equals(runAccession)) {
                return scanNode;
            }
        }

        return null;
    }

    public Set<String> extractRunAccessions() {

        Set<String> runs = Sets.newHashSet();

        for (ScanNode scanNode : scanNodes) {
            runs.add(scanNode.comments.get(ENA_RUN));
        }

        return runs;
    }

    public Set<String> extractAssays() {

        Set<String> assays = Sets.newHashSet();

        for (ScanNode scanNode : scanNodes) {
            assays.add(scanNode.getNodeName());
        }

        return assays;
    }

    public ScanNode getScanNodeForAssay(String assay) {

        for (ScanNode scanNode : scanNodes) {
            if (scanNode.getNodeName().equals(assay)) {
                return scanNode;
            }
        }

        return null;
    }

    public String findArrayForScanNode(ScanNode scanNode) {

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

    public Set<String> extractFactorsForENARuns() {

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

    public Set<String> extractFactorsForAssays() {

        Set<String> factors = Sets.newHashSet();

        for (ScanNode scanNode : scanNodes) {
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

    public Set<String> extractCharacteristics() {

        Set<String> characteristics = Sets.newHashSet();

        for (SourceNode sourceNode : sourceNodes) {
            for (CharacteristicsAttribute characteristicsAttribute : sourceNode.characteristics) {
                characteristics.add(characteristicsAttribute.type);
            }
        }

        return characteristics;
    }

}