package uk.ac.ebi.atlas.web.controllers;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@Controller
@Scope("request")
public class BarChartController {

    @RequestMapping(value = "/json/gene-by-cutoff/exp.json", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<String[]> getAll() {
        List<String[]> result = new ArrayList<>();

        String[] strings1 = {"0", "40851"};
        String[] strings2 = {"1", "37809"};
        result = Arrays.asList(strings1, strings2
//                ,
//                {"2", "35643"},
//                {"3", "34032"},
//                {"4", "32669"},
//                {"5", "31466"},
//                {"6", "30606"}
        );

        return result;


//        [2, 35643], [3, 34032], [4, 32669], [5, 31466], [6, 30606],
//        [7, 29825], [8, 29172], [9, 28563], [10, 26115], [11, 23493], [12, 21861], [13, 20602], [14, 19557], [15, 18730], [16, 17940], [17, 17242], [18, 16573], [19, 15969], [20, 11584], [21, 9074], [22, 7482], [23, 6334], [24, 5520], [25, 4908], [26, 4471], [27, 4058], [28, 3739], [29, 2134], [30, 1556], [31, 1209], [32, 992], [33, 854], [34, 742], [35, 657], [36, 589], [37, 548], [38, 304], [39, 201], [40, 158], [41, 137], [42, 117], [43, 98], [44, 83], [45, 75], [46, 66];
    }


    @RequestMapping(value = "/json/gene-by-cutoff/expMap.json", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getMap() {
        Map<String, String> result = new HashMap<>();

        result.put("1", "24908");
        result.put("2", "23690");
        result.put("3", "23014");
        result.put("4", "22478");
        result.put("5", "22094");
        result.put("6", "21790");
        result.put("7", "21504");
        result.put("8", "21280");
        result.put("9", "21070");
        result.put("10", "20267");
        result.put("11", "19147");
        result.put("12", "18297");
        result.put("13", "17559");
        result.put("14", "16934");
        result.put("15", "16311");
        result.put("16", "15763");
        result.put("17", "15252");
        result.put("18", "14756");
        result.put("19", "14261");
        result.put("20", "10555");
        result.put("21", "8283");
        result.put("22", "6793");
        result.put("23", "5830");
        result.put("24", "5068");
        result.put("25", "4521");
        result.put("26", "4074");
        result.put("27", "3724");
        result.put("28", "3451");
        result.put("29", "2038");
        result.put("30", "1493");
        result.put("31", "1204");
        result.put("32", "985");
        result.put("33", "844");
        result.put("34", "751");
        result.put("35", "681");
        result.put("36", "627");
        result.put("37", "568");
        result.put("38", "315");
        result.put("39", "229");
        result.put("40", "195");
        result.put("41", "166");
        result.put("42", "144");
        result.put("43", "129");
        result.put("44", "120");
        result.put("45", "115");
        result.put("46", "107");
        result.put("47", "76");
        result.put("48", "63");
        result.put("49", "54");
        Gson gson = new Gson();

        return gson.toJson(result, Map.class);

    }

//    @RequestMapping(value = "/json/gene-by-cutoff/exp.json", method = RequestMethod.GET)
//       @ResponseStatus(HttpStatus.OK)
//      public String[][] getAll() {
//           String[][] result = {{"0", "40851"}, {"1", "37809"},
//                   {"2", "35643"},
//                   {"3", "34032"},
//                   {"4", "32669"},
//                   {"5", "31466"},
//                   {"6", "30606"}
//           };
//           return result;
//
//
//   //        [2, 35643], [3, 34032], [4, 32669], [5, 31466], [6, 30606],
//   //        [7, 29825], [8, 29172], [9, 28563], [10, 26115], [11, 23493], [12, 21861], [13, 20602], [14, 19557], [15, 18730], [16, 17940], [17, 17242], [18, 16573], [19, 15969], [20, 11584], [21, 9074], [22, 7482], [23, 6334], [24, 5520], [25, 4908], [26, 4471], [27, 4058], [28, 3739], [29, 2134], [30, 1556], [31, 1209], [32, 992], [33, 854], [34, 742], [35, 657], [36, 589], [37, 548], [38, 304], [39, 201], [40, 158], [41, 137], [42, 117], [43, 98], [44, 83], [45, 75], [46, 66];
//       }
}
