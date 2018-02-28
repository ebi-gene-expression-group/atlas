package uk.ac.ebi.atlas.solr.analytics.differential;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentpage.baseline.LinkToExperimentPage;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.RichFactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.search.SemanticQuery;

import java.util.Map;

public class LinkToContrast extends LinkToExperimentPage<Pair<String, Contrast>> {

    public LinkToContrast(SemanticQuery geneQuery){
        super(geneQuery);
    }

    @Override
    public Map<String, String> perInputQueryParameters(Pair<String, Contrast> experimentAccessionAndContrast) {
        /*
        This is coupled to a corresponding property set in DifferentialExperiment.java, and the UI code understanding filterFactors:{<type> : <value>}
         */
        return ImmutableMap.of("filterFactors",
                new RichFactorGroup(new FactorSet().add(new Factor("Comparison Name",
                        experimentAccessionAndContrast.getRight().getDisplayName()))).asUrlEncodedJson());
    }

    @Override
    public String accession(Pair<String, Contrast> experimentAccessionAndContrast) {
        return experimentAccessionAndContrast.getLeft();
    }
}
