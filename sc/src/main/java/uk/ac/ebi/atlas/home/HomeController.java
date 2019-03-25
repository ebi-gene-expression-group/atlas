package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.experiments.ExperimentInfoListService;
import uk.ac.ebi.atlas.species.AtlasInformationDao;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.Map;

import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.PROTEOMICS_BASELINE;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.RNASEQ_MRNA_BASELINE;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.RNASEQ_MRNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE;
import static uk.ac.ebi.atlas.species.AtlasInformationDataType.EFO;
import static uk.ac.ebi.atlas.species.AtlasInformationDataType.ENSEMBL;
import static uk.ac.ebi.atlas.species.AtlasInformationDataType.GENOMES;
import static uk.ac.ebi.atlas.species.AtlasInformationDataType.PARASITE;

@Controller
public class HomeController extends HtmlExceptionHandlingController {
    private final LatestExperimentsService latestExperimentsService;
    private final ExperimentInfoListService experimentInfoListService;
    private final AtlasInformationDao atlasInformationDao;

    public HomeController(LatestExperimentsDao latestExperimentsDao,
                          ScxaExperimentTrader experimentTrader,
                          AtlasInformationDao atlasInformationDao) {
        latestExperimentsService = new LatestExperimentsService(latestExperimentsDao, experimentTrader);
        this.experimentInfoListService =
                new ExperimentInfoListService(experimentTrader, ImmutableList.of(SINGLE_CELL_RNASEQ_MRNA_BASELINE));
        this.atlasInformationDao = atlasInformationDao;
    }

    @RequestMapping(value = "/home")
    public String getHomePage(Model model) {
        model.addAllAttributes(latestExperimentsService.fetchLatestExperimentsAttributes());

        model.addAttribute("numberOfStudies", experimentInfoListService.listPublicExperiments().size());

        long numberOfSpecies =
                experimentInfoListService.listPublicExperiments().stream()
                        .map(ExperimentInfo::getSpecies)
                        .distinct()
                        .count();
        model.addAttribute("numberOfSpecies", numberOfSpecies);

        int numberOfAssays =
                experimentInfoListService.listPublicExperiments().stream()
                        .mapToInt(ExperimentInfo::getNumberOfAssays)
                        .sum();
        model.addAttribute("numberOfAssays", numberOfAssays);

        Map<String, String> atlasInformation = atlasInformationDao.fetchAll();
        model.addAttribute("info", atlasInformation);
        model.addAttribute("ensembl", ENSEMBL.getId());
        model.addAttribute("genomes", GENOMES.getId());
        model.addAttribute("paraSite", PARASITE.getId());
        model.addAttribute("efo", EFO.getId());

        return "home";
    }
}
