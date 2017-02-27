package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import javax.annotation.Nullable;
import java.util.*;

public abstract class RequestContext<DataColumnDescriptor extends DescribesDataColumns, K extends ExperimentPageRequestPreferences> {
    private K requestPreferences;
    private String filteredBySpecies;
    private String queryDescription;
    protected Experiment<DataColumnDescriptor> experiment;

    public void setExperiment(Experiment<DataColumnDescriptor> experiment){
        this.experiment = experiment;
    }

    public String getExperimentAccession() {
        return experiment.getAccession();
    }

    public String getQueryDescription(){
        return queryDescription;
    }

    public SemanticQuery getGeneQuery() {
        return getRequestPreferences().getGeneQuery();
    }

    public Integer getHeatmapMatrixSize() {
        return getRequestPreferences().getHeatmapMatrixSize();
    }

    public List<DataColumnDescriptor> getDataColumnsToReturn() {
        final Collection<String> selectedIds = requestPreferences.getSelectedColumnIds();
        if(selectedIds.isEmpty()){
            return experiment.getDataColumnDescriptors();
        } else {
            return FluentIterable.from(experiment.getDataColumnDescriptors()).filter(new Predicate<DataColumnDescriptor>() {
                 @Override
                 public boolean apply(@Nullable DataColumnDescriptor dataColumnDescriptor) {
                     return selectedIds.contains(dataColumnDescriptor.getId());
                 }
             }).toList();
        }
    }

    public String displayNameForColumn(DataColumnDescriptor dataColumnDescriptor){
        return getDataColumnsToReturn().contains(dataColumnDescriptor) ? displayNamePerSelectedDataColumnDescriptor().get
                (dataColumnDescriptor) : "";
    }

    // the species for the current slice. Note: this is the mapped Ensembl species, ie: not the SDRF sample organism,
    // but the mapped Ensembl species for the sample (usually this is the same however)
    public String getFilteredBySpecies() {
        return filteredBySpecies;
    }

    public double getCutoff() {
        return getRequestPreferences().getCutoff();
    }

    public boolean isSpecific() {
        return getRequestPreferences().isSpecific();
    }

    public List<DataColumnDescriptor> getAllDataColumnDescriptors() {
        return experiment.getDataColumnDescriptors();
    }

    void setFilteredBySpecies(String filteredBySpecies) {
        this.filteredBySpecies = filteredBySpecies;
    }

    void setQueryDescription(String queryDescription){
        this.queryDescription = queryDescription;
    }

    protected void setRequestPreferences(K requestPreferences) {
        this.requestPreferences = requestPreferences;
    }

    protected K getRequestPreferences() {
        return requestPreferences;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("requestPreferences", getRequestPreferences())
                .add("filteredBySpecies", filteredBySpecies)
                .toString();
    }

    //feel free to cache this bad boy
    private Map<DataColumnDescriptor, String> displayNamePerSelectedDataColumnDescriptor(){
        Map<DataColumnDescriptor, Map<String, Factor>> factorsByTypePerId = new HashMap<>();
        for(DataColumnDescriptor dataColumnDescriptor: getDataColumnsToReturn()){
            factorsByTypePerId.put(dataColumnDescriptor,
                    experiment.getExperimentDesign()
                            .getFactors(dataColumnDescriptor.getId())
                            .factorsByType
            );
        }
        final Map<String, Set<String>> allValuesPerType = new HashMap<>();

        for(Map.Entry<DataColumnDescriptor, Map<String, Factor>> e: factorsByTypePerId.entrySet()){
            for(Map.Entry<String, Factor> ee : e.getValue().entrySet()){
                if(!allValuesPerType.containsKey(ee.getKey())){
                    allValuesPerType.put(ee.getKey(), new HashSet<String>());
                }
                allValuesPerType.get(ee.getKey()).add(ee.getValue().getValue());
            }
        }

        List<String> typesWhoseValuesVaryAcrossSelectedDescriptors =
                FluentIterable.from(experiment.getDisplayDefaults().prescribedOrderOfFilters())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean apply(@Nullable String type) {
                                return allValuesPerType.containsKey(type) && allValuesPerType.get(type).size() >1 ;
                            }
                        }).toList();

        ImmutableMap.Builder<DataColumnDescriptor, String> b = ImmutableMap.builder();

        for(final Map.Entry<DataColumnDescriptor, Map<String, Factor>> e : factorsByTypePerId.entrySet()){
            b.put(e.getKey(), Joiner.on(", ").join(FluentIterable.from
                    (typesWhoseValuesVaryAcrossSelectedDescriptors).transform(new Function<String, String>() {
                @Nullable
                @Override
                public String apply(@Nullable String type) {
                    return e.getValue().get(type).getValue();
                }
            })));
        }

        return b.build();
    }

}
