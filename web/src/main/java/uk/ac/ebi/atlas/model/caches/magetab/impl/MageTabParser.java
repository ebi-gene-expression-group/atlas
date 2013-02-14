package uk.ac.ebi.atlas.model.caches.magetab.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class MageTabParser implements MageTabLoader, MageTabLoaderBuilder {

    private static final Logger LOGGER = Logger.getLogger(RankGeneProfilesCommand.class);

    @Value("#{configuration['experiment.magetab.idf.url.template']}")
    private String idfUrlTemplate;

    @Value("#{configuration['experiment.magetab.idf.path.template']}")
    private String idfPathTemplate;

    private Set<String> processedExperimentRunAccessions;

    private String experimentAccession;

    private static final String ENA_RUN = "ENA_RUN";

    private Collection<ScanNode> scanNodes;

    private Set<String> requiredFactorTypes;

    private MAGETABInvestigation investigation;

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
    public MageTabLoaderBuilder withProcessedExperimentRunAccessions(Set<String> processedExperimentRunAccessions) {
        this.processedExperimentRunAccessions = processedExperimentRunAccessions;
        return this;
    }

    @Override
    public MageTabLoader build() throws IOException, ParseException {
        checkState(experimentAccession != null, "Please invoke forExperimentAccession method to initialize the builder !");
        checkState(CollectionUtils.isNotEmpty(requiredFactorTypes), "Please invoke withRequiredFactorTypes method to initialize the builder !");

        investigation = parseInvestigation();
        scanNodes = investigation.SDRF.getNodes(ScanNode.class);
        return this;
    }


    @Override
    public Collection<ExperimentRun> extractExperimentRuns() throws IOException, ParseException {

        Collection<ExperimentRun> allExperimentRuns = extractAllExperimentRunsFromSdrf(scanNodes, investigation.IDF);

        Collection<ExperimentRun> processedExperimentRuns = Lists.newArrayList();

        for (ExperimentRun experimentRun: allExperimentRuns) {
            if (processedExperimentRunAccessions.contains(experimentRun.getRunAccession())){
                processedExperimentRuns.add(experimentRun);
            }
        }
        return processedExperimentRuns;
    }

    @Override
    public Set<String> extractSpecies() {
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
        LOGGER.info("<parseInvestigation> idfFileLocation = " +idfFileLocation);

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
                        break;
                    }
                }
            }
            Factor factor = new Factor(factorType, factorName, factorValueAttribute.getAttributeValue());
            if (requiredFactorTypes.contains(factor.getType())){

                experimentRun.addFactor(factor);
            }
        }

        return experimentRun;
    }


}
