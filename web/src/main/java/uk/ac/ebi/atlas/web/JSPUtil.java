package uk.ac.ebi.atlas.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class JSPUtil {

    public static String urlParamEncode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }
}
