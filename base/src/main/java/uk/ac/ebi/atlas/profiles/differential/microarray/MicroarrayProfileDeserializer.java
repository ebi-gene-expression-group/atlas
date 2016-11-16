package uk.ac.ebi.atlas.profiles.differential.microarray;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialTsvFileParsingUtil;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public class MicroarrayProfileDeserializer {

    private static final int GENE_ID_INDEX = 0;
    private static final int GENE_NAME_INDEX = 1;
    private static final int DESIGN_ELEMENT_INDEX = 2;
    private static final int FIRST_CONTRAST_INDEX = 3;
    private final List<Contrast> contrasts;
    private final MicroarrayProfileReusableBuilder profileBuilder;

    public MicroarrayProfileDeserializer(List<Contrast> contrasts, Predicate<DifferentialExpression> expressionFilter) {
        this.contrasts = contrasts;
        this.profileBuilder = new MicroarrayProfileReusableBuilder(expressionFilter);
    }

    /*
     * @param line eg: "FBgn0019828	dj	1627959_a_at	6.4460598240872E-6	-1.68509426666667	-20.5528971215852"
     * @return no profile if no expressions match the expressionFilter, otherwise a profile
     */
    public Optional<MicroarrayProfile> create(String line) {
        String[] elements = line.split("\t");
        String geneId = elements[GENE_ID_INDEX];
        String geneName = elements[GENE_NAME_INDEX];
        String designElementName = elements[DESIGN_ELEMENT_INDEX];
        profileBuilder.beginNewInstance(geneId, geneName, designElementName);

        String[] contrastAnalyticsArray = (String[]) ArrayUtils.subarray(elements, FIRST_CONTRAST_INDEX, elements.length);
        ImmutableList<String> contrastAnalyticsList = ImmutableList.<String>builder().add(contrastAnalyticsArray).build();
        UnmodifiableIterator<String> contrastAnalytics = contrastAnalyticsList.iterator();

        for (Contrast contrast: contrasts) {

            String contrastId = contrast.getId();
            checkState(contrastAnalytics.hasNext(), String.format("missing p-value for gene %s, contrast %s", geneName, contrastId));
            String pValueString = contrastAnalytics.next();

            checkState(contrastAnalytics.hasNext(), String.format("missing t-statistic for gene %s, contrast %s", geneName, contrastId));
            String tStatisticString = contrastAnalytics.next();

            checkState(contrastAnalytics.hasNext(), String.format("missing log2foldchange for gene %s, contrast %s", geneName, contrastId));
            String foldChangeString = contrastAnalytics.next();

            if (!("NA".equalsIgnoreCase(pValueString) || "NA".equalsIgnoreCase(tStatisticString) || "NA".equalsIgnoreCase(foldChangeString))) {
                double pValue = DifferentialTsvFileParsingUtil.parseDouble(pValueString);
                double tStatistic = DifferentialTsvFileParsingUtil.parseDouble(tStatisticString);
                double foldChange = DifferentialTsvFileParsingUtil.parseDouble(foldChangeString);

                MicroarrayExpression expression = new MicroarrayExpression(pValue, foldChange, tStatistic, contrast);
                profileBuilder.addExpression(expression);
            }

        }

        MicroarrayProfile profile = profileBuilder.create();
        return profile.isEmpty() ? Optional.<MicroarrayProfile>absent() : Optional.of(profile);
    }

    public ImmutableList<MicroarrayProfile> create(String[] lines) {
        ImmutableList.Builder<MicroarrayProfile> builder = ImmutableList.builder();

        for (String line : lines) {
            Optional<MicroarrayProfile> microarrayProfileOptional = create(line);
            if (microarrayProfileOptional.isPresent()) {
                builder.add(microarrayProfileOptional.get());
            }
        }

        return builder.build();
    }

}
