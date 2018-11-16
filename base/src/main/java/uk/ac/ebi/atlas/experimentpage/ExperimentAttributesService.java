package uk.ac.ebi.atlas.experimentpage;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.model.Publication;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.utils.EuropePmcClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExperimentAttributesService {

    private EuropePmcClient europePmcClient;
    private IdfParser idfParser;

    public ExperimentAttributesService(EuropePmcClient europePmcClient, IdfParser idfParser) {
        this.europePmcClient = europePmcClient;
        this.idfParser = idfParser;
    }

    public Map<String, Object> getAttributes(Experiment<?> experiment) {
        Map<String, Object> result = new HashMap<>();
        result.put("experimentAccession", experiment.getAccession());
        result.put("experimentDescription", experiment.getDescription());
        result.put("type", experiment.getType().getHumanDescription());
        result.putAll(experiment.getSpecies().getAttributes());
        result.put("pubMedIds", experiment.getPubMedIds());
        result.put("dois", experiment.getDois());
        result.put("disclaimer", experiment.getDisclaimer());
        result.put("lastUpdated", new SimpleDateFormat("dd-MM-yyyy").format(experiment.getLastUpdate()));
        result.put("numberOfAssays", experiment.getAnalysedAssays().size());
        result.put("factors", experiment.getExperimentDesign().getFactorHeaders());

        if (!experiment.getDois().isEmpty()) {
            result.put("publications", getPublications(experiment.getDois()));
        } else if (!experiment.getPubMedIds().isEmpty()) {
            result.put("publications", getPublications(experiment.getPubMedIds()));
        }

        result.put("longDescription", idfParser.parse(experiment.getAccession()).getExperimentDescription());
        // Internet says keywords are not that useful for SEO any more. Remove if it causes you problems.
        List<String> keywords = ImmutableList.<String>builder()
                .add("experiment")
                .add(experiment.getAccession())
                .addAll(experiment.getDataProviderDescription())
                .addAll(Arrays.asList(experiment.getType().getDescription().split("_")))
                .addAll(experiment.getExperimentDesign().getAssayHeaders())
                .build();
        result.put("pageKeywords", Joiner.on(',').join(keywords));

        // We want this to show up in Google searches.
        result.put("pageDescription", experiment.getDescription());

        // Extra information to show on experiment page (if they were provided in <expAcc>-factors.xml file)
        result.put("dataProviderURL", experiment.getDataProviderURL());
        result.put("dataProviderDescription", experiment.getDataProviderDescription());
        result.put("alternativeViews", experiment.getAlternativeViews());
        result.put("alternativeViewDescriptions", experiment.getAlternativeViewDescriptions());

        // TODO This could probably be improved...
        if (experiment instanceof MicroarrayExperiment) {
            MicroarrayExperiment microarrayExperiment = (MicroarrayExperiment) experiment;

            result.put("arrayDesignAccessions", microarrayExperiment.getArrayDesignAccessions());
            result.put("arrayDesignNames", microarrayExperiment.getArrayDesignNames());
        } else if (experiment instanceof DifferentialExperiment) {
            result.put("regulationValues", Regulation.values());
            result.put("contrasts", experiment.getDataColumnDescriptors());
        }

        return result;
    }

    private List<Publication> getPublications(List<String> identifiers) {
        List<Publication> publications = new ArrayList<>();
        for (String id : identifiers) {
            europePmcClient.getPublicationByIdentifier(id).ifPresent(publications::add);
        }

        return publications;
    }
}
