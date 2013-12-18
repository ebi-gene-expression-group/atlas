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
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class TranscriptProfilesLoader {

    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int TRANSCRIPT_ID_COLUMN_INDEX = 2;
    private static final int BATCH_SIZE = 1000;

    private String transcriptFileUrlTemplate;

    private CsvReaderFactory tsvReaderUtils;

    private TranscriptProfileDao transcriptProfileDao;

    @Inject
    public TranscriptProfilesLoader(CsvReaderFactory tsvReaderUtils, TranscriptProfileDao transcriptProfileDao,
                                    @Value("#{configuration['experiment.transcripts.path.template']}") String transcriptFileUrlTemplate) {
        this.tsvReaderUtils = tsvReaderUtils;
        this.transcriptProfileDao = transcriptProfileDao;
        this.transcriptFileUrlTemplate = transcriptFileUrlTemplate;
    }

    public int load(String experimentAccession) throws IOException {

        String fileURL = MessageFormat.format(transcriptFileUrlTemplate, experimentAccession);
        try (CSVReader csvReader = tsvReaderUtils.createTsvReader(fileURL)) {

            //skip header line
            csvReader.readNext();

            ImmutableList.Builder<TranscriptProfile> builder = ImmutableList.builder();

            int count = 1;
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                TranscriptProfile transcriptProfile = createTranscriptProfile(line);
                builder.add(transcriptProfile);
                if (count % BATCH_SIZE == 0) {
                    ImmutableList<TranscriptProfile> batch = builder.build();
                    transcriptProfileDao.loadTranscriptProfiles(experimentAccession, batch);
                    builder = ImmutableList.builder();
                }
                count++;
            }

            ImmutableList<TranscriptProfile> batch = builder.build();

            if (batch.size() > 0) {
                transcriptProfileDao.loadTranscriptProfiles(experimentAccession, batch);
            }

            return count;
        }

    }

    TranscriptProfile createTranscriptProfile(String[] line) {
        String geneId = line[GENE_ID_COLUMN_INDEX];
        String transcriptId = line[TRANSCRIPT_ID_COLUMN_INDEX];
        String[] expressionStrings = (String[]) ArrayUtils.subarray(line, 3, line.length);
        return new TranscriptProfile(geneId, transcriptId, ImmutableList.<String>builder().add(expressionStrings).build());
    }
}
