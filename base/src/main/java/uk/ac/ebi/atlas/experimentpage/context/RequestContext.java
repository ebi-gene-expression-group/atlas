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
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import javax.annotation.Nullable;
import java.util.*;

public abstract class RequestContext<DataColumnDescriptor extends DescribesDataColumns,E extends
        Experiment<DataColumnDescriptor>, K extends ExperimentPageRequestPreferences>
    implements ProfileStreamOptions<DataColumnDescriptor>{
    protected final K requestPreferences;
    protected final E experiment;

    public RequestContext(K requestPreferences, E experiment){
        this.requestPreferences = requestPreferences;
        this.experiment = experiment;
    }

    public String getExperimentAccession() {
        return experiment.getAccession();
    }

    public SemanticQuery getGeneQuery() {
        return requestPreferences.getGeneQuery();
    }

    public Integer getHeatmapMatrixSize() {
        return requestPreferences.getHeatmapMatrixSize();
    }

    public List<DataColumnDescriptor> getAllDataColumns(){
        return experiment.getDataColumnDescriptors();
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

    public Species getSpecies() {
        return experiment.getSpecies();
    }

    public double getCutoff() {
        return requestPreferences.getCutoff();
    }

    public boolean isSpecific() {
        return requestPreferences.isSpecific();
    }

    public List<DataColumnDescriptor> getAllDataColumnDescriptors() {
        return experiment.getDataColumnDescriptors();
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("requestPreferences", requestPreferences)
                .add("experimentAccession", experiment.getAccession())
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
