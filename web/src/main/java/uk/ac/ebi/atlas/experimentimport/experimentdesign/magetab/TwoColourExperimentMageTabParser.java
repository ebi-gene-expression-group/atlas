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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.SDRF;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.LabeledExtractNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;

import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class TwoColourExperimentMageTabParser extends MicroarrayExperimentMageTabParser {

    @Override
    protected Set<NamedSdrfNode<HybridizationNode>> getAssayNodes(SDRF sdrf) {
        Set<NamedSdrfNode<HybridizationNode>> namedSdrfNodes = Sets.newLinkedHashSet();

        Collection<? extends HybridizationNode>  hybridizationNodes = sdrf.getNodes(HybridizationNode.class);

        if (hybridizationNodes.size() == 0) {
            //this is required because of a bug in limpopo...
            hybridizationNodes = sdrf.getNodes(uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode.class);
        }

        for (HybridizationNode node : hybridizationNodes) {
            // create separate node for each each channel
            for (int channelNo = 1; channelNo <= 2; channelNo++) {
                namedSdrfNodes.add(new NamedSdrfNode<>(buildTwoColourExperimentAssayName(node.getNodeName(), sdrf.getLabelForChannel(channelNo)), node, channelNo));
            }
        }
        return namedSdrfNodes;
    }

    @Override
    protected Collection<SourceNode> findUpstreamSourceNodes(NamedSdrfNode namedSdrfNode) {
        Collection<SourceNode> upstreamSources = null;

        for (LabeledExtractNode labeledExtractNode : GraphUtils.findUpstreamNodes(namedSdrfNode.getSdrfNode(), LabeledExtractNode.class)) {
            if (extractLabelFromAssayName(namedSdrfNode.getName()).equals(labeledExtractNode.label.getAttributeValue())) {
                upstreamSources =
                        GraphUtils.findUpstreamNodes(labeledExtractNode, SourceNode.class);
            }
        }
        return upstreamSources;
    }

    @Override
    protected List<FactorValueAttribute> getFactorAttributes(NamedSdrfNode<HybridizationNode> namedSdrfNode) {
        HybridizationNode node = namedSdrfNode.getSdrfNode();

        ImmutableList.Builder<FactorValueAttribute> builder = ImmutableList.builder();

        for (FactorValueAttribute factorValueAttribute : node.factorValues) {
            // only extract factor values for the appropriate channel
            if (factorValueAttribute.scannerChannel == namedSdrfNode.getChannel()) {
                builder.add(factorValueAttribute);
            }
        }
        return builder.build();
    }

    protected String buildTwoColourExperimentAssayName(String assayName, String label) {
        return Joiner.on(".").join(assayName, label);
    }

    protected String extractLabelFromAssayName(String assayName) {
        return StringUtils.substringAfter(assayName, ".");
    }

}
