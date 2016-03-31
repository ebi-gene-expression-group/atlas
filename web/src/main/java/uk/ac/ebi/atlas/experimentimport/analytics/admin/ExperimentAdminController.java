package uk.ac.ebi.atlas.experimentimport.analytics.admin;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.ExperimentMetadataCRUD;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@Scope("request")
@RequestMapping("/admin/experiments")
public class ExperimentAdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentAdminController.class);

    private final ExperimentOps experimentOps;
    private final ExperimentTrader experimentTrader;
    private final ExperimentMetadataCRUD experimentMetadataCRUD;

    @Inject
    public ExperimentAdminController(ExperimentOps experimentOps, ExperimentTrader experimentTrader,
                                     ExperimentMetadataCRUD experimentMetadataCRUD) {
        this.experimentOps = experimentOps;
        this.experimentTrader = experimentTrader;
        this.experimentMetadataCRUD = experimentMetadataCRUD;
    }

    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public String listAllExperiments() {
        return "redirect:all/list";
    }

    @RequestMapping(
            value = "/{accessions}",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public String listExperiments(@PathVariable("accessions") String accessions) {
        return "redirect:" + accessions + "/list";
    }

    @RequestMapping(
            value = "/{accessions}/{op}",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String doOp(@PathVariable("accessions") String accessionParameter, @PathVariable("op") String opParameter) {
        ExperimentOps.Op op;
        try {
            op = ExperimentOps.Op.valueOf(opParameter.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new Gson().toJson("Operation " + opParameter + " not known. Here are the available ones: " +
                    Arrays.toString(ExperimentOps.Op.values()));
        }

        if (accessionParameter.length() == 0 || accessionParameter.toLowerCase().equals("all")) {
            // the case of all operations is sometimes optimized
            return new Gson().toJson(experimentOps.perform(op));
        } else {
            return new Gson().toJson(experimentOps.perform(readAccessions(accessionParameter), op));
        }
    }

    private Collection<String> readAccessions(String accessionParameter) {
        if (accessionParameter.toLowerCase().equals("baseline")) {
            return experimentTrader.getAllBaselineExperimentAccessions();
        } else if (accessionParameter.toLowerCase().equals("baseline_public")) {
            return experimentTrader.getBaselineExperimentAccessions();
        } else if (accessionParameter.toLowerCase().equals("microarray_differential")) {
            return experimentTrader.getMicroarrayExperimentAccessions();
        } else if (accessionParameter.toLowerCase().equals("proteomics")) {
            return experimentTrader.getProteomicsBaselineExperimentAccessions();
        } else if (accessionParameter.toLowerCase().equals("rnaseq_differential")) {
            return experimentTrader.getRnaSeqDifferentialExperimentAccessions();
        } else if (accessionParameter.contains("*")) {
            List<String> result = new ArrayList<>();
            Pattern pattern = Pattern.compile(accessionParameter.replaceAll("\\*", ".*"));
            for (ExperimentDTO dto : experimentMetadataCRUD.findAllExperiments()) {
                if (pattern.matcher(dto.getExperimentAccession()).matches()) {
                    result.add(dto.getExperimentAccession());
                }
            }
            return result;
        } else {
            return Arrays.asList(accessionParameter.split(","));
        }
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception e) throws IOException {
        String lineSeparator = "<br>";
        LOGGER.error(e.getMessage(), e);

        StringBuilder sb = new StringBuilder();
        sb.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(lineSeparator);

        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString()).append(lineSeparator);
        }

        return sb.toString();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleResourceNotFoundException(Exception e) throws IOException {
        LOGGER.error(e.getMessage(), e);
        return e.getClass().getSimpleName() + ": " + e.getMessage();
    }

}