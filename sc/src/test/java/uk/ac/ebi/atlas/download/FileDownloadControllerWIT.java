package uk.ac.ebi.atlas.download;

import com.google.common.net.HttpHeaders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.download.ExperimentFileType;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class FileDownloadControllerWIT {

    private final String EXPERIMENT_ACCESSION = "E-GEOD-106540";
    private final String EXPERIMENT_DESIGN_FILE_NAME = "ExpDesign-{0}.tsv";
    private final String URL = "/experiment/{experimentAccession}/download";

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void validExperimentAccession() throws Exception {
        String expectedFileName = MessageFormat.format(EXPERIMENT_DESIGN_FILE_NAME, EXPERIMENT_ACCESSION);
        this.mockMvc.perform(get(URL, EXPERIMENT_ACCESSION)
                .param("fileType", ExperimentFileType.EXPERIMENT_DESIGN.getId()))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+expectedFileName))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN));
    }

    @Test
    public void invalidExperimentAccession() throws Exception {
        this.mockMvc.perform(get(URL, "FOO")
                .param("fileType", ExperimentFileType.EXPERIMENT_DESIGN.getId()))
                .andExpect(status().is(400))
                .andExpect(view().name("error-page"));
    }

    @Test
    public void invalidExperimentFileType() throws Exception {
        this.mockMvc.perform(get(URL, EXPERIMENT_ACCESSION)
                .param("fileType", "foo"))
                .andExpect(status().is(404))
                .andExpect(view().name("error-page"));
    }
}
