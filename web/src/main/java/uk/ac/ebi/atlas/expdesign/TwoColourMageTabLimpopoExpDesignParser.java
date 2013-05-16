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
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.LabeledExtractNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.ArrayDesignAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.CharacteristicsAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;

import javax.inject.Named;
import java.util.*;

@Named
@Scope("prototype")
public class TwoColourMageTabLimpopoExpDesignParser extends MageTabLimpopoExpDesignParser {

    public List<Pair<String, Integer>> extractAssays() {
        List<Pair<String, Integer>> assayAccessions = new ArrayList<>();

        for (HybridizationNode hybridizationNode : hybridizationNodes) {
            // Assemble assay accession for each channel separately
            for (int channelNo = 1; channelNo <= 2; channelNo++) {
                assayAccessions.add(0, Pair.of(
                        hybridizationNode.getNodeName() + "." + investigation.SDRF.getLabelForChannel(channelNo),
                        channelNo));
            }
        }

        return assayAccessions;
    }

    public Set<String> extractFactors() {

        Set<String> factors = Sets.newHashSet();

        for (HybridizationNode hybridizationNode : hybridizationNodes) {
            for (FactorValueAttribute factorValueAttribute : hybridizationNode.factorValues) {
                factors.add(factorValueAttribute.type);
            }
        }

        return factors;
    }

    public HybridizationNode getHybridizationNodeForAssay(Pair<String, Integer> assay) {

        for (HybridizationNode hybridizationNode : hybridizationNodes) {
            if (assay.getKey().startsWith(hybridizationNode.getNodeName())) {
                return hybridizationNode;
            }
        }

        return null;
    }

    public String findFactorValueForAssay(Pair<String, Integer> assay, String factor) {

        HybridizationNode hybridizationNode = getHybridizationNodeForAssay(assay);
        if (hybridizationNode.arrayDesigns.size() > 1) {
            throw new IllegalStateException("Assays with multiple array designs not supported.");
        }

        for (FactorValueAttribute factorValueAttribute : hybridizationNode.factorValues) {
            if (factorValueAttribute.type.equals(factor) && factorValueAttribute.scannerChannel == assay.getValue()) {
                return factorValueAttribute.getAttributeValue();
            }
        }

        return null;
    }

    public String findArrayForHybridizationNode(HybridizationNode hybridizationNode) {

        if (hybridizationNode.arrayDesigns.size() > 1) {
            throw new IllegalStateException("Assays with multiple array designs not supported.");
        }

        ArrayDesignAttribute arrayDesignAttribute = hybridizationNode.arrayDesigns.get(0);
        return arrayDesignAttribute.getAttributeValue();
    }

    public List<String> findCharacteristicValueForAssay(Pair<String, Integer> assay, String characteristic) {

        HybridizationNode hybridizationNode = getHybridizationNodeForAssay(assay);
        int channelNo = assay.getValue();

        // Make sure that the upstream samples correspond to the same labelled extract as the this (single-channel) assayAcc
        Collection<SourceNode> upstreamSources = null;
        for (LabeledExtractNode labeledExtractNode : GraphUtils.findUpstreamNodes(hybridizationNode, LabeledExtractNode.class)) {
            if (investigation.SDRF.getLabelForChannel(channelNo).equals(labeledExtractNode.label.getAttributeValue())) {
                upstreamSources =
                        GraphUtils.findUpstreamNodes(labeledExtractNode, SourceNode.class);
            }
        }
        if (upstreamSources == null) {
            throw new IllegalStateException("Unable to find the labeled extract: " + investigation.SDRF.getLabelForChannel(channelNo) + " for the assay: " + assay.getKey());
        }

        SourceNode sourceNode = upstreamSources.iterator().next();
        for (CharacteristicsAttribute characteristicsAttribute : sourceNode.characteristics) {
            if (characteristicsAttribute.type.equals(characteristic)) {
                return Arrays.asList(characteristicsAttribute.values());
            }
        }

        return Collections.emptyList();
    }

}