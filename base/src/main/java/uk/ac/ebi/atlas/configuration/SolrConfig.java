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
    public SolrClient solrClientBioentities(@Value("${solr.host}") String solrHost,
                                            @Value("${solr.port}") String solrPort) {
        return new HttpSolrClient.Builder("http://" + solrHost + ":" + solrPort + "/solr/bioentities").build();
    }

    @Bean
    public SolrClient solrClientAnalytics(@Value("${solr.host}") String solrHost,
                                          @Value("${solr.port}") String solrPort) {
        return new HttpSolrClient.Builder("http://" + solrHost + ":" + solrPort + "/solr/analytics").build();
    }

    @Bean
    public CloudSolrClient cloudSolrClient(@Value("${zk.host}") String zkHost,
                                           @Value("${zk.port}") String zkPort) {
        return new CloudSolrClient.Builder().withZkHost(zkHost + ":" + zkPort).build();
    }
}
