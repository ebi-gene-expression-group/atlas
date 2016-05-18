
package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import java.util.Collections;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;


public abstract class RequestContext<T, K extends ExperimentPageRequestPreferences> {
    private K requestPreferences;
    private Set<T> selectedQueryFactors;
    private String filteredBySpecies;
    private Set<T> allQueryFactors;
    private String queryDescription;

    public String getQueryDescription(){
        return queryDescription;
    }

    public GeneQuery getGeneQuery() {
        return getRequestPreferences().getGeneQuery();
    }

    public Integer getHeatmapMatrixSize() {
        return getRequestPreferences().getHeatmapMatrixSize();
    }

    public Set<T> getSelectedQueryFactors() {
        return selectedQueryFactors;
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

    public boolean isExactMatch() {
        return getRequestPreferences().isExactMatch();
    }

    public Set<T> getAllQueryFactors() {
        return allQueryFactors;
    }

    void setAllQueryFactors(Set<T> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
    }

    void setSelectedQueryFactors(Set<T> selectedQueryFactors) {
        this.selectedQueryFactors = selectedQueryFactors;
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
        return Objects.toStringHelper(getClass())
                .add("requestPreferences", getRequestPreferences())
                .add("filteredBySpecies", filteredBySpecies)
                .toString();
    }

}
