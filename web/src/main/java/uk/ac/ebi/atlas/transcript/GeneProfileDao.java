package uk.ac.ebi.atlas.transcript;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
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
import java.sql.SQLException;


@Named
@Scope("prototype")
public class GeneProfileDao {

    @Inject
    private DataSource dataSource;

    @Inject
    private LobHandler lobhandler;

    public TranscriptProfiles getTranscriptProfiles(String experimentAccession, String geneId) {

        JdbcTemplate template = new JdbcTemplate(dataSource);

        return template.queryForObject("select transcript_profiles where experiment_accession = ? and gene_id = ?",
                new String[]{experimentAccession, geneId},
                TranscriptProfiles.class);
    }

    public void addTranscriptProfiles(final String experimentAccession, final String geneId, final TranscriptProfiles profiles) throws IOException {

        JdbcTemplate template = new JdbcTemplate(dataSource);

        template.execute(
                "INSERT INTO experiment_transcripts VALUES (?, ?, ?)",
                new AbstractLobCreatingPreparedStatementCallback(lobhandler) {
                    protected void setValues(PreparedStatement ps, LobCreator lobCreator)
                            throws SQLException {

                        ps.setString(1, experimentAccession);
                        ps.setString(2, geneId);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                            objectOutputStream.writeObject(profiles);
                            objectOutputStream.flush();
                            lobCreator.setBlobAsBytes(ps, 3, byteArrayOutputStream.toByteArray());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
}
