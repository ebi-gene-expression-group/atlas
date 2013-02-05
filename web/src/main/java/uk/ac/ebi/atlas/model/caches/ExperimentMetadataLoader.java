/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model.caches;

import com.google.common.base.Predicate;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Collections2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.IDF;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.CharacteristicsAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.arrayexpress2.magetab.parser.MAGETABParser;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentBuilder;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.readers.AnalysisMethodsTsvReader;
import uk.ac.ebi.atlas.model.readers.ExperimentFactorsTsvReader;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

//Be aware that this is a spring managed singleton object and uses the lookup-method injection to get a new instance of ExperimentBuilder everytime the load method is invoked
//The reason to do so is that Guava CacheBuilder, that is the one using this class, is not spring managed.
public abstract class ExperimentMetadataLoader extends CacheLoader<String, Experiment> {

    private static final Logger LOGGER = Logger.getLogger(ExperimentMetadataLoader.class);

    private static final String ENA_RUN = "ENA_RUN";

    @Value("#{configuration['experiment.magetab.idf.url.template']}")
    private String idfUrlTemplate;

    @Value("#{configuration['experiment.magetab.idf.path.template']}")
    private String idfPathTemplate;

    private AnalysisMethodsTsvReader analysisMethodsTsvReader;

    private ExperimentFactorsTsvReader experimentFactorsTsvReader;

    private ArrayExpressClient arrayExpressClient;

    @Inject
    public ExperimentMetadataLoader(AnalysisMethodsTsvReader analysisMethodsTsvReader
            , ExperimentFactorsTsvReader experimentFactorsTsvReader
            , ArrayExpressClient arrayExpressClient) {

        this.analysisMethodsTsvReader = analysisMethodsTsvReader;
        this.experimentFactorsTsvReader = experimentFactorsTsvReader;
        this.arrayExpressClient = arrayExpressClient;
    }

    @Override
    public Experiment load(String experimentAccession) throws ParseException, IOException {

        MAGETABInvestigation investigation = parseInvestigation(experimentAccession);

        Collection<ScanNode> scanNodes = investigation.SDRF.getNodes(ScanNode.class);

        String defaultQueryFactorType = parseDefaultQueryFactorType(experimentAccession);

        Set<Factor> defaultFilterFactors = parseDefaultFilterFactors(experimentAccession);

        String experimentName = fetchExperimentName(experimentAccession);

        ScanNode firstNode = scanNodes.iterator().next();

        Collection<ExperimentRun> allExperimentRuns = extractAllExperimentRunsFromSdrf(scanNodes, investigation.IDF);

        Collection<ExperimentRun> selectedExperimentRuns = Collections2.filter(allExperimentRuns, new IsExperimentRunSelected(experimentAccession));

        ExperimentBuilder experimentBuilder = createExperimentBuilder();

        return experimentBuilder.forSpecie(extractSpecie(firstNode))
                                .withDescription(experimentName)
                                .withDefaultQueryType(defaultQueryFactorType)
                                .withDefaultFilterFactors(defaultFilterFactors)
                                .withExperimentRuns(selectedExperimentRuns)
                                .create();

    }

    protected Collection<ExperimentRun> extractAllExperimentRunsFromSdrf(Collection<ScanNode> scanNodes, IDF idf) throws ParseException {

        Collection<ExperimentRun> experimentRuns = new ArrayList<>();

        for (ScanNode scanNode : scanNodes) {

            if (scanNode.comments.keySet().contains(ENA_RUN)) {
                ExperimentRun run = buildExperimentRun(scanNode, idf);
                experimentRuns.add(run);
            }
        }
        return experimentRuns;
    }

    private Set<Factor> parseDefaultFilterFactors(String experimentAccession) {
        Set<Factor> defaultFilterFactors = new HashSet<>();

        for (String[] line : experimentFactorsTsvReader.readAll(experimentAccession)) {
            if (line.length == 2) {
                defaultFilterFactors.add(new Factor(line[0], line[1]));
            }
        }
        return defaultFilterFactors;
    }

    private String parseDefaultQueryFactorType(String experimentAccession) {

        for (String[] line : experimentFactorsTsvReader.readAll(experimentAccession)) {
            if (line.length == 1) {
                return line[0];
            }
        }

        throw new IllegalStateException("No defaultQueryFactorType found in factors file.");
    }

    private String fetchExperimentName(String experimentAccession) {
        try {
            return arrayExpressClient.fetchExperimentName(experimentAccession);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "Error connecting to ArrayExpress!";
        }
    }

    private String extractSpecie(ScanNode firstScanNode) {
        SourceNode firstSourceNode = GraphUtils.findUpstreamNodes(firstScanNode, SourceNode.class).iterator().next();
        for (CharacteristicsAttribute characteristic : firstSourceNode.characteristics) {
            if (characteristic.type.equalsIgnoreCase("ORGANISM")) {
                return characteristic.getAttributeValue();
            }
        }
        return null;
    }

    //Required for testability - will be overridden to inject mock
    MAGETABInvestigation parseInvestigation(String experimentAccession) throws ParseException, IOException {

        String idfFileLocation = MessageFormat.format(idfPathTemplate, experimentAccession);
        MAGETABParser<MAGETABInvestigation> mageTabParser = new MAGETABParser<>();
        File idfFile = new File(idfFileLocation);
        if (idfFile.exists()) {
            return mageTabParser.parse(idfFile);
        } else {
            URL idfFileURL = new URL(MessageFormat.format(idfUrlTemplate, experimentAccession));
            return mageTabParser.parse(idfFileURL);
        }

    }

    ExperimentRun buildExperimentRun(ScanNode scanNode, IDF idf) {

        ExperimentRun experimentRun = new ExperimentRun(scanNode.comments.get(ENA_RUN));

        Collection<AssayNode> assayNodes = GraphUtils.findUpstreamNodes(scanNode, AssayNode.class);

        if (assayNodes.size() != 1) {
            throw new IllegalStateException("No assay corresponds to ENA run " + experimentRun.getRunAccession());
        }

        AssayNode assayNode = assayNodes.iterator().next();

        for (FactorValueAttribute factorValueAttribute : assayNode.factorValues) {

            String factorType = null;

            String factorName = factorValueAttribute.type;

            List<String> experimentalFactorNames = idf.experimentalFactorName;
            for (int i = 0; i < experimentalFactorNames.size(); i++) {
                if (experimentalFactorNames.get(i).equals(factorValueAttribute.type)) {
                    if (idf.experimentalFactorType.size() > i) {
                        factorType = idf.experimentalFactorType.get(i);
                    }
                }
            }

            Factor factor = new Factor(factorType, factorName, factorValueAttribute.getAttributeValue());
            experimentRun.addFactor(factor);
        }

        return experimentRun;
    }

    class IsExperimentRunSelected implements Predicate<ExperimentRun>{

        Set<String> selectedRunAccessions;

        public IsExperimentRunSelected(String experimentAccession) throws IOException{
            selectedRunAccessions = getSelectedRunAccessions(experimentAccession);
        }

        @Override
        public boolean apply(ExperimentRun experimentRun){
            return selectedRunAccessions.contains(experimentRun.getRunAccession());
        }

        protected final Set<String> getSelectedRunAccessions(String experimentAccession) throws IOException {

            return analysisMethodsTsvReader.readProcessedLibraries(experimentAccession);

        }

    }

    protected abstract ExperimentBuilder createExperimentBuilder();


}
