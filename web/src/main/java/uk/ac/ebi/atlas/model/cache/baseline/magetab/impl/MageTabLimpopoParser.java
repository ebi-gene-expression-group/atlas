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

package uk.ac.ebi.atlas.model.cache.baseline.magetab.impl;

import com.google.common.collect.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.IDF;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;
import uk.ac.ebi.atlas.model.baseline.ExperimentRun;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.cache.baseline.magetab.MageTabParser;
import uk.ac.ebi.atlas.model.cache.baseline.magetab.MageTabParserBuilder;

import javax.inject.Named;
import java.io.IOException;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class MageTabLimpopoParser extends MageTabLimpopoUtils implements uk.ac.ebi.atlas.model.cache.baseline.magetab.MageTabParser, MageTabParserBuilder {

    private static final String SPECIES_FACTOR_TYPE = "ORGANISM";

    private Set<String> processedRunAccessions;

    private static final String ENA_RUN = "ENA_RUN";

    private Set<String> requiredFactorTypes;

    private Collection<ExperimentRun> processedExperimentRuns;

    private Map<String, String> factorNamesByType;

    private Collection<ScanNode> scanNodes;

    private String experimentAccession;

    @Override
    public MageTabLimpopoParser forExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    @Override
    public MageTabParserBuilder withRequiredFactorTypes(Set<String> requiredFactorTypes) {
        this.requiredFactorTypes = requiredFactorTypes;
        return this;
    }

    @Override
    public MageTabParserBuilder withProcessedRunAccessions(Set<String> processedRunAccessions) {
        this.processedRunAccessions = processedRunAccessions;
        return this;
    }

    @Override
    public MageTabParser build() throws IOException, ParseException {
        checkState(experimentAccession != null, "Please invoke forExperimentAccession method to initialize the builder !");
        checkState(CollectionUtils.isNotEmpty(requiredFactorTypes), "Please invoke withRequiredFactorTypes method to initialize the builder !");

        scanNodes = extractScanNodes(experimentAccession);

        factorNamesByType = extractFactorNames();

        processedExperimentRuns = extractProcessedExperimentRuns();
        return this;
    }

    @Override
    public Collection<ExperimentRun> getProcessedExperimentRuns() {
        return Collections.unmodifiableCollection(processedExperimentRuns);
    }

    protected Collection<ExperimentRun> extractProcessedExperimentRuns() throws IOException, ParseException {

        Collection<ExperimentRun> allExperimentRuns = extractAllExperimentRunsFromSdrf(scanNodes, getInvestigation().IDF);

        Collection<ExperimentRun> processedExperimentRuns = Lists.newArrayList();

        for (ExperimentRun experimentRun : allExperimentRuns) {
            if (processedRunAccessions.contains(experimentRun.getAccession())) {
                processedExperimentRuns.add(experimentRun);
            }
        }
        return processedExperimentRuns;
    }


    @Override
    public Set<String> extractSpecies() {
        if (requiredFactorTypes.contains(SPECIES_FACTOR_TYPE)) {

            Set<String> species = Sets.newHashSet();
            for (ExperimentRun experimentRun : processedExperimentRuns) {
                String organism = experimentRun.getFactorByType(SPECIES_FACTOR_TYPE).getValue();
                if (organism != null) {
                    species.add(organism);
                }
            }
            return species;
        } else {
            return extractSpeciesFromSDRF(scanNodes);
        }

    }

    @Override
    public Map<String, String> getFactorNamesByType() {
        return Collections.unmodifiableMap(factorNamesByType);
    }

    Map<String, String> extractFactorNames() {
        IDF idf = getInvestigation().IDF;
        Map<String, String> namesByType = Maps.newHashMap();
        for (int i = 0; i < idf.experimentalFactorType.size(); i++) {
            String factorType = idf.experimentalFactorType.get(i);
            String normalizedFactorType = Factor.normalize(factorType);
            if (requiredFactorTypes.contains(normalizedFactorType)) {
                namesByType.put(normalizedFactorType, idf.experimentalFactorName.get(i));
            }
        }
        return namesByType;
    }

    Collection<ExperimentRun> extractAllExperimentRunsFromSdrf(Collection<ScanNode> scanNodes, IDF idf) throws ParseException {

        Collection<ExperimentRun> experimentRuns = new ArrayList<>();

        for (ScanNode scanNode : scanNodes) {

            if (scanNode.comments.keySet().contains(ENA_RUN)) {
                ExperimentRun run = buildExperimentRun(scanNode);
                experimentRuns.add(run);
            }
        }
        return experimentRuns;
    }

    ExperimentRun buildExperimentRun(ScanNode scanNode) {
        checkState(factorNamesByType != null, "Please invoke the extractFactorNames method first");
        ExperimentRun experimentRun = new ExperimentRun(scanNode.comments.get(ENA_RUN));

        Collection<AssayNode> assayNodes = GraphUtils.findUpstreamNodes(scanNode, AssayNode.class);

        if (assayNodes.size() != 1) {
            throw new IllegalStateException("No assay corresponds to ENA run " + experimentRun.getAccession());
        }

        AssayNode assayNode = assayNodes.iterator().next();

        BiMap<String, String> factorTypesByName = HashBiMap.create(factorNamesByType).inverse();

        for (FactorValueAttribute factorValueAttribute : assayNode.factorValues) {

            String factorName = factorValueAttribute.type;

            if (factorTypesByName.containsKey(factorName)) {

                Factor factor = new Factor(factorTypesByName.get(factorName), factorValueAttribute.getAttributeValue());

                experimentRun.addFactor(factor);

            }
        }

        return experimentRun;
    }


}
