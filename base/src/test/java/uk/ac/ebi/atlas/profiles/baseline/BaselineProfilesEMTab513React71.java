
package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineProfileDeserializer;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.OrderedFactorGroups;

import java.util.Iterator;

/* Returns the following gene profiles in the REACT_71 pathway (taken from E-MTAB-513)
 *
 * Gene ID	Gene Name	g1	g2	g3	g4	g5	g6	g7	g8	g9	g10	g11	g12	g13	g14	g15	g16
 * ENSG00000196652	ZKSCAN5	2	5	2	5	3	2	3	4	1	4	4	2	7	1	2	3
 * ENSG00000082258	CCNT2	3	9	5	11	8	6	7	10	7	10	10	5	10	5	4	9
 * ENSG00000047315	POLR2B	28	47	26	25	21	18	24	24	0	38	0	20	33	12	16	30
 * ENSG00000077312	SNRPA	41	19	6	20	27	14	7	74	33	0	11	26	19	7	23	24
 * ENSG00000198939	ZFP2	0.7	0.3	0.4	3	1	2	2	3	0.3	2	2	1	4	0.4	1	1
 * ENSG00000178665	ZNF713	0	0	0	2	0	0	2	0	0	0	0	0	3	0	0	0
 * ENSG00000161547	SRSF2	0	0	0	0	0	0	0	0.6	0	0	0	0	0	0	0	0
 *
 * Factor values: skeletal muscle, leukocyte, heart, ovary, lymph node, breast, brain, prostate, lung, thyroid, kidney, colon, testis, liver, adipose, adrenal gland
 *
 */
public class BaselineProfilesEMTab513React71 implements ObjectInputStream<BaselineProfile> {

    private final OrderedFactorGroups orderedFactorGroups;
    private Iterator<BaselineProfile> geneProfilesIterator;

    private static final String FACTOR_TYPE = "ORGANISM_PART";
    private static final String FACTOR_VALUES = "skeletal muscle, leukocyte, heart, ovary, lymph node, breast, brain, prostate, lung, thyroid, kidney, colon, testis, liver, adipose, adrenal gland";
    private static final String HEADER = "Gene ID\tGene Name\tg1\tg2\tg3\tg4\tg5\tg6\tg7\tg8\tg9\tg10\tg11\tg12\tg13\tg14\tg15\tg16\n";
    private static final String EXPRESSIONS = "ENSG00000196652\tZKSCAN5\t2\t5\t2\t5\t3\t2\t3\t4\t1\t4\t4\t2\t7\t1\t2\t3\n" +
            "ENSG00000082258\tCCNT2\t3\t9\t5\t11\t8\t6\t7\t10\t7\t10\t10\t5\t10\t5\t4\t9\n" +
            "ENSG00000047315\tPOLR2B\t28\t47\t26\t25\t21\t18\t24\t24\t0\t38\t0\t20\t33\t12\t16\t30\n" +
            "ENSG00000077312\tSNRPA\t41\t19\t6\t20\t27\t14\t7\t74\t33\t0\t11\t26\t19\t7\t23\t24\n" +
            "ENSG00000198939\tZFP2\t0.7\t0.3\t0.4\t3\t1\t2\t2\t3\t0.3\t2\t2\t1\t4\t0.4\t1\t1\n" +
            "ENSG00000178665\tZNF713\t0\t0\t0\t2\t0\t0\t2\t0\t0\t0\t0\t0\t3\t0\t0\t0\n" +
            "ENSG00000161547\tSRSF2\t0\t0\t0\t0\t0\t0\t0\t0.6\t0\t0\t0\t0\t0\t0\t0\t0";

    public BaselineProfilesEMTab513React71(double cutOff) {
        orderedFactorGroups = BaselineProfileDeserializer.orderedFactorGroupsOfSameFactorType(FACTOR_TYPE, FACTOR_VALUES);
        ImmutableList<BaselineProfile> profiles = BaselineProfileDeserializer.buildProfiles(FACTOR_TYPE, orderedFactorGroups, EXPRESSIONS, cutOff);
        geneProfilesIterator = profiles.iterator();
    }

    public OrderedFactorGroups getOrderedFactorGroups() {
        return orderedFactorGroups;
    }

    @Override
    public BaselineProfile readNext() {
        if (geneProfilesIterator.hasNext()) {
            return geneProfilesIterator.next();
        }
        return null;

    }

    @Override
    public void close() {
        // do nothing
    }

}
