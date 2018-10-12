package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.MoreObjects;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public abstract class RequestContext
        <D extends DescribesDataColumns,
         U extends ExpressionUnit,
         E extends Experiment<D>,
         K extends ExperimentPageRequestPreferences<U>>
        implements ProfileStreamOptions<D> {

    protected final K requestPreferences;
    protected final E experiment;

    public RequestContext(K requestPreferences, E experiment) {
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

    public List<D> getAllDataColumns() {
        return experiment.getDataColumnDescriptors();
    }

    protected Stream<D> dataColumnsToBeReturned() {
        final Collection<String> selectedIds = requestPreferences.getSelectedColumnIds();

        Predicate<D> keepColumns = selectedIds.isEmpty() ?
                dataColumnDescriptor -> true :
                dataColumnDescriptor -> selectedIds.contains(dataColumnDescriptor.getId());

        return experiment.getDataColumnDescriptors().stream().filter(keepColumns);
    }

    public List<D> getDataColumnsToReturn() {
        return experiment.getDisplayDefaults().preserveColumnOrder() ?
                dataColumnsToBeReturned()
                        .collect(toList()) :
                dataColumnsToBeReturned()
                        .sorted(Comparator.comparing(this::displayNameForColumn))
                        .collect(toList());
    }

    public abstract String displayNameForColumn(D dataColumnDescriptor);

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
