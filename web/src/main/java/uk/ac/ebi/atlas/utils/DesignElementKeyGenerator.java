package uk.ac.ebi.atlas.utils;

public class DesignElementKeyGenerator {

    public static String getKey(String arrayDesignAcc, String designElementAcc) {
        StringBuilder sb = new StringBuilder();
        sb.append(arrayDesignAcc).append("&&").append(designElementAcc);
        return sb.toString();
    }
}
