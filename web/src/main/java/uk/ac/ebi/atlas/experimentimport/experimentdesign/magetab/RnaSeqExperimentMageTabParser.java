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
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class RnaSeqExperimentMageTabParser extends MageTabParser<ScanNode> {

    private static final String ENA_RUN = "ENA_RUN";
    private static final String RUN_NAME = "RUN_NAME";

    @Override
    protected Set<NamedSdrfNode<ScanNode>> getAssayNodes(SDRF sdrf) {

        Set<NamedSdrfNode<ScanNode>> namedSdrfNodes = Sets.newLinkedHashSet();
        for (ScanNode scanNode : sdrf.getNodes(ScanNode.class)) {
            List<String> assayNodes = scanNode.comments.get(ENA_RUN);
            if (assayNodes == null || assayNodes.isEmpty()) {
                // We procure certain human RNA-seq experiments directly from EGA, hence their sdrf will not
                // have an ENA_RUN comment. Im such cases, we take the RUN_NAME comment column as the assay node.
                assayNodes = scanNode.comments.get(RUN_NAME);
            }
            namedSdrfNodes.add(new NamedSdrfNode<>(assayNodes.iterator().next(), scanNode));
        }
        return namedSdrfNodes;
    }

    @Override
    protected Collection<SourceNode> findUpstreamSourceNodes(NamedSdrfNode namedSdrfNode) {
        return GraphUtils.findUpstreamNodes(namedSdrfNode.getSdrfNode(), SourceNode.class);
    }

    @Override
    protected List<FactorValueAttribute> getFactorAttributes(NamedSdrfNode<ScanNode> namedSdrfNode) {
        ScanNode node = namedSdrfNode.getSdrfNode();
        Collection<uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode> assayNodes = GraphUtils.findUpstreamNodes(node, uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode.class);
        if (assayNodes.size() != 1) {
            throw new IllegalStateException("No assay corresponds to ENA run " + node.comments.get(ENA_RUN));
        }

        uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode assayNode = assayNodes.iterator().next();

        return assayNode.factorValues;
    }

    @Override
    protected void addArrays(ExperimentDesign experimentDesign, Set<NamedSdrfNode<ScanNode>> asseyNodes) {

    }
}
