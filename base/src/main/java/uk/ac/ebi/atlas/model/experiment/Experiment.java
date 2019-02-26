package uk.ac.ebi.atlas.model.experiment;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/*
 * The displayName is a bit confusing - it's used for baseline landing page and I think only there.
 * There's also a title which is fetched from ArrayExpress or (as fallback) from the IDF file.
*/
public abstract class Experiment<D extends DescribesDataColumns> implements Serializable {
    private ExperimentType type;
    protected ExperimentDesign experimentDesign;
    private Species species;
    private List<String> pubMedIds;
    private List<String> dois;
    private String accession;
    private String secondaryAccession;
    protected String description;
    private String displayName;
    private String disclaimer;
    private Date lastUpdate;
    private List<String> dataProviderURL;
    private List<String> dataProviderDescription;
    private List<String> alternativeViews;
    private List<String> alternativeViewDescriptions;
    private final ImmutableMap<String, D> dataColumnDescriptorsPerId;
    private final ExperimentDisplayDefaults experimentDisplayDefaults;

    public Experiment(ExperimentType type, String accession, String secondaryAccession, Date lastUpdate,
                      String displayName, String description, String disclaimer, Species species,
                      Collection<String> pubMedIds, Collection<String> dois,
                      ExperimentDesign experimentDesign, List<String> dataProviderURL,
                      List<String> dataProviderDescription, List<String> alternativeViews,
                      List<String> alternativeViewDescriptions, List<D> dataColumnDescriptors,
                      ExperimentDisplayDefaults experimentDisplayDefaults) {

        this.type = type;
        this.lastUpdate = lastUpdate;
        this.experimentDesign = experimentDesign;
        this.accession = accession;
        this.secondaryAccession = secondaryAccession;
        this.displayName = displayName;
        this.description = description;
        this.disclaimer = disclaimer;
        this.species = species;
        this.pubMedIds = ImmutableList.copyOf(Sets.newTreeSet(pubMedIds));
        this.dois = ImmutableList.copyOf(Sets.newTreeSet(dois));
        this.dataProviderURL = dataProviderURL;
        this.dataProviderDescription = dataProviderDescription;
        this.alternativeViews = alternativeViews;
        this.alternativeViewDescriptions = alternativeViewDescriptions;
        ImmutableMap.Builder<String, D> builder = ImmutableMap.builder();
        for (D dataColumnDescriptor: dataColumnDescriptors) {
            builder.put(dataColumnDescriptor.getId(), dataColumnDescriptor);
        }
        this.dataColumnDescriptorsPerId = builder.build();
        this.experimentDisplayDefaults = experimentDisplayDefaults;

    }

    public List<D> getDataColumnDescriptors() {
        return ImmutableList.<D>builder().addAll(dataColumnDescriptorsPerId.values()).build();
    }

    public D getDataColumnDescriptor(String id) {
        return dataColumnDescriptorsPerId.get(id);
    }

    public ExperimentType getType() {
        return type;
    }

    public ExperimentDesign getExperimentDesign() {
        return experimentDesign;
    }

    public ExperimentDisplayDefaults getDisplayDefaults() {
        return experimentDisplayDefaults;
    }

    public String getDisplayName() {
        if (StringUtils.isNotBlank(displayName)) {
            return displayName;
        }
        return getAccession();
    }

    public String getDescription() {
        return description;
    }

    public String getAccession() {
        return accession;
    }

    public String getSecondaryAccession() {
        return secondaryAccession;
    }

    public Species getSpecies() {
        return species;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public List<String> getPubMedIds() {
        return pubMedIds;
    }

    public List<String> getDois() {
        return dois;
    }

    public List<String> getDataProviderDescription() {
        return dataProviderDescription;
    }

    public List<String> getDataProviderURL() {
        return dataProviderURL;
    }

    public List<String> getAlternativeViews() {
        return alternativeViews;
    }

    public List<String> getAlternativeViewDescriptions() {
        return alternativeViewDescriptions;
    }

    public List<Pair<String, String>> alternativeViews() {
        List<Pair<String, String>> result = new ArrayList<>();
        Preconditions.checkState(alternativeViews.size() == alternativeViewDescriptions.size());

        for (int i = 0; i < alternativeViews.size(); i++) {
            result.add(Pair.of(alternativeViews.get(i), alternativeViewDescriptions.get(i)));
        }
        return result;
    }

    public ImmutableSet<String> getAnalysedAssays() {
        return ImmutableSet.copyOf(
                getDataColumnDescriptors().stream()
                        .flatMap(dataColumnDescriptor ->
                                    dataColumnDescriptor.assaysAnalyzedForThisDataColumn().stream())
                        .collect(Collectors.toSet()));
    }

    public ExperimentInfo buildExperimentInfo() {
        ExperimentInfo experimentInfo = new ExperimentInfo();
        experimentInfo.setExperimentAccession(accession);
        experimentInfo.setLastUpdate(new SimpleDateFormat("dd-MM-yyyy").format(lastUpdate));
        experimentInfo.setExperimentDescription(description);
        experimentInfo.setSpecies(species.getName());
        experimentInfo.setKingdom(species.getKingdom());
        experimentInfo.setExperimentType(type.getParent());
        experimentInfo.setExperimentalFactors(experimentDesign.getFactorHeaders());
        experimentInfo.setNumberOfAssays(getAnalysedAssays().size());
        return experimentInfo;
    }

    public ImmutableCollection<ImmutableMap<String, String>> getGenomeBrowsers() {
        return type.isMicroRna() ? ImmutableList.of() : species.getGenomeBrowsers();
    }

    public ImmutableCollection<String> getGenomeBrowserNames() {
        if (type.isMicroRna()) {
            return ImmutableList.of();
        }

        ImmutableList.Builder<String> genomeBrowserNamesBuilder = ImmutableList.builder();
        for (ImmutableMap<String, String> genomeBrowser : getGenomeBrowsers()) {
            genomeBrowserNamesBuilder.add(genomeBrowser.get("name"));
        }
        return genomeBrowserNamesBuilder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Experiment<?> that = (Experiment<?>) o;
        return Objects.equal(accession, that.accession);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accession);
    }

    protected abstract JsonObject propertiesForAssay(String runOrAssay);

    public abstract JsonArray groupingsForHeatmap();
}
