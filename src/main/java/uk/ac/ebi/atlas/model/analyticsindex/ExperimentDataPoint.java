package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.apache.solr.common.SolrInputDocument;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;

import static uk.ac.ebi.atlas.model.baseline.BioentityPropertyName.*;

import java.util.*;

public abstract class ExperimentDataPoint {

    public final String bioentityIdentifier;

    public static ImmutableList<BioentityPropertyName> bioentityPropertyNames = ImmutableList.of(
        GENE_BIOTYPE,
        HGNC_SYMBOL,
        RGD_SYMBOL,
        FLYBASENAME_GENE,
        SYMBOL,
        MIRBASE_NAME,
        SYNONYM,
        DESCRIPTION,
        MGI_ID,
        MGI_DESCRIPTION,
        GOTERM,
        GO,
        POTERM,
        PO,
        INTERPROTERM,
        INTERPRO,
        PATHWAYNAME,
        PATHWAYID,
        UNIPROT,
        DESIGN_ELEMENT
    );
    protected Map<String, Object> propertyMap = new HashMap<>();

    public ExperimentDataPoint(String bioentityIdentifier, Experiment experiment, String conditionSearch){
        this.bioentityIdentifier = bioentityIdentifier;
        propertyMap.put("bioentityIdentifier", bioentityIdentifier);
        propertyMap.put("species", experiment.getSpecies().mappedName);
        propertyMap.put("kingdom", experiment.getSpecies().kingdom);
        propertyMap.put("experimentAccession", experiment.getAccession());
        propertyMap.put("experimentType", experiment.getType().name().toUpperCase());

        propertyMap.put("conditionSearch", conditionSearch);

    }
    public Map<String, Object> getProperties(){
        return propertyMap;
    }

    public abstract ImmutableList<BioentityPropertyName> getRelevantBioentityPropertyNames();
}
