package uk.ac.ebi.atlas.model.experiment.baseline;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;

import javax.inject.Named;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BaselineExperimentBuilder {

    private Species species;
    private String description;
    private String disclaimer;
    private List<String> dataProviderURL;
    private List<String> dataProviderDescription;
    private String displayName;
    private String experimentAccession;
    private Set<String> pubMedIds;
    private Set<String> dois;
    private ExperimentDesign experimentDesign;
    private Date lastUpdate;
    private List<AssayGroup> assayGroups;
    private ExperimentDisplayDefaults experimentDisplayDefaults;
    private ExperimentType experimentType;
    private List<String> alternativeViews = Collections.emptyList();
    private List<String> alternativeViewDescriptions = Collections.emptyList();

    public BaselineExperimentBuilder forSpecies(Species species){
        this.species = species;
        return this;
    }

    public BaselineExperimentBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public BaselineExperimentBuilder withDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
        return this;
    }

    public BaselineExperimentBuilder withDataProviderURL(List<String> dataProviderURL) {
        this.dataProviderURL = dataProviderURL;
        return this;
    }

    public BaselineExperimentBuilder withDataProviderDescription(List<String> dataProviderDescription) {
        this.dataProviderDescription = dataProviderDescription;
        return this;
    }

    public BaselineExperimentBuilder withDisplayDefaults(ExperimentDisplayDefaults experimentDisplayDefaults) {
        this.experimentDisplayDefaults = experimentDisplayDefaults;
        return this;
    }

    public BaselineExperimentBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public BaselineExperimentBuilder withPubMedIds(Set<String> pubMedIds) {
        this.pubMedIds = pubMedIds;
        return this;
    }

    public BaselineExperimentBuilder withDois(Set<String> dois) {
        this.dois = dois;
        return this;
    }

    public BaselineExperimentBuilder withExperimentDesign(ExperimentDesign experimentDesign) {
        this.experimentDesign = experimentDesign;
        return this;
    }

    public BaselineExperimentBuilder withAssayGroups(List<AssayGroup> assayGroups) {
        this.assayGroups = assayGroups;
        return this;
    }

    public BaselineExperimentBuilder withAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    public BaselineExperimentBuilder withLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    public BaselineExperimentBuilder ofType(ExperimentType experimentType) {
        this.experimentType = experimentType;
        return this;
    }

    public BaselineExperimentBuilder withAlternativeViews(Pair<List<String>, List<String>> p) {
        this.alternativeViews = p.getLeft();
        this.alternativeViewDescriptions = p.getRight();
        return this;
    }



    public BaselineExperiment create() {
        validate();

        return new
                BaselineExperiment(experimentType, experimentAccession, lastUpdate, description,
                displayName, disclaimer, species, pubMedIds, dois, experimentDesign, assayGroups, dataProviderURL,
                dataProviderDescription, alternativeViews, alternativeViewDescriptions , experimentDisplayDefaults);
    }

    private void validate() {
        checkNotNull(experimentType);
        checkState(experimentType.isBaseline());
        checkNotNull(assayGroups, "Please provide a non empty set of AssayGroup objects");
        checkNotNull(species, "Please provide a species name");
        checkState(CollectionUtils.isNotEmpty(assayGroups), "Please provide a non empty set of AssayGroup objects");
        checkState(experimentDesign != null, "Please provide a ExperimentDesign object");
        checkState(pubMedIds != null, "Please provide a pubMedIds object");

        if (StringUtils.isBlank(displayName)) {
            displayName = experimentAccession;
        }
    }

}