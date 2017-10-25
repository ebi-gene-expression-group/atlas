package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import java.util.HashMap;
import java.util.Map;

public abstract class ExperimentDataPoint {

    public static final ImmutableList<BioentityPropertyName> bioentityPropertyNames = ImmutableList.of(
        BioentityPropertyName.ENSGENE,
        BioentityPropertyName.GENE_BIOTYPE,
        BioentityPropertyName.HGNC_SYMBOL,
        BioentityPropertyName.RGD_SYMBOL,
        BioentityPropertyName.FLYBASE_GENE_ID,
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

    public ExperimentDataPoint(String bioentityIdentifier, Experiment experiment, String conditionSearch) {
        this.bioentityIdentifier = bioentityIdentifier;
        propertyMap.put("bioentity_identifier", bioentityIdentifier);
        propertyMap.put("species", experiment.getSpecies().getReferenceName());
        propertyMap.put("kingdom", experiment.getSpecies().getKingdom());
        propertyMap.put("experiment_accession", experiment.getAccession());
        //propertyMap.put("experimentType", experiment.getType().getDescription());
        propertyMap.put("experiment_type", experiment.getType().name().toUpperCase());

        propertyMap.put("conditions_search", conditionSearch);
    }

    public Map<String, Object> getProperties(){
        return propertyMap;
    }

    public abstract ImmutableList<BioentityPropertyName> getRelevantBioentityPropertyNames();
}
