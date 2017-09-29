package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionAtlasExperimentCheckerTest {

    MockDataFileHub dataFileHub;

    static String accession = "E-MOCK-1";

    ExpressionAtlasExperimentChecker subject;

    @Before
    public void setUp() throws Exception{
        dataFileHub = MockDataFileHub.create();
        subject = new ExpressionAtlasExperimentChecker(dataFileHub, new ConfigurationTrader(dataFileHub));
    }

    @Test
    public void extractAssayGroupIdsProteomics() {
        String[] tsvFileHeader = "GeneID\tg1.SpectralCount\tg2.SpectralCount\tg3.SpectralCount\tg4.SpectralCount\tg5.SpectralCount\tg6.SpectralCount\tg7.SpectralCount\tg8.SpectralCount\tg9.SpectralCount\tg10.SpectralCount\tg11.SpectralCount\tg12.SpectralCount\tg13.SpectralCount\tg14.SpectralCount\tg15.SpectralCount\tg16.SpectralCount\tg17.SpectralCount\tg18.SpectralCount\tg19.SpectralCount\tg20.SpectralCount\tg21.SpectralCount\tg22.SpectralCount\tg23.SpectralCount\tg24.SpectralCount\tg25.SpectralCount\tg26.SpectralCount\tg27.SpectralCount\tg28.SpectralCount\tg29.SpectralCount\tg30.SpectralCount\tg1.WithInSampleAbundance\tg2.WithInSampleAbundance\tg3.WithInSampleAbundance\tg4.WithInSampleAbundance\tg5.WithInSampleAbundance\tg6.WithInSampleAbundance\tg7.WithInSampleAbundance\tg8.WithInSampleAbundance\tg9.WithInSampleAbundance\tg10.WithInSampleAbundance\tg11.WithInSampleAbundance\tg12.WithInSampleAbundance\tg13.WithInSampleAbundance\tg14.WithInSampleAbundance\tg15.WithInSampleAbundance\tg16.WithInSampleAbundance\tg17.WithInSampleAbundance\tg18.WithInSampleAbundance\tg19.WithInSampleAbundance\tg20.WithInSampleAbundance\tg21.WithInSampleAbundance\tg22.WithInSampleAbundance\tg23.WithInSampleAbundance\tg24.WithInSampleAbundance\tg25.WithInSampleAbundance\tg26.WithInSampleAbundance\tg27.WithInSampleAbundance\tg28.WithInSampleAbundance\tg29.WithInSampleAbundance\tg30.WithInSampleAbundance".split("\t");
        assertThat(subject.proteomicsIdsFromHeader(tsvFileHeader), is(new String[]{"g1", "g2",
                "g3", "g4", "g5",
                "g6", "g7", "g8", "g9",
                "g10", "g11", "g12", "g13", "g14", "g15", "g16", "g17", "g18", "g19", "g20", "g21", "g22", "g23", "g24", "g25", "g26", "g27", "g28", "g29", "g30"}));
    }


    Collection<String> configurationWithAssayGroups(Collection<String> assayGroupList){
        ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_BASELINE;
        ImmutableList.Builder<String> b = ImmutableList.builder();
        b.add(MessageFormat.format("<configuration experimentType=\"{0}\" r_data=\"1\">", experimentType.getDescription()),
                "<analytics>",
                "<assay_groups>");

        b.addAll(assayGroupList);

        b.add(
                "</assay_groups>",
                "</analytics>",
                "</configuration>"
        );
        return b.build();
    }

    void setup(String assayGroupXml, String[] fileHeader, BiConsumer<String, List<String[]>> addFile,
                            String[] fileHeader2, BiConsumer<String, List<String[]>> addFile2){
        dataFileHub.addConfigurationFile(accession, configurationWithAssayGroups(ImmutableList.of(
                assayGroupXml
        )));
        dataFileHub.addFactorsFile(accession, ImmutableList.of("<factors-definition>", "</factors-definition>"));
        addFile.accept(accession, ImmutableList.of(fileHeader));
        addFile2.accept(accession,ImmutableList.of(fileHeader2));

    }

    void setup(String assayGroupXml, String[] fileHeader, BiConsumer<String, List<String[]>> addFile){
        dataFileHub.addConfigurationFile(accession, configurationWithAssayGroups(ImmutableList.of(
                assayGroupXml
        )));
        dataFileHub.addFactorsFile(accession, ImmutableList.of("<factors-definition>", "</factors-definition>"));
        addFile.accept(accession, ImmutableList.of(fileHeader));

    }
    void assertPasses(String assayGroupXml, String[] fileHeader, BiConsumer<String, List<String[]>> addFile){
        setup(assayGroupXml, fileHeader, addFile);
        subject.checkRnaSeqBaselineFiles(accession);
    }

    void assertFails(String assayGroupXml, String[] fileHeader, BiConsumer<String, List<String[]>> addFile){
        setup(assayGroupXml, fileHeader, addFile);
        try{
            subject.checkRnaSeqBaselineFiles(accession);
            fail("Should fail with IllegalStateException");
        } catch (IllegalStateException e){
            //yum
        }
    }

    void assertPasses(String assayGroupXml, String[] fileHeader, BiConsumer<String, List<String[]>> addFile,
                      String[] fileHeader2, BiConsumer<String, List<String[]>> addFile2){
        setup(assayGroupXml, fileHeader, addFile, fileHeader2, addFile2);
        subject.checkRnaSeqBaselineFiles(accession);
    }

    void assertFails(String assayGroupXml, String[] fileHeader, BiConsumer<String, List<String[]>> addFile,
                     String[] fileHeader2, BiConsumer<String, List<String[]>> addFile2){
        setup(assayGroupXml, fileHeader, addFile, fileHeader2, addFile2);
        try{
            subject.checkRnaSeqBaselineFiles(accession);
            fail("Should fail with IllegalStateException");
        } catch (IllegalStateException e){
            //yum
        }
    }

    @Test
    public void baselineRnaSeqFileNeedsAtLeastOneDataFile(){
        assertFails("<assay_group id=\"g1\"><assay>A</assay></assay_group>", new String[]{}, (a,b)-> {});
    }




    void fileMatchesHeaderBasedOnAssayGroups(BiConsumer<String, List<String[]>> addFile){
        assertPasses("<assay_group id=\"g1\"><assay>A</assay></assay_group>", new String[]{"","", "g1"}, addFile);
        assertPasses("<assay_group id=\"g1\"><assay>A</assay></assay_group>" +
                "<assay_group id=\"g2\"><assay>B</assay></assay_group>", new String[]{"","", "g1", "g2"},addFile);
        assertPasses("<assay_group id=\"g1\"><assay>A</assay></assay_group>" +
                "<assay_group id=\"g2\"><assay>B</assay></assay_group>", new String[]{"","", "g2", "g1"},addFile);
        assertFails("<assay_group id=\"g1\"><assay>A</assay></assay_group>" +
                "<assay_group id=\"g2\"><assay>B</assay></assay_group>", new String[]{"","", "g1"}, addFile);
        assertFails("<assay_group id=\"g1\"><assay>A</assay></assay_group>", new String[]{"","", "g1", "g2"},addFile);
    }

    @Test
    public void baselineRnaSeqGeneExpressionFilesArePerAssayGroup1(){
        fileMatchesHeaderBasedOnAssayGroups(dataFileHub::addFpkmsExpressionFile);
    }

    @Test
    public void baselineRnaSeqGeneExpressionFilesArePerAssayGroup2(){
        fileMatchesHeaderBasedOnAssayGroups(dataFileHub::addTpmsExpressionFile);
    }

    @Test
    public void baselineRnaSeqGeneExpressionFilesArePerAssayGroup3(){
        fileMatchesHeaderBasedOnAssayGroups((a,b)-> {
            dataFileHub.addTpmsExpressionFile(a,b);
            dataFileHub.addFpkmsExpressionFile(a,b);
        });
    }

    @Test
    public void checkBaselineRnaSeqFileCheckTranscriptsFile(){
        assertPasses("<assay_group id=\"g1\"><assay>A</assay></assay_group>", new String[]{"","", "A"},dataFileHub::addTranscriptsTpmsExpressionFile,
                new String[]{"","", "g1"},dataFileHub::addTpmsExpressionFile);
        assertPasses("<assay_group id=\"g1\"><assay>A</assay></assay_group>" +
                "<assay_group id=\"g2\"><assay>B</assay></assay_group>", new String[]{"","", "A", "B"},dataFileHub::addTranscriptsTpmsExpressionFile,
                new String[]{"","", "g1", "g2"}, dataFileHub:: addTpmsExpressionFile);
        assertFails("<assay_group id=\"g1\"><assay>A</assay></assay_group>" +
                "<assay_group id=\"g2\"><assay>B</assay></assay_group>", new String[]{"","", "A"},
                dataFileHub::addTranscriptsTpmsExpressionFile, new String[]{"","", "g1", "g2"}, dataFileHub::addTpmsExpressionFile);
        assertFails("<assay_group id=\"g1\"><assay>A</assay></assay_group>", new String[]{"","", "A", "B"},
                dataFileHub::addTranscriptsTpmsExpressionFile,new String[]{"","", "g1"},dataFileHub::addTpmsExpressionFile);
    }


    void technicalReplicatesInOneAssayGroupPasses(List<String> assayTags,String [] header){
        assertPasses("<assay_group id=\"g1\">"+ Joiner.on("\n").join(assayTags)+"</assay_group>",
                ImmutableList.builder().add("","").addAll(Arrays.asList(header)).build().toArray(new String[]{})
                ,dataFileHub::addTranscriptsTpmsExpressionFile,
                new String[]{"","", "g1"},dataFileHub::addTpmsExpressionFile);
    }

    void technicalReplicatesInOneAssayGroupFails(List<String> assayTags,String [] header){
        assertFails("<assay_group id=\"g1\">"+ Joiner.on("\n").join(assayTags)+"</assay_group>",
                ImmutableList.builder().add("","").addAll(Arrays.asList(header)).build().toArray(new String[]{}),
                dataFileHub::addTranscriptsTpmsExpressionFile,
                new String[]{"","", "g1"},dataFileHub::addTpmsExpressionFile);
    }

    @Test
    public void technicalReplicatesGetVerified(){
        technicalReplicatesInOneAssayGroupPasses(ImmutableList.of(
                "<assay technical_replicate_id=\"t1\">A</assay>" ,
                "<assay technical_replicate_id=\"t1\">B</assay>"
        ), new String[]{"t1"});
        technicalReplicatesInOneAssayGroupFails(ImmutableList.of(
                "<assay technical_replicate_id=\"t1\">A</assay>" ,
                "<assay technical_replicate_id=\"t1\">B</assay>"
        ), new String[]{"A","B"});
        technicalReplicatesInOneAssayGroupPasses(ImmutableList.of(
                "<assay technical_replicate_id=\"t1\">A</assay>" ,
                "<assay technical_replicate_id=\"t1\">B</assay>",
                "<assay>C</assay>"
        ), new String[]{"t1", "C"});
        technicalReplicatesInOneAssayGroupPasses(ImmutableList.of(
                "<assay technical_replicate_id=\"t1\">A</assay>" ,
                "<assay technical_replicate_id=\"t1\">B</assay>",
                "<assay technical_replicate_id=\"t2\">C</assay>" ,
                "<assay technical_replicate_id=\"t2\">D</assay>",
                "<assay>E</assay>"
        ), new String[]{"t1", "t2", "E"});
        technicalReplicatesInOneAssayGroupPasses(ImmutableList.of(
                "<assay technical_replicate_id=\"t1\">A</assay>" ,
                "<assay>E</assay>",
                "<assay technical_replicate_id=\"t2\">C</assay>" ,
                "<assay technical_replicate_id=\"t1\">B</assay>",
                "<assay technical_replicate_id=\"t2\">D</assay>"
        ), new String[]{"t1", "t2", "E"});
    }


}