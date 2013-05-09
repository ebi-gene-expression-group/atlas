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
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.CharacteristicsAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

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

    public Set<String> extractFactors() {

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