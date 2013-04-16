package uk.ac.ebi.atlas.transcript;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfiles;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


@Named
@Scope("prototype")
public class GeneProfileDao {

    private static final Logger LOGGER = Logger.getLogger(GeneProfileDao.class);

    @Inject
    private DataSource dataSource;

    @Inject
    private LobHandler lobhandler;

    public TranscriptProfiles getTranscriptProfiles(String experimentAccession, String geneId) {

        JdbcTemplate template = new JdbcTemplate(dataSource);

        return template.queryForObject("select transcript_profiles from experiment_transcripts where experiment_accession = ? and gene_id = ?",
                    new String[]{experimentAccession, geneId},
                    new RowMapper<TranscriptProfiles>() {
                        public TranscriptProfiles mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return TranscriptProfiles.fromJson(rs.getString("transcript_profiles"));
                        }
                    });
        }

    public void addTranscriptProfiles(final String experimentAccession, final String geneId, final TranscriptProfiles profiles) throws IOException {

        Map<String, Object> insertParameters = Maps.newHashMap();
        insertParameters.put("experiment_accession", experimentAccession);
        insertParameters.put("gene_id", geneId);
        insertParameters.put("transcript_profiles", profiles.toJson());
        new SimpleJdbcInsert(dataSource).withTableName("experiment_transcripts").execute(insertParameters);
    }
}
