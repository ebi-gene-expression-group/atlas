package uk.ac.ebi.atlas.model;

import com.google.common.cache.CacheLoader;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.arrayexpress2.magetab.parser.MAGETABParser;

import javax.inject.Named;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Named("investigationLoader")
public class MageTabInvestigationLoader extends CacheLoader<String, List<ExperimentRun>> {

    private static final String ENA_RUN = "ENA_RUN";

    @Value("#{webappProperties['magetab.idf.url.template']}")
    private String idfFileUrlTemplate;

    @Override
    public List<ExperimentRun> load(String experimentAccession) throws Exception {

        String idfFileLocation = String.format(idfFileUrlTemplate, experimentAccession, experimentAccession);

        URL idfFileURL = new URL(idfFileLocation);

        MAGETABParser parser = new MAGETABParser();

        MAGETABInvestigation investigation= parser.parse(idfFileURL);

        return  Lists.newArrayList(extractExperimentRuns(investigation));

    }


    public Set<ExperimentRun> extractExperimentRuns(MAGETABInvestigation investigation) {
        Set<ExperimentRun> experimentRuns = new LinkedHashSet<>();

        Collection<ScanNode> scanNodes = investigation.SDRF.getNodes(ScanNode.class);
        for (ScanNode scanNode : scanNodes) {

            if (scanNode.comments.keySet().contains(ENA_RUN)) {
                ExperimentRun run = buildExperimentRun(scanNode);
                experimentRuns.add(run);
            }
        }
        return experimentRuns;
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
