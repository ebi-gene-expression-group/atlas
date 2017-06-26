package uk.ac.ebi.atlas.markergenes;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class MarkerGeneProfileTest {

    @Test(expected = IllegalArgumentException.class)
    public void emptyList() throws Exception {
        MarkerGeneProfile.create(ImmutableList.of());
    }

    @Test(expected = IllegalArgumentException.class)
    public void multipleExperimentAccessions() throws Exception {
        MarkerGeneProfile.create(
                ImmutableList.of(
                        MarkerGeneDao.MarkerGeneDto.create("E-MTAB-4388", 2, 0, 0.999),
                        MarkerGeneDao.MarkerGeneDto.create("E-MTAB-5061", 2, 0, 0.999)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void multiplePerplexities() throws Exception {
        MarkerGeneProfile.create(
                ImmutableList.of(
                        MarkerGeneDao.MarkerGeneDto.create("E-MTAB-4388", 2, 0, 0.999),
                        MarkerGeneDao.MarkerGeneDto.create("E-MTAB-4388", 3, 0, 0.999)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidPerplexityAndClusterId() throws Exception {
        MarkerGeneProfile.create(
                ImmutableList.of(
                        MarkerGeneDao.MarkerGeneDto.create("E-MTAB-4388", 2, 2, 0.999)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void duplicatePerplexityAndClusterId() throws Exception {
        MarkerGeneProfile.create(
                ImmutableList.of(
                        MarkerGeneDao.MarkerGeneDto.create("E-MTAB-4388", 2, 0, 0.989),
                        MarkerGeneDao.MarkerGeneDto.create("E-MTAB-4388", 2, 0, 0.999)));
    }

    @Test
    public void create() throws Exception {
        MarkerGeneProfile mgp = MarkerGeneProfile.create(
                ImmutableList.of(
                        MarkerGeneDao.MarkerGeneDto.create("E-MTAB-4388", 6, 2, 0.99),
                        MarkerGeneDao.MarkerGeneDto.create("E-MTAB-4388", 6, 3, 0.99),
                        MarkerGeneDao.MarkerGeneDto.create("E-MTAB-4388", 6, 4, 0.99)
                )
        );
        assertThat(mgp.clusters(), hasSize(3));
    }
}