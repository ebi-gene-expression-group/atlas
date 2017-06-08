package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public abstract class RequestContext<DataColumnDescriptor extends DescribesDataColumns,E extends
        Experiment<DataColumnDescriptor>, K extends ExperimentPageRequestPreferences>
    implements ProfileStreamOptions<DataColumnDescriptor>{
    protected final K requestPreferences;
    protected final E experiment;

    public RequestContext(K requestPreferences, E experiment){
        this.requestPreferences = requestPreferences;
        this.experiment = experiment;
    }

    public String serializationShortString(){
        return requestPreferences.serializationShortString();
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

    protected FluentIterable<DataColumnDescriptor> dataColumnsToBeReturned(){
        final Collection<String> selectedIds = requestPreferences.getSelectedColumnIds();
        Predicate<DataColumnDescriptor> keepColumns =
                selectedIds.isEmpty() ? Predicates.<DataColumnDescriptor>alwaysTrue()
                        : new Predicate<DataColumnDescriptor>() {
                    @Override
                    public boolean apply(@Nullable DataColumnDescriptor dataColumnDescriptor) {
                        return selectedIds.contains(dataColumnDescriptor.getId());
                    }
                };
        return FluentIterable.from(experiment.getDataColumnDescriptors()).filter(keepColumns);
    }

    public List<DataColumnDescriptor> getDataColumnsToReturn() {
        return experiment.getDisplayDefaults().preserveColumnOrder()
                ? dataColumnsToBeReturned().toList()
                : dataColumnsToBeReturned().toSortedList(new Comparator<DataColumnDescriptor>() {
            @Override
            public int compare(DataColumnDescriptor o1, DataColumnDescriptor o2) {
                return displayNameForColumn(o1).compareTo(displayNameForColumn(o2));
            }
        });
    }



    public abstract String displayNameForColumn(DataColumnDescriptor dataColumnDescriptor);

    public Species getSpecies() {
        return experiment.getSpecies();
    }

    public double getCutoff() {
        return requestPreferences.getCutoff();
    }

    public boolean isSpecific() {
        return requestPreferences.isSpecific();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("requestPreferences", requestPreferences)
                .add("experimentAccession", experiment.getAccession())
                .toString();
    }
}
