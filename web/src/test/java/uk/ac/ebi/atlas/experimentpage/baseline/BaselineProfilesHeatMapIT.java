package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Joiner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.LoadGeneIdsIntoRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptionsWrapperAsGeneSets;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineProfilesHeatMapIT {

    public static final String E_MTAB_513 = "E-MTAB-513";
    private static final Factor FACTOR_LEUKOCYTE = new Factor("ORGANISM_PART", "leukocyte");

    @Resource(name = "baselineProfilesHeatMap")
    private BaselineProfilesHeatMap subject;

    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;

    @Inject
    BaselineRequestContextBuilder baselineRequestContextBuilder;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    private BaselineRequestContext baselineRequestContext;

    @Inject
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Before
    public void initRequestContext() {
        populateRequestContext();
    }

    private void populateRequestContext() {
        BaselineExperiment baselineExperiment = baselineExperimentsCache.getExperiment(E_MTAB_513);

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        baselineRequestContext =
                baselineRequestContextBuilder.forExperiment(baselineExperiment)
                .withPreferences(requestPreferences)
                .build();
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true
    @Test
    public void eMTAB513_Specific() throws GenesNotFoundException {
        BaselineProfilesList profiles = subject.fetch(baselineRequestContext);

        assertThat(profiles.getTotalResultCount(), is(147));
        // System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));
        assertThat(profiles.extractGeneNames(), contains("ACTL7A", "TEX33", "SLC10A1", "DCAF4L2", "ABCG8", "PRMT8", "UBQLNL", "FAM47B", "CCNE2", "ZSCAN5B", "WFDC10A", "USP26", "SBK2", "LIPF", "SRSF2", "C17orf64", "SCN2A", "RP11-192H23.4", "MYOD1", "ESPL1", "ZNF713", "NOTUM", "CRISP3", "C1QL3", "GFI1", "MT-ATP6", "SLAMF6", "MFSD7", "PVRL4", "RTDR1", "ARHGAP8", "RAB27B", "TRPM2", "IL12RB2", "DAPL1", "RGS7BP", "PRSS16", "SRGAP3", "C1QTNF2", "NEDD8", "PMM2", "RGP1", "GLB1L2", "CCDC66", "RANBP17", "BICD1", "ZFP2", "NEBL", "COL15A1", "APOBR"));

    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on
    @Test
    public void eMTAB513_NotSpecific() throws GenesNotFoundException {
        setNotSpecific();
        //requestPreferences.setHeatmapMatrixSize(170);
        //populateRequestContext();
        BaselineProfilesList profiles = subject.fetch(baselineRequestContext);

        assertThat(profiles.getTotalResultCount(), is(147));
        System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));
        assertThat(profiles.extractGeneNames(), containsInAnyOrder("MT-ATP6", "TMSB10", "RTN4", "NEDD8", "ARPC5", "RAB13", "CTNNA1", "ATP1B3", "RP11-466P24.2", "CALU", "VPS4A", "PPT1", "VTI1B", "S1PR1", "AIDA", "ZCRB1", "IL13RA1", "ARHGAP1", "CBX1", "CRLS1", "BMI1", "PLEKHB2", "BOD1", "MAPRE2", "POLR2B", "NEBL", "SNRPA", "POLE3", "GFPT1", "B3GNT1", "GGPS1", "MRPL13", "DEPTOR", "VAPB", "COL15A1", "C6orf203", "GOLPH3L", "PTBP3", "TUBA1C", "SLK", "ASNS", "EMCN", "OCEL1", "RNF25", "RNF41", "INPP5D", "ERLIN1", "CRY2", "THOC3", "COL4A3BP"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true&queryFactorValues=leukocyte
    @Test
    public void eMTAB513_Specific_QueryFactorLeukocyte() throws GenesNotFoundException {
        setQueryFactor(FACTOR_LEUKOCYTE);

        BaselineProfilesList profiles = subject.fetch(baselineRequestContext);

        assertThat(profiles.getTotalResultCount(), is(20));
        assertThat(profiles.extractGeneNames(), contains("APOBR", "INPP5D", "PTBP3", "ZDHHC18", "PPT1", "ARPC5", "GFI1", "RP11-466P24.2", "INPP4A", "SNX27", "KDM4A", "POLR2B", "PLCG2", "SLAMF6", "COL4A3BP", "RAB27B", "FBXO38", "C6orf1", "TMSB10", "POLE3"));

    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on&queryFactorValues=leukocyte
    @Test
    public void eMTAB513_NotSpecific_QueryFactorLeukocyte() throws GenesNotFoundException {
        setNotSpecific();
        setQueryFactor(FACTOR_LEUKOCYTE);

        BaselineProfilesList profiles = subject.fetch(baselineRequestContext);

        assertThat(profiles.getTotalResultCount(), is(103));
        // System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));
        assertThat(profiles.extractGeneNames(), containsInAnyOrder("MT-ATP6", "TMSB10", "ARPC5", "RP11-466P24.2", "NEDD8", "RTN4", "PPT1", "INPP5D", "S1PR1", "PTBP3", "ATP1B3", "PLEKHB2", "POLR2B", "CTNNA1", "MAPRE2", "SLAMF6", "IL13RA1", "APOBR", "ZDHHC18", "ARHGAP1", "POLE3", "AIDA", "VPS4A", "COL4A3BP", "CBX1", "PLCG2", "GGPS1", "BMI1", "RNF41", "SNX27", "INPP4A", "SNRPA", "TUBA1C", "OCEL1", "ZCRB1", "VRK3", "VTI1B", "FBXO38", "RNF25", "CALU", "RAE1", "C6orf1", "MRPL13", "CRLS1", "SLK", "MSH6", "BET1", "KDM4A", "TERF2", "VAPB"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on&geneQuery=react_14797+react_19184+react_604+react_111102+react_111217+react_6900+react_71+react_116125+react_75774+react_6802+react_17015+react_22258+react_15518+react_115566+react_12627&geneSetMatch=true
    @Test
    public void eMTAB513_NotSpecific_MultipleGeneSets_Order() throws GenesNotFoundException {
        setNotSpecific();
        setGeneQuery("R-HSA-372790\tR-HSA-388396\tR-HSA-109582\tR-HSA-162582\tR-HSA-1430728\tR-HSA-168256\tR-HSA-74160\tR-HSA-1643685\tR-HSA-1280218\tR-HSA-168249\tR-HSA-392499\tR-HSA-556833\tR-HSA-382551\tR-HSA-1640170\tR-HSA-212436");

        BaselineProfilesList profiles = subject.fetch(asGeneSet(baselineRequestContext));

        assertThat(profiles.getTotalResultCount(), is(15));
        // System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));
        assertThat(profiles.extractGeneNames(), contains("R-HSA-1430728","R-HSA-168249","R-HSA-388396","R-HSA-162582","R-HSA-109582","R-HSA-372790","R-HSA-382551","R-HSA-168256","R-HSA-1643685","R-HSA-392499","R-HSA-1280218","R-HSA-556833","R-HSA-74160","R-HSA-1640170","R-HSA-212436"));
    }

    private BaselineProfileStreamOptions asGeneSet(BaselineRequestContext baselineRequestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.load(baselineRequestContext, baselineRequestContext.getFilteredBySpecies());
        return new BaselineProfileStreamOptionsWrapperAsGeneSets(baselineRequestContext);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true&geneQuery=react_14797+react_19184+react_604+react_111102+react_111217+react_6900+react_71+react_116125+react_75774+react_6802+react_17015+react_22258+react_15518+react_115566+react_12627&geneSetMatch=true
    @Test
    public void eMTAB513_Specific_MultipleGeneSets() throws GenesNotFoundException {
        setGeneQuery("R-HSA-372790\tR-HSA-388396\tR-HSA-109582\tR-HSA-162582\tR-HSA-1430728\tR-HSA-168256\tR-HSA-74160\tR-HSA-1643685\tR-HSA-1280218\tR-HSA-168249\tR-HSA-392499\tR-HSA-556833\tR-HSA-382551\tR-HSA-1640170\tR-HSA-212436");

        BaselineProfilesList profiles = subject.fetch(asGeneSet(baselineRequestContext));

        assertThat(profiles.getTotalResultCount(), is(15));
        // System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));
        assertThat(profiles.extractGeneNames(), containsInAnyOrder("R-HSA-372790", "R-HSA-388396", "R-HSA-109582", "R-HSA-162582", "R-HSA-1430728", "R-HSA-168256", "R-HSA-74160", "R-HSA-1643685", "R-HSA-1280218", "R-HSA-168249", "R-HSA-392499", "R-HSA-556833", "R-HSA-382551", "R-HSA-1640170", "R-HSA-212436"));
    }

    private void setGeneQuery(String geneQuery) {
        requestPreferences.setGeneQuery(GeneQuery.create(geneQuery));
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

    private void setQueryFactor(Factor factor) {
        requestPreferences.setQueryFactorType(factor.getType());
        requestPreferences.setQueryFactorValues(Collections.singleton(factor.getValue()));

        // set query factor on request context
        populateRequestContext();
    }

}
