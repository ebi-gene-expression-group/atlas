
package uk.ac.ebi.atlas.experimentpage.analysismethods;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.experimentpage.fastqc.FastQCReportUtil;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Set;
import java.util.SortedSet;

@Controller
@Scope("request")
public class AnalysisMethodsPageController {

    private static final String QC_ARRAY_DESIGNS_ATTRIBUTE = "qcArrayDesigns";
    private static final String SPECIES = "species";
    protected static final String ALL_ARRAY_DESIGNS_ATTRIBUTE = "allArrayDesigns";

    private FileTsvReaderBuilder fileTsvReaderBuilder;

    private DownloadURLBuilder downloadURLBuilder;

    protected ArrayDesignTrader arrayDesignTrader;

    private FastQCReportUtil fastQCReportUtil;

    private String pdfFileTemplate;

    private final ExperimentTrader experimentTrader;

    @Inject
    public void setFastQCReportUtil(FastQCReportUtil fastQCReportUtil) {
        this.fastQCReportUtil = fastQCReportUtil;
    }

    @Inject
    public AnalysisMethodsPageController(FileTsvReaderBuilder fileTsvReaderBuilder, DownloadURLBuilder downloadURLBuilder,
                                         @Value("#{configuration['experiment.analysis-method.path.template']}") String pathTemplate,
                                         ArrayDesignTrader arrayDesignTrader,ExperimentTrader experimentTrader,
                                         @Value("#{configuration['analysis-methods.pdf.path.template']}") String pdfFileTemplate) {

        this.fileTsvReaderBuilder = fileTsvReaderBuilder.forTsvFilePathTemplate(pathTemplate);
        this.downloadURLBuilder = downloadURLBuilder;
        this.arrayDesignTrader = arrayDesignTrader;
        this.experimentTrader = experimentTrader;
        this.pdfFileTemplate = pdfFileTemplate;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods", params = {"type=RNASEQ_MRNA_BASELINE"})
    public String baselineAnalysisMethods(@PathVariable String experimentAccession,@RequestParam(value = "accessKey",
            required = false) String accessKey, Model model, HttpServletRequest request) throws IOException {
        BaselineExperiment experiment = (BaselineExperiment)
                experimentTrader.getExperiment(experimentAccession, accessKey);

        //This is necessary for adding functionality to the QC button
        Set<Factor> organisms = experiment.getExperimentalFactors().getDefaultFilterFactors();
        String specie = experiment.getFirstOrganism();

        if(!organisms.isEmpty()) {
            for (Factor factor : organisms) {
                if (factor.getType().equals("ORGANISM")) {
                    specie = factor.getValue();
                }
            }
        }

        try {
            if (fastQCReportUtil.hasFastQC(experimentAccession, specie)) {
                fastQCReportUtil.buildFastQCIndexHtmlPath(experimentAccession, specie);
                model.addAttribute(SPECIES, specie);
            }
        } catch (IOException e) {
            throw new ResourceNotFoundException("Species could not be found");
        }

        return analysisMethods(experimentAccession, model, request.getRequestURI());
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods", params = {"type=RNASEQ_MRNA_DIFFERENTIAL"})
    public String rnaSeqAnalysisMethods(@PathVariable String experimentAccession, Model model, HttpServletRequest request) throws IOException {
        return analysisMethods(experimentAccession, model, request.getRequestURI());
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods", params = {"type=MICROARRAY_ANY"})
    public String microArrayAnalysisMethods(@PathVariable String experimentAccession,
                                            @RequestParam(value = "accessKey",required = false) String accessKey,
                                            Model model, HttpServletRequest request) throws IOException {

        //For showing the QC REPORTS button in the header
        MicroarrayExperiment experiment = (MicroarrayExperiment)
                experimentTrader.getExperiment(experimentAccession,accessKey);
        request.setAttribute(QC_ARRAY_DESIGNS_ATTRIBUTE, experiment.getArrayDesignAccessions());

        SortedSet<String> arrayDesignNames = arrayDesignTrader.getArrayDesignNames(experiment.getArrayDesignAccessions());
        model.addAttribute(ALL_ARRAY_DESIGNS_ATTRIBUTE, arrayDesignNames);

        return analysisMethods(experimentAccession, model, request.getRequestURI());
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods", params = {"type=PROTEOMICS_BASELINE"})
    public String proteomicsAnalysisMethods(@PathVariable String experimentAccession, Model model, HttpServletRequest request) throws IOException {
        return analysisMethods(experimentAccession, model, request.getRequestURI());
    }

    public String analysisMethods(String experimentAccession, Model model, String requestURI) throws
            IOException {

        TsvReader tsvReader = fileTsvReaderBuilder.withExperimentAccession(experimentAccession).build();

        model.addAttribute("csvLines", tsvReader.readAll());

        model.addAttribute("experimentAccession", experimentAccession);

        model.addAllAttributes(downloadURLBuilder.dataDownloadUrls(requestURI));

        return "experiment-analysis-methods";
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods/{resource}.pdf", method = RequestMethod.GET)
    public String getAnalysisMethodsPdf(@PathVariable String experimentAccession,@PathVariable String resource) throws IOException {
        if(!hasPDF(experimentAccession, resource)) {
            throw new ResourceNotFoundException("No pdf for " + resource);
        }

        String path = generatePDFPath(experimentAccession, resource);
        return "forward:" + path;
    }

    /***** analysis-methods pdf path ****/
    public boolean hasPDF(String experimentAccession, String resource) throws IOException {
        String path = buildPDFPath(experimentAccession, resource);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

    private String buildPDFPath(String experimentAccession, String resource)  {
        return MessageFormat.format(pdfFileTemplate, experimentAccession, resource);
    }

    public String generatePDFPath(String experimentAccession, String resource) {
        return MessageFormat.format("/expdata/{0}/{1}.pdf", experimentAccession, resource);
    }

}
















