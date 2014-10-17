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

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.SDRF;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class ProteomicsBaselineExperimentMageTabParser extends MageTabParser<AssayNode> {

    @Override
    protected Set<NamedSdrfNode<AssayNode>> getAssayNodes(SDRF sdrf) {

        Set<NamedSdrfNode<AssayNode>> namedSdrfNodes = Sets.newLinkedHashSet();
        for (AssayNode node : sdrf.getNodes(AssayNode.class)) {
            namedSdrfNodes.add(new NamedSdrfNode<>(node.getNodeName(), node));
        }
        return namedSdrfNodes;
    }

    @Override
    protected Collection<SourceNode> findUpstreamSourceNodes(NamedSdrfNode namedSdrfNode) {
        return GraphUtils.findUpstreamNodes(namedSdrfNode.getSdrfNode(), SourceNode.class);
    }

    @Override
    protected List<FactorValueAttribute> getFactorAttributes(NamedSdrfNode<AssayNode> namedSdrfNode) {
        return namedSdrfNode.getSdrfNode().factorValues;
    }

    @Override
    protected void addArrays(ExperimentDesign experimentDesign, Set<NamedSdrfNode<AssayNode>> assayNodes) {

    }
}
