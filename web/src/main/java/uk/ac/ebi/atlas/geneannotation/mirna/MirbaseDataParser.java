/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.geneannotation.mirna;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MirbaseDataParser {

    public static final String ID = "ID";

    public static final String DR = "DR";

    private String miRnaDataLocation;

    protected void setMiRnaDataLocation(String miRnaDataLocation) {
        this.miRnaDataLocation = miRnaDataLocation;
    }

    public Map<String, String> extractAnnotationsMap() {

        Map<String, String> result = Maps.newHashMap();

        try (BufferedReader br = new BufferedReader(new FileReader(miRnaDataLocation))) {

            readData(result, br);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected void readData(Map<String, String> result, BufferedReader br) throws IOException {
        String line = br.readLine();

        String id;
        String name;

        while (line != null) {
            id = getID(line);
            if (id != null) {
                name = getName(line);
                if (name != null) {
                    result.put(id, name);
                }
            }

            line = br.readLine();
        }
    }

    protected String getName(String entry) {
        if (entry.startsWith(DR)) {
            ArrayList<String> words = Lists.newArrayList(Splitter.on("; ").trimResults().split(entry));
            String nameWithDot = words.get(words.size() - 1);
            return nameWithDot.substring(0, nameWithDot.length() - 1);
        }
        return null;
    }


    protected String getID(String entry) {
        if (entry.startsWith(ID)) {
            ArrayList<String> words = Lists.newArrayList(Splitter.on(" ").trimResults().omitEmptyStrings().split(entry));
            if (words.size() > 2) {
                return words.get(1);
            }
        }
        return null;
    }
}
