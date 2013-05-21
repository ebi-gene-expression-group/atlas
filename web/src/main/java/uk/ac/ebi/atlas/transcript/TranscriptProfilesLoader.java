/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.transcript;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Named
@Scope("prototype")
public class TranscriptProfilesLoader {

    private static final Logger LOGGER = Logger.getLogger(TranscriptProfilesLoader.class);
    private static final int GENE_ID_COLUMN_INDEX = 0;
    public static final int TRANSCRIPT_ID_COLUMN_INDEX = 1;
    public static final int BATCH_SIZE = 1000;

    private String transcriptFileUrlTemplate;

    private TsvReaderUtils tsvReaderUtils;

    private GeneProfileDao geneProfileDao;

    @Inject
    public TranscriptProfilesLoader(TsvReaderUtils tsvReaderUtils, GeneProfileDao geneProfileDao,
                                    @Value("#{configuration['experiment.transcripts.path.template']}") String transcriptFileUrlTemplate) {
        this.tsvReaderUtils = tsvReaderUtils;
        this.geneProfileDao = geneProfileDao;
        this.transcriptFileUrlTemplate = transcriptFileUrlTemplate;
    }

    public int load(String experimentAccession) {
        geneProfileDao.deleteTranscriptProfilesForExperiment(experimentAccession);

        String fileURL = MessageFormat.format(transcriptFileUrlTemplate, experimentAccession);
        try (CSVReader csvReader = tsvReaderUtils.build(fileURL)) {

            //skip header line
            csvReader.readNext();

            List<TranscriptProfile> transcriptProfilesBatch = new ArrayList<>(BATCH_SIZE);

            int count = 0;
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                TranscriptProfile transcriptProfile = createTranscriptProfile(line);
                transcriptProfilesBatch.add(transcriptProfile);
                if (count % BATCH_SIZE == 0) {
                    geneProfileDao.addTranscriptProfiles(experimentAccession, transcriptProfilesBatch);
                    transcriptProfilesBatch.clear();
                }
                count++;
            }
            if (transcriptProfilesBatch.size() > 0) {
                geneProfileDao.addTranscriptProfiles(experimentAccession, transcriptProfilesBatch);
            }

            return count;

        } catch (IOException e) {
            LOGGER.error("Exception reading transcripts profile file: " + fileURL);
            throw new IllegalStateException(e.getMessage(), e);
        }

    }

    TranscriptProfile createTranscriptProfile(String[] line) {
        String geneId = line[GENE_ID_COLUMN_INDEX];
        String transcriptId = line[TRANSCRIPT_ID_COLUMN_INDEX];
        String[] expressionStrings = (String[]) ArrayUtils.subarray(line, 2, line.length);
        List<Double> expressionLevels = Lists.newArrayList();
        for (String expressionString : expressionStrings) {
            expressionLevels.add(Double.parseDouble(expressionString));
        }
        return new TranscriptProfile(geneId, transcriptId, expressionLevels);
    }
}
