package uk.ac.ebi.atlas.utils;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class UniProtClientIT {

    private UniProtClient subject;

    @Before
    public void initSubject() {
        RestTemplate restTemplate = new RestTemplate();

        subject = new UniProtClient(restTemplate);
    }

    @Test
    public void testFetchRectomeIds() throws Exception {
        Collection<String> ids = subject.fetchReactomeIds("Q8N349");
        assertThat(ids, containsInAnyOrder("REACT_111102"));
    }

}

