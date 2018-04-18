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
public class SingleCellBaselineExperimentBuilder {

    private Species species;
    private String description;
    private String disclaimer;
    private List<String> dataProviderURL;
    private List<String> dataProviderDescription;
    private String displayName;
    private String experimentAccession;
    private Set<String> pubMedIds;
    private ExperimentDesign experimentDesign;
    private Date lastUpdate;
    private List<Cell> cells;
    private ExperimentDisplayDefaults experimentDisplayDefaults;
    private ExperimentType experimentType;
    private List<String> alternativeViews = Collections.emptyList();
    private List<String> alternativeViewDescriptions = Collections.emptyList();

    public SingleCellBaselineExperimentBuilder forSpecies(Species species){
        this.species = species;
        return this;
    }

    public SingleCellBaselineExperimentBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public SingleCellBaselineExperimentBuilder withDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
        return this;
    }

    public SingleCellBaselineExperimentBuilder withDataProviderURL(List<String> dataProviderURL) {
        this.dataProviderURL = dataProviderURL;
        return this;
    }

    public SingleCellBaselineExperimentBuilder withDataProviderDescription(List<String> dataProviderDescription) {
        this.dataProviderDescription = dataProviderDescription;
        return this;
    }

    public SingleCellBaselineExperimentBuilder withDisplayDefaults(ExperimentDisplayDefaults experimentDisplayDefaults) {
        this.experimentDisplayDefaults = experimentDisplayDefaults;
        return this;
    }

    public SingleCellBaselineExperimentBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public SingleCellBaselineExperimentBuilder withPubMedIds(Set<String> pubMedIds) {
        this.pubMedIds = pubMedIds;
        return this;
    }

    public SingleCellBaselineExperimentBuilder withExperimentDesign(ExperimentDesign experimentDesign) {
        this.experimentDesign = experimentDesign;
        return this;
    }

    public SingleCellBaselineExperimentBuilder withCells(List<Cell> cells) {
        this.cells = cells;
        return this;
    }

    public SingleCellBaselineExperimentBuilder withAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    public SingleCellBaselineExperimentBuilder withLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    public SingleCellBaselineExperimentBuilder ofType(ExperimentType experimentType) {
        this.experimentType = experimentType;
        return this;
    }

//    public SingleCellBaselineExperimentBuilder withAlternativeViews(Pair<List<String>, List<String>> p) {
//        this.alternativeViews = p.getLeft();
//        this.alternativeViewDescriptions = p.getRight();
//        return this;
//    }



    public SingleCellBaselineExperiment create() {
        validate();

        return new
                SingleCellBaselineExperiment(experimentType, experimentAccession, lastUpdate, displayName,
                description, disclaimer, species, pubMedIds, experimentDesign, dataProviderURL,
                dataProviderDescription, alternativeViews, alternativeViewDescriptions, cells, experimentDisplayDefaults);
    }

    private void validate() {
        checkNotNull(experimentType);
        checkState(experimentType.isBaseline());
//        checkNotNull(assayGroups, "Please provide a non empty set of AssayGroup objects");
        checkNotNull(species, "Please provide a species name");
//        checkState(CollectionUtils.isNotEmpty(assayGroups), "Please provide a non empty set of AssayGroup objects");
        checkState(experimentDesign != null, "Please provide a ExperimentDesign object");
        checkState(pubMedIds != null, "Please provide a pubMedIds object");

        if (StringUtils.isBlank(displayName)) {
            displayName = experimentAccession;
        }
    }
}
