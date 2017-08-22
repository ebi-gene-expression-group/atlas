package uk.ac.ebi.atlas.experimentimport;

import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

public class ExperimentDTOTest {

    public static ExperimentDTO mockDTO(String accession, ExperimentType experimentType){
        return new ExperimentDTO(accession, experimentType,
                "Homo sapiens", new HashSet<>(), "title", new Date(), false, UUID.randomUUID().toString());
    }
}