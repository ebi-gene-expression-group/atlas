package uk.ac.ebi.atlas.model.caches.magetab.impl;

import com.google.common.collect.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
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
import uk.ac.ebi.atlas.commands.RankGeneProfilesCommand;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.caches.magetab.MageTabLoader;
import uk.ac.ebi.atlas.model.caches.magetab.MageTabLoaderBuilder;

import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class MageTabParser implements MageTabLoader, MageTabLoaderBuilder {

    private static final Logger LOGGER = Logger.getLogger(RankGeneProfilesCommand.class);

    private static final String SPECIES_FACTOR_TYPE = "ORGANISM";

    @Value("#{configuration['experiment.magetab.idf.url.template']}")
    private String idfUrlTemplate;

    @Value("#{configuration['experiment.magetab.idf.path.template']}")
    private String idfPathTemplate;

    private Set<String> processedRunAccessions;

    private String experimentAccession;

    private static final String ENA_RUN = "ENA_RUN";

    private Collection<ScanNode> scanNodes;

    private Set<String> requiredFactorTypes;

    private MAGETABInvestigation investigation;

    private Collection<ExperimentRun> processedExperimentRuns;

    private Map<String, String> factorNamesByType;



    public MageTabParser forExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    @Override
    public MageTabLoaderBuilder withRequiredFactorTypes(Set<String> requiredFactorTypes) {
        this.requiredFactorTypes = requiredFactorTypes;
        return this;
    }

    @Override
    public MageTabLoaderBuilder withProcessedRunAccessions(Set<String> processedRunAccessions) {
        this.processedRunAccessions = processedRunAccessions;
        return this;
    }

    @Override
    public MageTabLoader build() throws IOException, ParseException {
        checkState(experimentAccession != null, "Please invoke forExperimentAccession method to initialize the builder !");
        checkState(CollectionUtils.isNotEmpty(requiredFactorTypes), "Please invoke withRequiredFactorTypes method to initialize the builder !");

        investigation = parseInvestigation();
        scanNodes = investigation.SDRF.getNodes(ScanNode.class);

        factorNamesByType = extractFactorNames();

        processedExperimentRuns = extractProcessedExperimentRuns();
        return this;
    }

    @Override
    public Collection<ExperimentRun> getProcessedExperimentRuns() {
        return processedExperimentRuns;
    }

    protected Collection<ExperimentRun> extractProcessedExperimentRuns() throws IOException, ParseException {

        Collection<ExperimentRun> allExperimentRuns = extractAllExperimentRunsFromSdrf(scanNodes, investigation.IDF);

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
            return extractSpeciesFromSDRF();
        }

    }

    @Override
    public Map<String, String> getFactorNamesByType() {
        return factorNamesByType;
    }

    Map<String, String> extractFactorNames() {
        IDF idf = investigation.IDF;
        Map<String, String> namesByType = Maps.newHashMap();
        for (int i = 0; i < idf.experimentalFactorType.size(); i++) {
            String factorType = idf.experimentalFactorType.get(i);
            if (requiredFactorTypes.contains(factorType)) {
                namesByType.put(factorType, idf.experimentalFactorName.get(i));
            }
        }
        return namesByType;
    }

    private Set<String> extractSpeciesFromSDRF() {
        Set<String> species = Sets.newHashSet();
        for (ScanNode scanNode : scanNodes) {
            SourceNode firstScanNode = GraphUtils.findUpstreamNodes(scanNode, SourceNode.class).iterator().next();

            for (CharacteristicsAttribute characteristic : firstScanNode.characteristics) {
                if (characteristic.type.equalsIgnoreCase("ORGANISM")) {
                    species.add(characteristic.getAttributeValue());
                }
            }

        }
        return species;
    }

    //Required for testability - will be overridden to inject mock
    MAGETABInvestigation parseInvestigation() throws ParseException, IOException {

        String idfFileLocation = MessageFormat.format(idfPathTemplate, experimentAccession);
        LOGGER.info("<parseInvestigation> idfFileLocation = " + idfFileLocation);

        MAGETABParser<MAGETABInvestigation> mageTabParser = new MAGETABParser<>();
        File idfFile = new File(idfFileLocation);
        if (idfFile.exists()) {
            LOGGER.info("<parseInvestigation> investigation file exists on the filesystem, going to use it");
            return mageTabParser.parse(idfFile);
        } else {
            LOGGER.info("<parseInvestigation> investigation file not found on the filesystem, going to use online file");
            URL idfFileURL = new URL(MessageFormat.format(idfUrlTemplate, experimentAccession));
            return mageTabParser.parse(idfFileURL);
        }

    }

    Collection<ExperimentRun> extractAllExperimentRunsFromSdrf(Collection<ScanNode> scanNodes, IDF idf) throws ParseException {

        Collection<ExperimentRun> experimentRuns = new ArrayList<>();

        for (ScanNode scanNode : scanNodes) {

            if (scanNode.comments.keySet().contains(ENA_RUN)) {
                ExperimentRun run = buildExperimentRun(scanNode, idf);
                experimentRuns.add(run);
            }
        }
        return experimentRuns;
    }


    ExperimentRun buildExperimentRun(ScanNode scanNode, IDF idf) {
        checkState(factorNamesByType != null, "Please invoke the extractFactorNames method first");
        ExperimentRun experimentRun = new ExperimentRun(scanNode.comments.get(ENA_RUN));

        Collection<AssayNode> assayNodes = GraphUtils.findUpstreamNodes(scanNode, AssayNode.class);

        if (assayNodes.size() != 1) {
            throw new IllegalStateException("No assay corresponds to ENA run " + experimentRun.getAccession());
        }

        AssayNode assayNode = assayNodes.iterator().next();

        BiMap<String, String> factorTypesByName = HashBiMap.create(factorNamesByType).inverse();

        for (FactorValueAttribute factorValueAttribute : assayNode.factorValues) {


            String factorName = factorValueAttribute.getAttributeType();

            if(factorTypesByName.containsKey(factorName)){

                Factor factor = new Factor(factorTypesByName.get(factorName), factorValueAttribute.getAttributeValue());

                experimentRun.addFactor(factor);

            }
        }

        return experimentRun;
    }


}
