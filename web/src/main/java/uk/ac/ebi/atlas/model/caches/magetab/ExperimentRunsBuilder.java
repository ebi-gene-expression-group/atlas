package uk.ac.ebi.atlas.model.caches.magetab;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
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
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.readers.AnalysisMethodsTsvReader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class ExperimentRunsBuilder implements  MageTabLoader, MageTabLoaderBuilder{

    @Value("#{configuration['experiment.magetab.idf.url.template']}")
    private String idfUrlTemplate;

    @Value("#{configuration['experiment.magetab.idf.path.template']}")
    private String idfPathTemplate;

    private AnalysisMethodsTsvReader analysisMethodsTsvReader;

    private String experimentAccession;

    private static final String ENA_RUN = "ENA_RUN";

    private Collection<ScanNode> scanNodes;

    @Inject
    public ExperimentRunsBuilder(AnalysisMethodsTsvReader analysisMethodsTsvReader) {
        this.analysisMethodsTsvReader = analysisMethodsTsvReader;
    }

    public ExperimentRunsBuilder forExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    @Override
    public MageTabLoaderBuilder withRequiredFactorTypes(Set<String> factorTypes) {
        return null;
    }

    @Override
    public MageTabLoader build() {
        return null;
    }


    @Override
    public Collection<ExperimentRun> extractExperimentRuns() throws IOException, ParseException {
        MAGETABInvestigation investigation = parseInvestigation();
        scanNodes = investigation.SDRF.getNodes(ScanNode.class);

        Collection<ExperimentRun> allExperimentRuns = extractAllExperimentRunsFromSdrf(scanNodes, investigation.IDF);

        //ToDo: Collections2.filter replace with standard collection
        return Collections2.filter(allExperimentRuns, new IsExperimentRunSelected(experimentAccession));

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
        MAGETABParser<MAGETABInvestigation> mageTabParser = new MAGETABParser<>();
        File idfFile = new File(idfFileLocation);
        if (idfFile.exists()) {
            return mageTabParser.parse(idfFile);
        } else {
            URL idfFileURL = new URL(MessageFormat.format(idfUrlTemplate, experimentAccession));
            return mageTabParser.parse(idfFileURL);
        }

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


    class IsExperimentRunSelected implements Predicate<ExperimentRun> {

        Set<String> selectedRunAccessions;

        public IsExperimentRunSelected(String experimentAccession) throws IOException {
            selectedRunAccessions = getSelectedRunAccessions(experimentAccession);
        }

        @Override
        public boolean apply(ExperimentRun experimentRun) {
            return selectedRunAccessions.contains(experimentRun.getRunAccession());
        }

        protected final Set<String> getSelectedRunAccessions(String experimentAccession) throws IOException {

            return analysisMethodsTsvReader.readProcessedLibraries(experimentAccession);

        }

    }
}
