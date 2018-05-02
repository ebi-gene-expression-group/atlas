package uk.ac.ebi.atlas.experimentpage;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TsnePlotSettingsService {
    private final DataFileHub dataFileHub;
    private final IdfParser idfParser;

    public TsnePlotSettingsService(DataFileHub dataFileHub, IdfParser idfParser) {
        this.dataFileHub = dataFileHub;
        this.idfParser = idfParser;
    }

    public List<Integer> getAvailableClusters(String experimentAccession) {
        return dataFileHub.getSingleCellExperimentFiles(experimentAccession).clustersTsv.get().get()
                .skip(1)
                .map(line -> Integer.parseInt(line[1]))
                .collect(Collectors.toList());
    }

    // TODO Get available perplexities from scxa_tsne https://www.pivotaltracker.com/story/show/154898174
    public List<Integer> getAvailablePerplexities(String experimentAccession) {
        return Arrays.asList(1, 5, 10, 15, 20);
    }

    public Integer getExpectedClusters(String experimentAccession) {
        Integer expectedClusters = null;

        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);

        // Only add preferred cluster property if it exists in the idf file and it is one of the available k values
        if (idfParserOutput.getExpectedClusters() != 0 && getAvailableClusters(experimentAccession).contains(idfParserOutput.getExpectedClusters())) {
            expectedClusters = idfParserOutput.getExpectedClusters();
        }
        else {
            Optional<Integer> optimalClusterFromFile = dataFileHub.getSingleCellExperimentFiles(experimentAccession).clustersTsv.get().get()
                    .skip(1)
                    .filter(line -> line[0].equalsIgnoreCase("false"))
                    .map(line -> Integer.parseInt(line[1]))
                    .findFirst();

            if(optimalClusterFromFile.isPresent()) {
                expectedClusters = optimalClusterFromFile.get();
            }
        }

        return expectedClusters;
    }


}
