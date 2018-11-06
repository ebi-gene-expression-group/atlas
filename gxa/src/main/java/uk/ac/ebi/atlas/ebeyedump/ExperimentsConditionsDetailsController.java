package uk.ac.ebi.atlas.ebeyedump;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;

import static java.lang.String.join;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.PROTEOMICS_BASELINE;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.RNASEQ_MRNA_BASELINE;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.RNASEQ_MRNA_DIFFERENTIAL;

@Controller
@Scope("request")
public class ExperimentsConditionsDetailsController {
    private ExpressionAtlasExperimentTrader experimentTrader;

    public ExperimentsConditionsDetailsController(ExpressionAtlasExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @GetMapping("/api/assaygroupsdetails.tsv")
    public void generateTsvFormatBaseline(HttpServletResponse response) {
        writeTsvLinesToResponse(
                response,
                experiment -> new BaselineExperimentAssayGroupsLines((BaselineExperiment) experiment),
                RNASEQ_MRNA_BASELINE,
                PROTEOMICS_BASELINE);
    }

    @GetMapping("/api/contrastdetails.tsv")
    public void generateTsvFormatDifferential(HttpServletResponse response) {
        writeTsvLinesToResponse(
                response,
                experiment -> new DifferentialExperimentContrastLines((DifferentialExperiment) experiment),
                MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL,
                MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL,
                RNASEQ_MRNA_DIFFERENTIAL);
    }

    private void writeTsvLinesToResponse(HttpServletResponse response,
                                         Function<Experiment, Iterable<String[]>> linesIteratorProducer,
                                         ExperimentType... experimentTypes) {
        response.setContentType("text/tab-separated-values");

        try {
            for (Experiment experiment : experimentTrader.getPublicExperiments(experimentTypes)) {
                for (String[] line : linesIteratorProducer.apply(experiment)) {
                    response.getWriter().write(join("\t", line) + "\n");
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
