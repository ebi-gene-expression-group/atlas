package uk.ac.ebi.atlas.transcript;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.utils.TsvReaderBuilder;

import java.io.IOException;
import java.text.MessageFormat;

public class TranscriptProfilesLoader {

    private TsvReaderBuilder tsvReaderBuilder;

    private GeneProfileDao geneProfileDao;

    @Value("#{configuration['experiment.transcripts.path.template']}")
    private String transcriptFileUrlTemplate;


    public TranscriptProfilesLoader(TsvReaderBuilder tsvReaderBuilder, GeneProfileDao geneProfileDao) {
        this.tsvReaderBuilder = tsvReaderBuilder;
        this.geneProfileDao = geneProfileDao;
    }

    public int loadTranscriptProfiles(String experimentAccession) {
        int count = 0;
        String fileURL = MessageFormat.format(transcriptFileUrlTemplate, experimentAccession);
        try (CSVReader csvReader = tsvReaderBuilder.build(fileURL)) {
            csvReader.readNext();


            String[] line;
            while((line = csvReader.readNext()) != null ) {
                TranscriptProfile transcriptProfile = createTranscriptProfile(line);
                String geneId  = getGeneId(line);
                geneProfileDao.addTranscriptProfiles(experimentAccession, geneId, transcriptProfile);
            }

        } catch (IOException e) {
            throw new IllegalStateException("Cannot read transcript profile file for: " + fileURL, e );
        }

        return count;
    }

    private String getGeneId(String[] line) {
        return line[0];
    }

    private TranscriptProfile createTranscriptProfile(String[] line) {
        String transcriptId = line[1];
        return null;
    }
}
