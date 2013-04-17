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
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@Named
@Scope("prototype")
public class GeneProfileDao {

    private static final Logger LOGGER = Logger.getLogger(GeneProfileDao.class);
    public static final String TRANSCRIPT_PROFILE_QUERY = "SELECT gene_id, transcript_id, transcript_expressions FROM experiment_transcripts WHERE experiment_accession = ? AND gene_id = ?";

    @Inject
    private DataSource dataSource;

    public Collection<TranscriptProfile> getTranscriptProfiles(String experimentAccession, String geneId) {

        JdbcTemplate template = new JdbcTemplate(dataSource);

        return template.query(TRANSCRIPT_PROFILE_QUERY,
                new String[]{experimentAccession, geneId},
                new TranscriptProfileRowMapper());
    }

    public void addTranscriptProfiles(String experimentAccession, List<TranscriptProfile> profiles) {

    }

    public void addTranscriptProfile(String experimentAccession, TranscriptProfile profile) {
        Map<String, Object> insertParameters = Maps.newHashMap();
        insertParameters.put("experiment_accession", experimentAccession);
        insertParameters.put("gene_id", profile.getGeneId());
        insertParameters.put("transcript_id", profile.getTranscriptId());
        List<Double> expressions = profile.getExpressions();
        insertParameters.put("transcript_expressions", expressions.toArray(new Double[expressions.size()]));
        new SimpleJdbcInsert(dataSource).withTableName("experiment_transcripts").execute(insertParameters);
    }

    public int deleteTranscriptProfilesForExperimentAndGene(String experimentAccession, String geneId) {
        JdbcTemplate delete = new JdbcTemplate(dataSource);
        return delete.update("DELETE FROM experiment_transcripts WHERE experiment_accession= ? AND gene_id = ?",
                new Object[]{experimentAccession, geneId});
    }

    public int deleteTranscriptProfilesForExperiment(String experimentAccession) {
        JdbcTemplate delete = new JdbcTemplate(dataSource);
        return delete.update("DELETE FROM experiment_transcripts WHERE experiment_accession= ?",
                new Object[]{experimentAccession});
    }
}
