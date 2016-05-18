
package uk.ac.ebi.atlas.experimentimport;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Named;
import java.util.Set;

@Named
@Scope("prototype")
public class ExperimentDTOBuilder {
    private String experimentAccession;
    private ExperimentType experimentType;
    private boolean isPrivate;

    private Set<String> species;
    private String title;
    private Set<String> pubmedIds;

    public ExperimentDTOBuilder forExperimentAccession(String experimentAccession){
        this.experimentAccession = experimentAccession;
        return this;
    }

    public ExperimentDTOBuilder withExperimentType(ExperimentType experimentType){
        this.experimentType = experimentType;
        return this;
    }

    public ExperimentDTOBuilder withPrivate(boolean isPrivate){
        this.isPrivate = isPrivate;
        return this;
    }

    public ExperimentDTOBuilder withSpecies(Set<String> species) {
        this.species = species;
        return this;
    }

    public ExperimentDTOBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ExperimentDTOBuilder withPubMedIds(Set<String> pubmedIds) {
        this.pubmedIds = pubmedIds;
        return this;
    }

    public ExperimentDTO build(){
        return new ExperimentDTO(experimentAccession, experimentType, species, pubmedIds, title, isPrivate);
    }
}
