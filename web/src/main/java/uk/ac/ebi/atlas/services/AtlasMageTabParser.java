package uk.ac.ebi.atlas.services;

import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.SDRF;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AssayNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.arrayexpress2.magetab.parser.MAGETABParser;
import uk.ac.ebi.arrayexpress2.magetab.parser.SDRFParser;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentRun;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AtlasMageTabParser {

    private MAGETABParser parser;

    private MAGETABInvestigation investigation;

    private Experiment experiment;

    private static final String ENA_RUN = "ENA_RUN";

    private Map<String, ExperimentRun> experimentRuns = new HashMap<>();


    public AtlasMageTabParser(MAGETABParser parser) {
        this.parser = parser;
    }


    public AtlasMageTabParser parse(URL url) throws ParseException {
        investigation = parser.parse(url);
        experiment = new Experiment(investigation.getAccession());
        return this;
    }


    public Experiment getExperiment() {
        return experiment;
    }


    public String[] getSDRFHeaders(InputStream input) throws ParseException {
        SDRFParser sdrfParser = new SDRFParser();
        sdrfParser.setStripEscaping(true);
        SDRF sdrf = sdrfParser.parse(input);
        return sdrf.getLayout().getHeaders();
    }


    public Map<String, ExperimentRun> parseExperimentRuns() {
        Collection<ScanNode> scanNodes = investigation.SDRF.getNodes(ScanNode.class);
        for (ScanNode scanNode : scanNodes) {

            if (scanNode.comments.keySet().contains(ENA_RUN)) {
                ExperimentRun run = buildExperimentRun(scanNode);
                experimentRuns.put(run.getAccession(), run);
            }
        }

        return experimentRuns;
    }


    protected ExperimentRun buildExperimentRun(ScanNode scanNode) {
        ExperimentRun run = new ExperimentRun(scanNode.comments.get(ENA_RUN));

        Collection<AssayNode> assayNodes = GraphUtils.findUpstreamNodes(scanNode, AssayNode.class);

        if (assayNodes.size() != 1) {
            throw new IllegalStateException("No assay corresponds to ENA run " + run.getAccession());
        }

        AssayNode assayNode = assayNodes.iterator().next();

        for (FactorValueAttribute factorValueAttribute : assayNode.factorValues) {
            run.addFactorValue(factorValueAttribute.getAttributeType(), factorValueAttribute.getAttributeValue());
        }

        return run;
    }


    public Collection<ScanNode> getScanNodes() {
        return investigation.SDRF.getNodes(ScanNode.class);
    }


    public ScanNode getScanNode(String nodeName) {
        return investigation.SDRF.getNode(nodeName, ScanNode.class);
    }

}
