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

    public void addTranscriptProfiles(final String experimentAccession, final String geneId, TranscriptProfile profile) throws IOException {

        Map<String, Object> insertParameters = Maps.newHashMap();
        insertParameters.put("experiment_accession", experimentAccession);
        insertParameters.put("gene_id", geneId);
        insertParameters.put("transcript_profile", profile.toJson());
        new SimpleJdbcInsert(dataSource).withTableName("experiment_transcripts").execute(insertParameters);
    }
}
