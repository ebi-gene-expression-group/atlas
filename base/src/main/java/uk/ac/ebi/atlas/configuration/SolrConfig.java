package uk.ac.ebi.atlas.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.io.SolrClientCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:solr.properties")
public class SolrConfig {
    public static final SolrClientCache SOLR_CLIENT_CACHE = new SolrClientCache();

    @Bean
    public SolrClient solrClientBioentities(@Value("${solr.host}") String solrHost) {
        return new HttpSolrClient.Builder("http://" + solrHost + ":8983/solr/bioentities").build();
    }

    @Bean
    public SolrClient solrClientAnalytics(@Value("${solr.host}") String solrHost) {
        return new HttpSolrClient.Builder("http://" + solrHost + ":8983/solr/analytics").build();
    }

    @Bean
    public CloudSolrClient cloudSolrClient(@Value("${zk.host}") String zkHost) {
        return SOLR_CLIENT_CACHE.getCloudSolrClient(zkHost);
    }
}
