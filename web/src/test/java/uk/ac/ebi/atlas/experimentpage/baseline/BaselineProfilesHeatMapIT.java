package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

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

    @Inject
    SolrQueryService solrQueryService;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    private BaselineRequestContext baselineRequestContext;

    @Before
    public void initRequestContext() throws ExecutionException {
        populateRequestContext();
    }

    private void populateRequestContext() throws ExecutionException{
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
        Optional<GeneQueryResponse> geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext
                (baselineRequestContext,baselineRequestContext.getFilteredBySpecies());
        BaselineProfilesList profiles = subject.fetch(baselineRequestContext,geneQueryResponse);

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
        Optional<GeneQueryResponse> geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext
                (baselineRequestContext,baselineRequestContext.getFilteredBySpecies());
        BaselineProfilesList profiles = subject.fetch(baselineRequestContext,geneQueryResponse);

        assertThat(profiles.getTotalResultCount(), is(147));
        System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));
        assertThat(profiles.extractGeneNames(), containsInAnyOrder("MT-ATP6", "TMSB10", "RTN4", "NEDD8", "ARPC5", "RAB13", "CTNNA1", "ATP1B3", "RP11-466P24.2", "CALU", "VPS4A", "PPT1", "VTI1B", "S1PR1", "AIDA", "ZCRB1", "IL13RA1", "ARHGAP1", "CBX1", "CRLS1", "BMI1", "PLEKHB2", "BOD1", "MAPRE2", "POLR2B", "NEBL", "SNRPA", "POLE3", "GFPT1", "B3GNT1", "GGPS1", "MRPL13", "DEPTOR", "VAPB", "COL15A1", "C6orf203", "GOLPH3L", "PTBP3", "TUBA1C", "SLK", "ASNS", "EMCN", "OCEL1", "RNF25", "RNF41", "INPP5D", "ERLIN1", "CRY2", "THOC3", "COL4A3BP"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true&queryFactorValues=leukocyte
    @Test
    public void eMTAB513_Specific_QueryFactorLeukocyte() throws GenesNotFoundException, ExecutionException  {
        setQueryFactor(FACTOR_LEUKOCYTE);

        Optional<GeneQueryResponse> geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext
                (baselineRequestContext,baselineRequestContext.getFilteredBySpecies());
        BaselineProfilesList profiles = subject.fetch(baselineRequestContext,geneQueryResponse);
        //System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");
        assertThat(profiles.getTotalResultCount(), is(10));
        assertThat(profiles.extractGeneNames(), contains("MYOD1", "DVL1", "IL12RB2", "VPS4A", "DEPTOR", "CC2D1B", "CRY2", "GPD2", "RPRD2", "TERF2"));

    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on&queryFactorValues=leukocyte
    @Test
    public void eMTAB513_NotSpecific_QueryFactorLeukocyte() throws GenesNotFoundException, ExecutionException {
        setNotSpecific();
        setQueryFactor(FACTOR_LEUKOCYTE);

        Optional<GeneQueryResponse> geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext
                (baselineRequestContext,baselineRequestContext.getFilteredBySpecies());
        BaselineProfilesList profiles = subject.fetch(baselineRequestContext,geneQueryResponse);

        assertThat(profiles.getTotalResultCount(), is(101));
        //System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");
        assertThat(profiles.extractGeneNames(), containsInAnyOrder("VPS4A", "TMSB10", "DEPTOR", "RTN4", "NEDD8", "CTNNA1", "SNRPA", "DVL1", "CALU", "VTI1B", "BOD1", "RAB13", "POLR2B", "CRY2", "VAPB", "ZCRB1", "CRLS1", "TERF2", "POLE3", "CC2D1B", "BMI1", "CBX1", "ARHGAP1", "AIDA", "GPD2", "IL13RA1", "RP11-466P24.2", "GSTZ1", "RPRD2", "SLK", "ATP1B3", "ASNS", "S1PR1", "OCEL1", "VRK3", "MAPRE2", "RNF41", "B3GNT1", "RNF25", "SNX27", "C6orf203", "AXIN2", "GFPT1", "ARPC5", "IL12RB2", "C6orf1", "GOLPH3L", "MSH6", "THOC6", "MRPL13"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on&geneQuery=react_14797+react_19184+react_604+react_111102+react_111217+react_6900+react_71+react_116125+react_75774+react_6802+react_17015+react_22258+react_15518+react_115566+react_12627&geneSetMatch=true
    @Test
    public void eMTAB513_NotSpecific_MultipleGeneSets_Order() throws GenesNotFoundException {
        setNotSpecific();
        setGeneQuery("R-HSA-372790\tR-HSA-388396\tR-HSA-109582\tR-HSA-162582\tR-HSA-1430728\tR-HSA-168256\tR-HSA-74160\tR-HSA-1643685\tR-HSA-1280218\tR-HSA-168249\tR-HSA-392499\tR-HSA-556833\tR-HSA-382551\tR-HSA-1640170\tR-HSA-212436");

        Optional<GeneQueryResponse> geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext
                (baselineRequestContext,baselineRequestContext.getFilteredBySpecies());
        BaselineProfilesList profiles = subject.fetch(baselineRequestContext,geneQueryResponse);

        assertThat(profiles.getTotalResultCount(), is(15));
        // System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));
        assertThat(profiles.extractGeneNames(), contains("R-HSA-1430728","R-HSA-168249","R-HSA-388396","R-HSA-162582","R-HSA-109582","R-HSA-372790","R-HSA-382551","R-HSA-168256","R-HSA-1643685","R-HSA-392499","R-HSA-556833","R-HSA-1280218","R-HSA-74160","R-HSA-1640170","R-HSA-212436"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true&geneQuery=react_14797+react_19184+react_604+react_111102+react_111217+react_6900+react_71+react_116125+react_75774+react_6802+react_17015+react_22258+react_15518+react_115566+react_12627&geneSetMatch=true
    @Test
    public void eMTAB513_Specific_MultipleGeneSets() throws GenesNotFoundException {
        setGeneQuery("R-HSA-372790\tR-HSA-388396\tR-HSA-109582\tR-HSA-162582\tR-HSA-1430728\tR-HSA-168256\tR-HSA-74160\tR-HSA-1643685\tR-HSA-1280218\tR-HSA-168249\tR-HSA-392499\tR-HSA-556833\tR-HSA-382551\tR-HSA-1640170\tR-HSA-212436");

        Optional<GeneQueryResponse> geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext
                (baselineRequestContext,baselineRequestContext.getFilteredBySpecies());
        BaselineProfilesList profiles = subject.fetch(baselineRequestContext,geneQueryResponse);

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

    private void setQueryFactor(Factor factor) throws ExecutionException {
        requestPreferences.setQueryFactorType(factor.getType());
        requestPreferences.setQueryFactorValues(Collections.singleton(factor.getValue()));

        // set query factor on request context
        populateRequestContext();
    }

}
