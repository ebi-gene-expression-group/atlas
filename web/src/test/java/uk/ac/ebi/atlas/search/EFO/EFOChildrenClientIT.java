package uk.ac.ebi.atlas.search.EFO;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class EFOChildrenClientIT {


    @Inject
    EFOChildrenClient subject;

    @Test
    public void cancer() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("cancer");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children.size(), greaterThan(1000));
    }

    @Test
    public void mus_musculus() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("Mus musculus");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(243));
    }

    @Test
    public void mus() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("Mus");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(243));
    }

    @Test
    public void musculus() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("musculus");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(136));
    }

    @Test
    public void mus_musculus_quoted() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("\"Mus musculus\"");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(136));
    }

    @Test
    public void wild_type() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("wild type");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children.size(), greaterThan(1000));
    }

    @Test
    public void Brachyolmia() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("Brachyolmia");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(9));
    }

    @Test
    public void Brachyolmia_wild() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("Brachyolmia wild");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(10));
    }

    @Test
    public void Brachyolmia_wild_quoted() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("\"Brachyolmia wild\"");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(0));
    }

    @Test
    public void wild() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("wild");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(1));
    }


    @Test
         public void wild_type_quoted() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("\"wild type\"");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(1));
    }


    @Test
    public void OR() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("OR");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(0));
    }


    @Test
    public void OR_wild_foobar() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("OR wild foobar_this_is_not_in_index");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(0));
    }

    @Test
    public void wild_OR_foobar() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("wild OR foobar_this_is_not_in_index");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(1));
    }
}