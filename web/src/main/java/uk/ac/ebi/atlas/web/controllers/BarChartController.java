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

    @RequestMapping(value = "/json/gene-by-cutoff/exp.json", method = RequestMethod.GET, produces="application/json")
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


    @RequestMapping(value = "/json/gene-by-cutoff/expMap.json", method = RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getMap() {
        Map<String, String> result = new HashMap<>();

        result.put("0", "40851");
        result.put("1", "37809");

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
