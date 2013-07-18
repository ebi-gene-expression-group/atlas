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

package uk.ac.ebi.atlas.experimentloader.experimentdesign;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.SDRF;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.LabeledExtractNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;

import javax.inject.Named;
import java.util.Collection;
import java.util.Set;

//ToDo: (N) to be tested

@Named("twoColourExperimentDesignMageTabParser")
@Scope("prototype")
public class TwoColourExperimentDesignMageTabParser extends MicroarrayExperimentDesignMageTabParser {

    @Override
    protected Set<AssayNode<HybridizationNode>> getAssayNodes(SDRF sdrf) {
        Set<AssayNode<HybridizationNode>> assayNodes = Sets.newLinkedHashSet();

        for (HybridizationNode node : sdrf.getNodes(HybridizationNode.class)) {
            // Assemble assay accession for each channel separately
            for (int channelNo = 1; channelNo <= 2; channelNo++) {
                assayNodes.add(new AssayNode<HybridizationNode>(buildTwoColourExperimentAssayName(node.getNodeName(), sdrf.getLabelForChannel(channelNo)), node));
            }
        }
        return assayNodes;
    }

    @Override
    protected Collection<SourceNode> findUpstreamSourceNodes(AssayNode assayNode) {
        Collection<SourceNode> upstreamSources = null;

        for (LabeledExtractNode labeledExtractNode : GraphUtils.findUpstreamNodes(assayNode.getSdrfNode(), LabeledExtractNode.class)) {
            if (extractLabelFromAssayName(assayNode.getName()).equals(labeledExtractNode.label.getAttributeValue())) {
                upstreamSources =
                        GraphUtils.findUpstreamNodes(labeledExtractNode, SourceNode.class);
            }
        }
        return upstreamSources;
    }

    protected String buildTwoColourExperimentAssayName(String assayName, String label) {
        return Joiner.on(".").join(assayName, label);
    }

    protected String extractLabelFromAssayName(String assayName) {
        return StringUtils.substringAfter(assayName, ".");
    }

}
