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

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;


@Named
@Scope("prototype")
public class GeneProfileDao {

    private static final Logger LOGGER = Logger.getLogger(GeneProfileDao.class);

    @Inject
    private DataSource dataSource;


    public Collection<TranscriptProfile> getTranscriptProfiles(String experimentAccession, String geneId) {

        JdbcTemplate template = new JdbcTemplate(dataSource);

        return template.query("select transcript_profile from experiment_transcripts where experiment_accession = ? and gene_id = ?",
                new String[]{experimentAccession, geneId},
                new RowMapper<TranscriptProfile>() {
                    public TranscriptProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return TranscriptProfile.fromJson(rs.getString("transcript_profile"));
                    }
                });
    }

    public void addTranscriptProfile(final String experimentAccession, final String geneId, TranscriptProfile profile) throws IOException {

        Map<String, Object> insertParameters = Maps.newHashMap();
        insertParameters.put("experiment_accession", experimentAccession);
        insertParameters.put("gene_id", geneId);
        insertParameters.put("transcript_profile", profile.toJson());
        new SimpleJdbcInsert(dataSource).withTableName("experiment_transcripts").execute(insertParameters);
    }
}
