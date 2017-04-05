package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.LinkedListMultimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.species.Species;

import java.util.*;

public class BaselineExperiment extends Experiment<AssayGroup> {

    private ExperimentalFactors experimentalFactors;

    BaselineExperiment(ExperimentType experimentType, String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description, String displayName, String disclaimer, Species species,
                       boolean hasRData, Collection<String> pubMedIds, ExperimentDesign experimentDesign,
                       List<AssayGroup> assayGroups, List<String> dataProviderURL, List<String> dataProviderDescription,
                       List<String> alternativeViews, List<String> alternativeViewDescriptions, ExperimentDisplayDefaults experimentDisplayDefaults) {

        super(experimentType, accession, lastUpdate, displayName, description, disclaimer, hasRData, species,
              pubMedIds, experimentDesign, dataProviderURL, dataProviderDescription,
              alternativeViews, alternativeViewDescriptions, assayGroups, experimentDisplayDefaults);

        this.experimentalFactors = experimentalFactors;
    }

    public ExperimentalFactors getExperimentalFactors() {
        return experimentalFactors;
    }

    public SortedSet<Factor> getAssayGroupFactors(Collection<String> assayGroupIds, String factorType) {
        return getExperimentalFactors().getFactors(assayGroupIds, factorType);
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

        LinkedListMultimap<String, LinkedListMultimap<String, String>> filtersByType = LinkedListMultimap.create();

        //populate the keys in the order we want later (Linked map preserves insertion order)
        for(String factorHeader: experimentDisplayDefaults.prescribedOrderOfFilters()){
            filtersByType.put(Factor.normalize(factorHeader), LinkedListMultimap.<String, String>create());
        }
        for(String factorHeader: experimentDesign.getFactorHeaders()){
            if(! experimentDisplayDefaults.prescribedOrderOfFilters().contains(Factor.normalize(factorHeader))){
                filtersByType.put(Factor.normalize(factorHeader), LinkedListMultimap.<String, String>create());
            }
        }
        for(String sampleHeader: experimentDesign.getSampleHeaders()){
            if(! experimentDesign.getFactorHeaders().contains(sampleHeader)){
                filtersByType.put(Factor.normalize(sampleHeader), LinkedListMultimap.<String, String>create());
            }
        }

        // add the information about which headers go to which categories
        for(DescribesDataColumns dataColumnDescriptor: getDataColumnDescriptors()){
            for(String assayAnalyzedForThisDataColumn : dataColumnDescriptor.assaysAnalyzedForThisDataColumn()){
                for(Factor factor : experimentDesign.getFactors(assayAnalyzedForThisDataColumn)){
                    filtersByType.get(Factor.normalize(factor.getHeader())).get(0)
                            .put(factor.getValue(), dataColumnDescriptor.getId());
                }
                for(SampleCharacteristic sampleCharacteristic
                        : experimentDesign.getSampleCharacteristics(assayAnalyzedForThisDataColumn)){
                    if(filtersByType.containsKey(Factor.normalize(sampleCharacteristic.header()))){
                        filtersByType.get(Factor.normalize(sampleCharacteristic.header())).get(0)
                                .put(sampleCharacteristic.value(), dataColumnDescriptor.getId());
                    }
                }
            }
        }
        JsonArray result = new JsonArray();
        for(Map.Entry<String, LinkedListMultimap<String, String>> e : filtersByType.entries()){
            result.add(groupForFilterType(e.getKey(), experimentDisplayDefaults.defaultFilterValuesForFactor(e.getKey
                    ()), e.getValue().asMap()));
        }


        return result;
    }
}
