package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.velocity.util.StringUtils;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExperimentalFactorsFactory {

    public ExperimentalFactors createExperimentalFactors(String experimentAccession, ExperimentDesign
            experimentDesign, BaselineExperimentConfiguration factorsConfig,
                                                         AssayGroups assayGroups, String[] orderedAssayGroupIds, boolean orderCurated) {
        String defaultQueryFactorType = factorsConfig.getDefaultQueryFactorType();
        Set<Factor> defaultFilterFactors = factorsConfig.getDefaultFilterFactors();
        Set<String> requiredFactorTypes = getRequiredFactorTypes(defaultQueryFactorType, defaultFilterFactors);

        Map<String, String> factorNamesByType = getFactorDisplayNameByType(experimentDesign.getFactorHeaders(), requiredFactorTypes);

        List<FactorGroup> orderedFactorGroups = extractOrderedFactorGroups(experimentAccession, orderedAssayGroupIds, assayGroups, experimentDesign);
        Map<String, FactorGroup> orderedFactorGroupsByAssayGroup = extractOrderedFactorGroupsByAssayGroup(orderedAssayGroupIds, assayGroups, experimentDesign);

        ExperimentalFactorsBuilder b= new ExperimentalFactorsBuilder()
                .withOrderedFactorGroups(orderedFactorGroups)
                .withOrderedFactorGroupsByAssayGroupId(orderedFactorGroupsByAssayGroup)
                .withMenuFilterFactorTypes(factorsConfig.getMenuFilterFactorTypes())
                .withFactorNamesByType(factorNamesByType)
                .withDefaultQueryType(factorsConfig.getDefaultQueryFactorType())
                .withDefaultFilterFactors(defaultFilterFactors);

        return orderCurated ? b.createFromXML() : b.create();
    }

    Set<String> getRequiredFactorTypes(String defaultQueryFactorType, Set<Factor> defaultFilterFactors) {
        Set<String> requiredFactorTypes = Sets.newHashSet(defaultQueryFactorType);

        for (Factor defaultFilterFactor : defaultFilterFactors) {
            requiredFactorTypes.add(defaultFilterFactor.getType());
        }
        return requiredFactorTypes;
    }

    protected Map<String, String> getFactorDisplayNameByType(SortedSet<String> factorHeaders, Set<String> requiredFactorTypes) {
        Map<String, String> factorDisplayNameByType = Maps.newHashMap();

        for (String factorType : factorHeaders) {
            String normalizedFactorType = Factor.normalize(factorType);
            if (requiredFactorTypes.contains(normalizedFactorType)) {
                factorDisplayNameByType.put(normalizedFactorType, prettifyFactorType(factorType));
            }
        }
        return factorDisplayNameByType;
    }

    //TODO: move this into ExperimentFactorsLoader
    protected String prettifyFactorType(String factorType) {
        StringBuilder result = new StringBuilder();
        String[] split = factorType.replaceAll("_", " ").split(" ");
        boolean firstTokenCapitalized = false;
        for (String token : split) {
            int nbUpperCase = countUpperCaseLetters(token);
            if (nbUpperCase > 1) {
                result.append(token);
            } else {
                token = token.toLowerCase();

                if (!firstTokenCapitalized) {
                    token = StringUtils.capitalizeFirstLetter(token);
                    firstTokenCapitalized = true;
                }
                result.append(token);
            }
            result.append(" ");
        }

        return result.toString().trim();
    }

    protected int countUpperCaseLetters(String token) {
        int nbUpperCase = 0;
        for (int i = 0; i < token.length(); i++) {
            if (Character.isUpperCase(token.charAt(i))) {
                nbUpperCase++;
            }
        }
        return nbUpperCase;
    }

    List<FactorGroup> extractOrderedFactorGroups(String experimentAccession, String[] orderedAssayGroupIds, final AssayGroups assayGroups, ExperimentDesign experimentDesign) {

        List<FactorGroup> factorGroups = Lists.newArrayList();

        for (String groupId : orderedAssayGroupIds) {
            AssayGroup assayGroup = assayGroups.getAssayGroup(groupId);

            checkNotNull(assayGroup, String.format("%s: No assay group \"%s\"", experimentAccession, groupId));

            FactorGroup factorGroup = experimentDesign.getFactors(assayGroup.getFirstAssayAccession());
            checkNotNull(factorGroup, String.format("%s: Factors not found for \"%s\" - \"%s\"", experimentAccession,
                    groupId, assayGroup.getFirstAssayAccession()));
            factorGroups.add(factorGroup);

        }
        return factorGroups;

    }

    Map<String, FactorGroup> extractOrderedFactorGroupsByAssayGroup(String[] orderedAssayGroupIds, final AssayGroups assayGroups, ExperimentDesign experimentDesign) {

        Map<String, FactorGroup> factorGroups = Maps.newLinkedHashMap();

        for (String groupId : orderedAssayGroupIds) {
            AssayGroup assayGroup = assayGroups.getAssayGroup(groupId);

            FactorGroup factorGroup = experimentDesign.getFactors(assayGroup.getFirstAssayAccession());
            factorGroups.put(groupId, factorGroup);

        }
        return factorGroups;

    }
}
