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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.velocity.util.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.IDF;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
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

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class MageTabLimpopoParser implements uk.ac.ebi.atlas.model.cache.baseline.magetab.MageTabParser, MageTabParserBuilder {

    private static final String SPECIES_FACTOR_TYPE = "ORGANISM";

    private Set<String> processedRunAccessions;

    private static final String ENA_RUN = "ENA_RUN";

    private Set<String> requiredFactorTypes;

    private Map<String, ExperimentRun> processedExperimentRuns;

    private Map<String, String> factorNamesByType;

    private Collection<ScanNode> scanNodes;

    private String experimentAccession;

    private MAGETABInvestigation investigation;

    private MageTabLimpopoUtils mageTabLimpopoUtils;

    @Inject
    public void setMageTabLimpopoUtils(MageTabLimpopoUtils mageTabLimpopoUtils) {
        this.mageTabLimpopoUtils = mageTabLimpopoUtils;
    }

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
        checkState(mageTabLimpopoUtils != null, "MageTabLimpopoUtils not injected !");
        checkState(CollectionUtils.isNotEmpty(requiredFactorTypes), "Please invoke withRequiredFactorTypes method to initialize the builder !");

        investigation = mageTabLimpopoUtils.parseInvestigation(experimentAccession);

        scanNodes = investigation.SDRF.getNodes(ScanNode.class);

        factorNamesByType = transformFactorNames(extractPreservedFactorTypes());

        processedExperimentRuns = extractProcessedExperimentRuns();
        return this;
    }

    protected Map<String, String> transformFactorNames(Map<String, String> map) {
        Map<String, String> result = new HashMap<>();
        for (String key : map.keySet()) {
            result.put(key, prettifyFactorType(map.get(key)));
        }
        return result;
    }

    protected Map<String, ExperimentRun> extractProcessedExperimentRuns() {

        Collection<ExperimentRun> allExperimentRuns = extractAllExperimentRunsFromSdrf(scanNodes, investigation.IDF);

        Map<String, ExperimentRun> extractedExperimentRuns = Maps.newHashMap();

        for (ExperimentRun experimentRun : allExperimentRuns) {
            if (processedRunAccessions.contains(experimentRun.getAccession())) {
                extractedExperimentRuns.put(experimentRun.getAccession(), experimentRun);
            }
        }
        return extractedExperimentRuns;
    }


    @Override
    public Set<String> extractSpecies() {
        if (requiredFactorTypes.contains(SPECIES_FACTOR_TYPE)) {

            Set<String> species = Sets.newHashSet();
            for (ExperimentRun experimentRun : processedExperimentRuns.values()) {
                String organism = experimentRun.getFactorByType(SPECIES_FACTOR_TYPE).getValue();
                if (organism != null) {
                    species.add(organism);
                }
            }
            return species;
        }

        return mageTabLimpopoUtils.extractSpeciesFromSDRF(investigation);

    }

    @Override
    public Map<String, String> getFactorNamesByType() {
        return Collections.unmodifiableMap(factorNamesByType);
    }

    Map<String, String> extractFactorNames() {
        IDF idf = investigation.IDF;
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

    Map<String, String> extractPreservedFactorTypes() {
        IDF idf = investigation.IDF;
        Map<String, String> preservedByType = Maps.newHashMap();
        for (int i = 0; i < idf.experimentalFactorType.size(); i++) {
            String factorType = idf.experimentalFactorType.get(i);
            String normalizedFactorType = Factor.normalize(factorType);
            if (requiredFactorTypes.contains(normalizedFactorType)) {
                preservedByType.put(normalizedFactorType, factorType);
            }
        }
        return preservedByType;
    }

    protected String prettifyFactorType(String factorType) {
        StringBuilder result = new StringBuilder();
        String[] split = factorType.replaceAll("_", " ").split(" ");
        for (String token : split) {
            int nbUpperCase = countUpperCaseLetters(token);
            if (nbUpperCase > 1) {
                result.append(token);
            } else {
                token = token.toLowerCase();
                token = StringUtils.capitalizeFirstLetter(token);
                result.append(token);
            }
            result.append(" ");
        }

        return result.toString().trim();
    }

    protected int countUpperCaseLetters(String token) {
        int nbUpperCase = 0;
        for (int i = 0; i < token.length(); i++) {
            if (Character.isUpperCase(token.charAt(i))) {
                nbUpperCase++;
            }
        }
        return nbUpperCase;
    }

    Collection<ExperimentRun> extractAllExperimentRunsFromSdrf(Collection<ScanNode> scanNodes, IDF idf) {

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
        ExperimentRun experimentRun = new ExperimentRun(scanNode.comments.get(ENA_RUN).iterator().next());

        Collection<AssayNode> assayNodes = GraphUtils.findUpstreamNodes(scanNode, AssayNode.class);

        if (assayNodes.size() != 1) {
            throw new IllegalStateException("No assay corresponds to ENA run " + experimentRun.getAccession());
        }

        AssayNode assayNode = assayNodes.iterator().next();

        BiMap<String, String> factorTypesByName = HashBiMap.create(extractFactorNames()).inverse();

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
