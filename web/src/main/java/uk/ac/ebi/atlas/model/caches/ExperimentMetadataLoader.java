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

import com.google.common.cache.CacheLoader;
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
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.readers.AnalysisMethodsTsvReader;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

@Named("experimentMetadataLoader")
public class ExperimentMetadataLoader extends CacheLoader<String, Experiment> {

    private static final Logger LOGGER = Logger.getLogger(ExperimentMetadataLoader.class);

    private static final String ENA_RUN = "ENA_RUN";

    private String idfUrlTemplate;
    private String idfPathTemplate;

    private AnalysisMethodsTsvReader analysisMethodsTsvReader;

    private ArrayExpressClient arrayExpressClient;

    @Inject
    public ExperimentMetadataLoader(
            @Value("#{configuration['experiment.magetab.idf.url.template']}") String idfUrlTemplate
            , @Value("#{configuration['experiment.magetab.idf.path.template']}") String idfPathTemplate
            , AnalysisMethodsTsvReader analysisMethodsTsvReader, ArrayExpressClient arrayExpressClient) {

        this.idfUrlTemplate = idfUrlTemplate;
        this.idfPathTemplate = idfPathTemplate;
        this.analysisMethodsTsvReader = analysisMethodsTsvReader;
        this.arrayExpressClient = arrayExpressClient;
    }

    @Override
    public Experiment load(String experimentAccession) throws ParseException, IOException {

        MAGETABInvestigation investigation = parseInvestigation(experimentAccession);

        Collection<ScanNode> scanNodes = investigation.SDRF.getNodes(ScanNode.class);

        // TODO: takes first experimental factor type as default
        String experimentalFactorType = investigation.IDF.experimentalFactorType.get(0).replaceAll(" ", "_").toUpperCase();

        String experimentName = fetchExperimentName(experimentAccession);

        ScanNode firstNode = scanNodes.iterator().next();

        Experiment experiment = new Experiment(experimentAccession, experimentName
                , getExperimentRunAccessions(experimentAccession), experimentalFactorType, extractSpecie(firstNode));

        experiment.addAll(extractExperimentRuns(scanNodes, investigation.IDF));

        return experiment;

    }

    private String fetchExperimentName(String experimentAccession) {
        try {
            return arrayExpressClient.fetchExperimentName(experimentAccession);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "Error connecting to ArrayExpress!";
        }
    }

    private Set<String> getExperimentRunAccessions(String experimentAccession) throws IOException {
        Map<String, String> analysisMethodsRows = analysisMethodsTsvReader.readAsMap(experimentAccession);

        String[] processedLibraries = analysisMethodsRows.get("Processed libraries").split(",");

        Set<String> experimentRunAccessions = new HashSet<>();

        for (String processedLibrary : processedLibraries) {
            experimentRunAccessions.add(processedLibrary.trim());
        }

        return experimentRunAccessions;
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

    public Collection<ExperimentRun> extractExperimentRuns(Collection<ScanNode> scanNodes, IDF idf) throws ParseException {

        Collection<ExperimentRun> experimentRuns = new ArrayList<>();

        for (ScanNode scanNode : scanNodes) {

            if (scanNode.comments.keySet().contains(ENA_RUN)) {
                ExperimentRun run = buildExperimentRun(scanNode, idf);
                experimentRuns.add(run);
            }
        }
        return experimentRuns;
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

        ExperimentRun run = new ExperimentRun(scanNode.comments.get(ENA_RUN));

        Collection<AssayNode> assayNodes = GraphUtils.findUpstreamNodes(scanNode, AssayNode.class);

        if (assayNodes.size() != 1) {
            throw new IllegalStateException("No assay corresponds to ENA run " + run.getRunAccession());
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
            if (factorType == null) {
                factorType = factorName;
            }

            run.addFactorValue(factorType, factorName, factorValueAttribute.getAttributeValue().toLowerCase());


        }

        return run;
    }


}
