package uk.ac.ebi.atlas.solr.cloud.search;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FacetStreamDaoBuilderTest {

    private FacetStreamDaoBuilder subject;

    @Before
    public void setUp() {
        subject = new FacetStreamDaoBuilder();
    }

    @Test
    public void blah() {
        assertThat(subject.build()).isInstanceOf(TupleStreamDao.class);
    }

}