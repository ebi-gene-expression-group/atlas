package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;

import java.util.HashMap;
import java.util.Map;

import static uk.ac.ebi.atlas.model.baseline.BioentityPropertyName.*;

public abstract class ExperimentDataPoint {

    public static final ImmutableList<BioentityPropertyName> bioentityPropertyNames = ImmutableList.of(
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

    public final String bioentityIdentifier;

    protected Map<String, Object> propertyMap = new HashMap<>();

    public ExperimentDataPoint(String bioentityIdentifier, Experiment experiment, String conditionSearch){
        this.bioentityIdentifier = bioentityIdentifier;
        propertyMap.put("bioentityIdentifier", bioentityIdentifier);
        propertyMap.put("species", experiment.getSpecies().mappedName);
        propertyMap.put("kingdom", experiment.getSpecies().kingdom);
        propertyMap.put("experimentAccession", experiment.getAccession());
        propertyMap.put("experimentType", experiment.getType().name().toUpperCase());

        propertyMap.put("conditionsSearch", conditionSearch);

    }
    public Map<String, Object> getProperties(){
        return propertyMap;
    }

    public abstract ImmutableList<BioentityPropertyName> getRelevantBioentityPropertyNames();
}
