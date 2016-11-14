package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.ArrayUtils;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.OrderedFactorGroups;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import static com.google.common.base.Preconditions.checkState;

public final class BaselineProfileDeserializer {

    private BaselineProfileDeserializer() {

    }

    /**
     * @param factorType eg: "organism part"
     * @param factorValues eg: "adipose, adrenal gland, brain, breast"
     * @param profileLines eg: "ENSG00000082258\tCCNT2\t3\t9\t5\t11\t8\n" +
     *                          "ENSG00000047315\tPOLR2B\t28\t47\t0\t25"
     */
    public static ImmutableList<BaselineProfile> buildProfiles(String factorType, String factorValues, String profileLines, double cutOff) {
        String normalizedFactorType = Factor.normalize(factorType);
        OrderedFactorGroups factorGroups = orderedFactorGroupsOfSameFactorType(normalizedFactorType, factorValues);
        return buildProfiles(normalizedFactorType, factorGroups, profileLines, cutOff);
    }

    public static ImmutableList<BaselineProfile> buildProfiles(String factorType, OrderedFactorGroups factorGroups, String expressions, double cutOff) {
        ImmutableList.Builder<BaselineProfile> builder = ImmutableList.builder();
        String[] expressionLines = expressions.split("\n");

        for (String expressionLine : expressionLines) {
            String[] cells = expressionLine.split("\t");

            String geneId = cells[0];
            String geneName = cells[1];
            BaselineProfile profile = new BaselineProfile(geneId, geneName);

            String[] levels = (String[]) ArrayUtils.subarray(cells, 2, cells.length);
            checkState(levels.length == factorGroups.size(), "Number of levels != number of factor groups");

            for (int i = 0; i < levels.length; i++) {
                String level = levels[i];
                BaselineExpression expression = new BaselineExpression(level, factorGroups.get(i));

                if (isAboveCutoff(expression, cutOff)) {
                    profile.add(factorType, expression);
                }
            }

            builder.add(profile);
        }

        return builder.build();
    }

    private static boolean isAboveCutoff(BaselineExpression expression, double cutOff) {
        return !expression.isKnown() || expression.isGreaterThan(cutOff);
    }

    public static OrderedFactorGroups orderedFactorGroupsOfSameFactorType(String factorType, String factorValues) {
        ImmutableList.Builder<FactorGroup> builder = ImmutableList.builder();
        String[] values = factorValues.split(",");

        for (String value : values) {
            String trimmedValue = value.trim();
            FactorSet factorSet = FactorSet.create(ImmutableMap.of(factorType, trimmedValue));
            builder.add(factorSet);
        }

        return new OrderedFactorGroups(builder.build());
    }

}
