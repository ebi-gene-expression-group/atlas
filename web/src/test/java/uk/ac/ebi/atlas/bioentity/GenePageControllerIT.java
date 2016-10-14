package uk.ac.ebi.atlas.bioentity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class GenePageControllerIT {

    @Inject
    GenePageController subject;




    @Test
    public void bioentityProperties() throws Exception {


        bioentityProperties("ENSG00000005801", 8); //zinc finger
        bioentityProperties("Sb01g008360");

    }

    private void bioentityProperties(String bioentityIdentifier){
        bioentityProperties(bioentityIdentifier,3);
    }

    private void bioentityProperties(String bioentityIdentifier, int expectedMinimalSize){
        Model model = new BindingAwareModelMap();
        subject.showGenePage(bioentityIdentifier,model);

        JsonArray bioentityProperties = new Gson().fromJson((String) model.asMap().get("bioentityProperties"), JsonArray.class);

        assertTrue(bioentityIdentifier+" should have properties" , bioentityProperties.size()>=expectedMinimalSize);
    }
}