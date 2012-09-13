package uk.ac.ebi.atlas.model.caches;

import com.google.common.cache.CacheLoader;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.arrayexpress2.magetab.parser.MAGETABParser;
import uk.ac.ebi.atlas.model.ExperimentRun;

import javax.inject.Named;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Named("investigationLoader")
public class MageTabInvestigationLoader extends CacheLoader<String, List<ExperimentRun>> {

    private static final String ENA_RUN = "ENA_RUN";

    private String idfFileUrlTemplate;

    @Value("#{webappProperties['magetab.idf.url.template']}")
    void setIdfFileUrlTemplate(String idfFileUrlTemplate){
        this.idfFileUrlTemplate = idfFileUrlTemplate;
    }

    @Override
    public List<ExperimentRun> load(String experimentAccession) throws ParseException, MalformedURLException {

        String idfFileLocation = buildIdfFileUrl(experimentAccession);

        URL idfFileURL = new URL(idfFileLocation);


        return  extractExperimentRuns(idfFileURL);

    }

    String buildIdfFileUrl(String experimentAccession) {
        return String.format(idfFileUrlTemplate, experimentAccession, experimentAccession);
    }


    public List<ExperimentRun> extractExperimentRuns(URL idfFileURL) throws ParseException {

        MAGETABInvestigation investigation = parseInvestigation(idfFileURL);

        Set<ExperimentRun> experimentRuns = new LinkedHashSet<>();

        Collection<ScanNode> scanNodes = investigation.SDRF.getNodes(ScanNode.class);
        for (ScanNode scanNode : scanNodes) {

            if (scanNode.comments.keySet().contains(ENA_RUN)) {
                ExperimentRun run = buildExperimentRun(scanNode);
                experimentRuns.add(run);
            }
        }
        return Lists.newArrayList(experimentRuns);
    }

    //Required for testability - will be overridden to inject mock
    MAGETABInvestigation parseInvestigation(URL idfFileURL) throws ParseException {
        MAGETABParser mageTabParser = new MAGETABParser();

        return mageTabParser.parse(idfFileURL);

    }

    ExperimentRun buildExperimentRun(ScanNode scanNode) {

        ExperimentRun run = new ExperimentRun(scanNode.comments.get(ENA_RUN));

        Collection<AssayNode> assayNodes = GraphUtils.findUpstreamNodes(scanNode, AssayNode.class);

        if (assayNodes.size() != 1) {
            throw new IllegalStateException("No assay corresponds to ENA run " + run.getRunAccession());
        }

        AssayNode assayNode = assayNodes.iterator().next();

        for (FactorValueAttribute factorValueAttribute : assayNode.factorValues) {
            run.addFactorValue(factorValueAttribute.type, factorValueAttribute.getAttributeValue());
        }

        return run;
    }


}
