package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;

import java.util.*;

public class BaselineExperiment extends Experiment<AssayGroup> {

    private final Map<AssayGroup, FactorGroup> experimentalFactors;



    BaselineExperiment(ExperimentType experimentType, String accession, Date lastUpdate,
                       String description, String displayName, String disclaimer, Species species,
                       boolean hasRData, Collection<String> pubMedIds, ExperimentDesign experimentDesign,
                       List<AssayGroup> assayGroups, List<String> dataProviderURL, List<String> dataProviderDescription,
                       List<String> alternativeViews, List<String> alternativeViewDescriptions, ExperimentDisplayDefaults experimentDisplayDefaults) {

        super(experimentType, accession, lastUpdate, displayName, description, disclaimer, hasRData, species,
              pubMedIds, experimentDesign, dataProviderURL, dataProviderDescription,
              alternativeViews, alternativeViewDescriptions, assayGroups, experimentDisplayDefaults);

        ImmutableMap.Builder<AssayGroup, FactorGroup> b = ImmutableMap.builder();
        for(AssayGroup assayGroup: assayGroups){
            b.put(assayGroup, experimentDesign.getFactors(assayGroup.getFirstAssayAccession()));
        }
        experimentalFactors = b.build();
    }

    public FactorGroup getFactors(AssayGroup assayGroup){
        return experimentalFactors.get(assayGroup);
    }

    @Override
    protected JsonObject propertiesForAssay(String runOrAssay) {
        boolean analysed = false;
        for(AssayGroup assayGroup : getDataColumnDescriptors()){
            if(assayGroup.assaysAnalyzedForThisDataColumn().contains(runOrAssay)){
                analysed = true;
                break;
            }
        }
        JsonObject result = new JsonObject();
        result.addProperty("analysed", analysed);
        return result;
    }

    @Override
    public JsonArray groupingsForHeatmap() {
        ExperimentDesign experimentDesign = getExperimentDesign();
        ExperimentDisplayDefaults experimentDisplayDefaults = getDisplayDefaults();

        DataColumnGroup.DataColumnGroupList dataColumnGroupList = new DataColumnGroup.DataColumnGroupList(experimentDisplayDefaults);

        //populate the keys in the order we want later
        for(String factorHeader: experimentDisplayDefaults.prescribedOrderOfFilters()){
            dataColumnGroupList.addDataColumnGroupIfNotPresent(factorHeader, true);
        }
        for(String factorHeader: experimentDesign.getFactorHeaders()){
            dataColumnGroupList.addDataColumnGroupIfNotPresent(factorHeader, true);
        }
        for(String sampleHeader: experimentDesign.getSampleHeaders()){
            dataColumnGroupList.addDataColumnGroupIfNotPresent(sampleHeader, false);
        }

        // add the information about which headers go to which categories
        for(DescribesDataColumns dataColumnDescriptor: getDataColumnDescriptors()){
            for(String assayAnalyzedForThisDataColumn : dataColumnDescriptor.assaysAnalyzedForThisDataColumn()){
                for(Factor factor : experimentDesign.getFactors(assayAnalyzedForThisDataColumn)){
                    dataColumnGroupList.addValueToGroupingInGroup(factor.getHeader(), factor.getValue(), dataColumnDescriptor);
                }
                for(SampleCharacteristic sampleCharacteristic
                        : experimentDesign.getSampleCharacteristics(assayAnalyzedForThisDataColumn)){
                    dataColumnGroupList.addValueToGroupingInGroup(sampleCharacteristic.header(), sampleCharacteristic.value(), dataColumnDescriptor);
                }
            }
        }
        return dataColumnGroupList.asJson();
    }
}
