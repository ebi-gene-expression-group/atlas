package uk.ac.ebi.atlas.model.readers;

import com.google.common.collect.SortedSetMultimap;

import java.util.List;


public interface TsvReader {

    String[] readLine(String experimentAccession, long lineIndex);

    List<String[]> readAll(String experimentAccession);

    List<String[]> readAllComments(String experimentAccession);

    SortedSetMultimap<String, String> readAllCommentsAsMap(String experimentAccession);

}