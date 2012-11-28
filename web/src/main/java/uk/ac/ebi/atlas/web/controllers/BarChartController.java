package uk.ac.ebi.atlas.web.controllers;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.TreeMap;

@Controller
@Scope("request")
public class BarChartController {

    @RequestMapping(value = "/json/gene-by-cutoff/expMap.json", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getMap() {
        TreeMap<Double, String> chartData = new TreeMap<>();

        chartData.put(0d, "24908");
        chartData.put(0.1, "23690");
        chartData.put(0.2, "23014");
        chartData.put(0.3, "22478");
        chartData.put(0.4, "22094");
        chartData.put(0.5, "21790");
        chartData.put(0.6, "21504");
        chartData.put(0.7, "21280");
        chartData.put(0.8, "21070");
        chartData.put(0.9, "20267");
        chartData.put(1d, "19147");
        chartData.put(2d, "18297");
        chartData.put(3d, "17559");
        chartData.put(4d, "16934");
        chartData.put(5d, "16311");
        chartData.put(6d, "15763");
        chartData.put(7d, "15252");
        chartData.put(8d, "14756");
        chartData.put(9d, "14261");
        chartData.put(10d, "10555");
        chartData.put(20d, "8283");
        chartData.put(30d, "6793");
        chartData.put(40d, "5830");
        chartData.put(50d, "5068");
        chartData.put(60d, "4521");
        chartData.put(70d, "4074");
        chartData.put(80d, "3724");
        chartData.put(90d, "3451");
        chartData.put(100d, "2038");
        chartData.put(200d, "1493");
        chartData.put(300d, "1204");
        chartData.put(400d, "985");
        chartData.put(500d, "844");
        chartData.put(600d, "751");
        chartData.put(700d, "681");
        chartData.put(800d, "627");
        chartData.put(900d, "568");
        chartData.put(1000d, "315");
        chartData.put(2000d, "229");
        chartData.put(3000d, "195");
        chartData.put(4000d, "166");
        chartData.put(5000d, "144");
        chartData.put(6000d, "129");
        chartData.put(7000d, "120");
        chartData.put(8000d, "115");
        chartData.put(9000d, "107");
        chartData.put(10000d, "76");

        Gson gson = new Gson();

        return gson.toJson(chartData, TreeMap.class);

    }

}
