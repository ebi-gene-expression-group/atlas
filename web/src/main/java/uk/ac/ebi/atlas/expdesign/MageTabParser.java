package uk.ac.ebi.atlas.expdesign;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.CharacteristicsAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.UnitAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Set;

public abstract class MageTabParser {

    private MageTabLimpopoUtils mageTabLimpopoUtils;

    private PropertyMergeService propertyMergeService;

    private MAGETABInvestigation investigation;

    @Inject
    public void setPropertyMergeService(PropertyMergeService propertyMergeService) {
        this.propertyMergeService = propertyMergeService;
    }

    @Inject
    protected void setMageTabLimpopoUtils(MageTabLimpopoUtils mageTabLimpopoUtils) {
        this.mageTabLimpopoUtils = mageTabLimpopoUtils;
    }

    public ExperimentDesign parse(String experimentAccession) throws MalformedURLException, ParseException, ExperimentDesignWritingException {

        ExperimentDesign experimentDesign = new ExperimentDesign();

        investigation = mageTabLimpopoUtils.parseInvestigation(experimentAccession);

        addCharacteristics(experimentDesign);
        addFactorValues(experimentDesign);

        addArrays(experimentDesign);

        return experimentDesign;
    }

    protected MAGETABInvestigation getInvestigation() {
        return investigation;
    }

    protected void addCharacteristics(ExperimentDesign experimentDesign) {
        for (String assay : extractAssays()) {
            HybridizationNode node = getNode(assay);
            Collection<SourceNode> sourceNodes = GraphUtils.findUpstreamNodes(node, SourceNode.class);
            if (sourceNodes.size() != 1) {
                throw new IllegalStateException("There is no one to one mapping between sdrfNode and sourceNode for sdrfNode: " + node);
            }

            SourceNode sourceNode = sourceNodes.iterator().next();
            for (CharacteristicsAttribute characteristicsAttribute : sourceNode.characteristics) {

                String value = cleanValueAndUnitIfNeeded(characteristicsAttribute.getNodeName(), characteristicsAttribute.unit);
                experimentDesign.putSample(assay, characteristicsAttribute.type, value);
            }

        }
    }

    // Set containing all factors for which values need doses merged into them
    private static final Set<String> FACTORS_NEEDING_DOSE = Sets.newHashSet();

    static {
        FACTORS_NEEDING_DOSE.add("compound");
        FACTORS_NEEDING_DOSE.add("irradiate");
    }

    private static final String DOSE = "dose";

    protected void addFactorValues(ExperimentDesign experimentDesign) {

        String compoundFactorValue = null;
        String compoundFactorType = null;

        for (String assay : extractAssays()) {

            HybridizationNode node = getNode(assay);
            for (FactorValueAttribute factor : node.factorValues) {

                String factorType = factor.type;
                String factorValue = cleanValueAndUnitIfNeeded(factor.getNodeName(), factor.unit);

                if (FACTORS_NEEDING_DOSE.contains(factor.type.toLowerCase())) {
                    compoundFactorType = factorType;
                    compoundFactorValue = factorValue;

                } else if (DOSE.equals(factor.type.toLowerCase())) {
                    if (StringUtils.isNotEmpty(compoundFactorValue) ) {

                        factorValue = Joiner.on(" ").join(compoundFactorValue, factorValue);
                        factorType = compoundFactorType;

                        compoundFactorType = null;
                        compoundFactorValue = null;
                    } else {
                        throw new IllegalStateException(DOSE + " : " + factorValue + " has no corresponding value for any of the following factors: " + FACTORS_NEEDING_DOSE);
                    }

                    experimentDesign.putFactor(assay, factorType, factorValue);
                }
            }

            if(StringUtils.isNotEmpty(compoundFactorType) && StringUtils.isNotEmpty(compoundFactorValue)) {
                experimentDesign.putFactor(assay, compoundFactorType, compoundFactorValue);
            }

        }
    }

    protected String cleanValueAndUnitIfNeeded(String value, UnitAttribute unit) {
        if (!StringUtils.isEmpty(value)) {
            value.replaceAll("( )+", " ").replaceAll("(_)+", "_").trim();
            if (unit != null) {
                if (StringUtils.isEmpty(unit.getAttributeType())) {
                    throw new IllegalStateException("Unable to find unit value for factor value: " + value);
                }
                value = propertyMergeService.mergeValueAndUnit(value, unit.getAttributeType());
            }
        }
        return value;
    }

    protected abstract Set<String> extractAssays();

    protected abstract HybridizationNode getNode(String assay);

    protected abstract void addArrays(ExperimentDesign experimentDesign);
//    {
//        return investigation.SDRF.getNode(assay, HybridizationNode.class);
//    }
}
