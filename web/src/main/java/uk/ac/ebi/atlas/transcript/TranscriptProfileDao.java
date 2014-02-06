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

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Named
@Scope("prototype")
public class TranscriptProfileDao {
    private static final Logger LOGGER = Logger.getLogger(TranscriptProfileDao.class);

    private static final String TRANSCRIPT_PROFILE_QUERY = "SELECT GENE_IDENTIFIER, TRANSCRIPT_IDENTIFIER, TRANSCRIPT_EXPRESSIONS " +
            "FROM RNASEQ_BSLN_TRANSCRIPTS WHERE EXPERIMENT = ? AND GENE_IDENTIFIER = ? AND ISACTIVE='T'";

    private JdbcTemplate jdbcTemplate;

    @Inject
    public TranscriptProfileDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<TranscriptProfile> findTranscriptProfiles(String experimentAccession, String geneId) {
        LOGGER.debug("<findTranscriptProfiles> experimentAccession = " + experimentAccession + ", geneId = " + geneId);

        return jdbcTemplate.query(TRANSCRIPT_PROFILE_QUERY,
                new String[]{experimentAccession, geneId},
                new TranscriptProfileRowMapper());
    }

}
