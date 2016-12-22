package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParserOutput;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class ExperimentDTO {

    private final String experimentAccession;
    private final ExperimentType experimentType;
    private final String species;
    private final Set<String> pubmedIds;
    private final String title;
    private final Date lastUpdate;
    private final boolean isPrivate;
    private final String accessKey;


    public ExperimentDTO(String experimentAccession, ExperimentType experimentType, String species,
                         Set<String> pubmedIds, String title, Date lastUpdate, boolean isPrivate, String accessKey) {

        this.experimentAccession = experimentAccession;
        this.experimentType = experimentType;
        this.species = species;
        this.pubmedIds = pubmedIds;
        this.title = title;
        this.lastUpdate = lastUpdate;
        this.isPrivate = isPrivate;
        this.accessKey = accessKey;

    }

    static ExperimentDTO createNew(String experimentAccession, ExperimentType experimentType, String species,
                                   Set<String> pubmedIds, String title, boolean isPrivate) {

        return new ExperimentDTO(
                experimentAccession, experimentType, species, pubmedIds, title, null, isPrivate,
                UUID.randomUUID().toString());

    }

    static ExperimentDTO createNew(CondensedSdrfParserOutput condensedSdrfParserOutput, String species,
                                   boolean isPrivate) {

        return ExperimentDTO.createNew(
                condensedSdrfParserOutput.getExperimentAccession(), condensedSdrfParserOutput.getExperimentType(),
                species, condensedSdrfParserOutput.getPubmedIds(), condensedSdrfParserOutput.getTitle(), isPrivate);

    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public ExperimentType getExperimentType() {
        return experimentType;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(experimentAccession, experimentType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExperimentDTO) {
            ExperimentDTO other = (ExperimentDTO) obj;
            return this.experimentAccession.equals(other.experimentAccession) &&
                    this.experimentType.equals(other.experimentType);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ExperimentAccession", experimentAccession)
                .add("ExperimentType", experimentType)
                .add("species", species)
                .add("pubmedIds", pubmedIds)
                .add("title", title)
                .add("isPrivate", isPrivate)
                .add("accessKey", accessKey)
                .add("lastUpdate", lastUpdate).toString();
    }

    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        result.add("accession", new JsonPrimitive(experimentAccession));
        result.add("type", new JsonPrimitive(experimentType.name()));
        result.add("species", new JsonPrimitive(species));
        JsonArray pubmedIdsArray = new JsonArray();
        for(String id: pubmedIds){
            pubmedIdsArray.add(new JsonPrimitive(id));
        }
        result.add("pubmedIds", pubmedIdsArray);
        result.add("title", new JsonPrimitive(title));
        result.add("isPrivate", new JsonPrimitive(isPrivate));
        result.add("accessKey", new JsonPrimitive(accessKey));
        result.add("lastUpdate", new JsonPrimitive(lastUpdate.toString()));

        return result;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSpecies() {
        return species;
    }

    public Set<String> getPubmedIds() {
        return pubmedIds;
    }

    public String getTitle() {
        return title;
    }

}