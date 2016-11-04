package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;

import java.util.HashMap;
import java.util.Map;

public abstract class ExperimentDataPoint {

    public static final ImmutableList<BioentityPropertyName> bioentityPropertyNames = ImmutableList.of(
        BioentityPropertyName.GENE_BIOTYPE,
        BioentityPropertyName.HGNC_SYMBOL,
        BioentityPropertyName.RGD_SYMBOL,
        BioentityPropertyName.FLYBASENAME_GENE,
        BioentityPropertyName.SYMBOL,
        BioentityPropertyName.MIRBASE_NAME,
        BioentityPropertyName.SYNONYM,
        BioentityPropertyName.DESCRIPTION,
        BioentityPropertyName.MGI_ID,
        BioentityPropertyName.MGI_DESCRIPTION,
        BioentityPropertyName.GOTERM,
        BioentityPropertyName.GO,
        BioentityPropertyName.POTERM,
        BioentityPropertyName.PO,
        BioentityPropertyName.INTERPROTERM,
        BioentityPropertyName.INTERPRO,
        BioentityPropertyName.PATHWAYNAME,
        BioentityPropertyName.PATHWAYID,
        BioentityPropertyName.UNIPROT,
        BioentityPropertyName.DESIGN_ELEMENT
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
