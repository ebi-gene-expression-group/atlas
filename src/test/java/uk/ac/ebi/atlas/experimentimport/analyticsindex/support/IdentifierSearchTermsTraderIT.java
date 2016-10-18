package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class IdentifierSearchTermsTraderIT {


    @Inject
    IdentifierSearchTermsTrader subject;

    @Test
    public void testGetBioentityIdToIdentifierSearchMap() throws Exception {

        Map<String,String> result = subject.getBioentityIdToIdentifierSearchMap("E-MTAB-3726");

        assertThat(result.size(), greaterThan(0));

        for(Map.Entry<String,String> e: result.entrySet()) {
            Map<String, Collection<String>> x = identifierSearchAsMap(e.getValue());

            Set<String> bioentityIdentifiersWithoutSymbols = new HashSet<>();


            if(x.get("symbol") == null || x.get("symbol").size()==0){
                bioentityIdentifiersWithoutSymbols.add(e.getKey());
            } else if(x.get("symbol").size()>1){
                fail("Ambiguous symbols "+x.get("symbol") +" for accession "+ e.getKey());
            }
            assertThat(bioentityIdentifiersWithoutSymbols.size(), lessThan(10));

        }
        //TODO interesting stuff potentially
        //One gene is failing this, when you run it for all experiments!: TRAES3BF003300090CFD_g
        //Also: ENSACAG00000000328 for E-MTAB-3727
        //Also: ENSXETG00000000235 for E-MTAB-3726


    }

    private Map<String, Collection<String>> identifierSearchAsMap(String v){
        Multimap<String, String> map = LinkedListMultimap.create();
        Matcher m = Pattern.compile("(\\w+):\\{([^\\}]+)\\}").matcher(v);
        while (m.find()) {
            map.put(m.group(1), m.group(2));
        }
        return map.asMap();
    }
}