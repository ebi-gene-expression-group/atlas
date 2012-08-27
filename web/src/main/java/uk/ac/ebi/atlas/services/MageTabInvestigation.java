package uk.ac.ebi.atlas.services;

import org.apache.log4j.Logger;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.arrayexpress2.magetab.parser.MAGETABParser;
import uk.ac.ebi.atlas.model.ExperimentRun;

import java.net.URL;
import java.util.*;

public class MageTabInvestigation {
    private static final Logger logger = Logger.getLogger(MageTabInvestigation.class);

    private MAGETABParser parser;

    private MAGETABInvestigation investigation;

    private static final String ENA_RUN = "ENA_RUN";

    public static MageTabInvestigation parse(URL url){
        MageTabInvestigation investigation = new MageTabInvestigation(new MAGETABParser());
        return investigation.parseInvestigation(url);
    }

    MageTabInvestigation(MAGETABParser parser) {
        this.parser = parser;
    }

    MageTabInvestigation parseInvestigation(URL url) {
        try {
            investigation = parser.parse(url);
            return this;
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("ParseException thrown when parsing investigation file: " + e.getMessage());
        }

    }


    public Set<ExperimentRun> extractExperimentRuns() {
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
