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
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Named
public class MirbaseFastaParser {

    @Value("#{configuration['mirbase.fasta.path.template']}")
    private String miRnaFastaLocation;

    public static final String NEW_ENTRY_SYMBOL = ">";

    protected void setMiRnaFastaLocation(String miRnaFastaLocation) {
        this.miRnaFastaLocation = miRnaFastaLocation;
    }


    public List<MiRNAEntity> parse() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new URL(miRnaFastaLocation).openConnection().getInputStream())))) {

            return readData(br);

        } catch (IOException e) {
            throw new IllegalStateException("Cannot find miRBase fasta file in " + miRnaFastaLocation, e);
        }
    }


    protected List<MiRNAEntity> readData(BufferedReader br) throws IOException {
        List<MiRNAEntity> entries = Lists.newArrayList();
        String line;

        MiRNAEntity entity = null;

        while ((line = br.readLine()) != null) {
            if (line.startsWith(NEW_ENTRY_SYMBOL)) {

                ArrayList<String> words = splitLine(line);

                entity = new MiRNAEntity(parseIdentifier(words), parseAccession(words), parseOrganism(words), parseName(words));
            } else {
                entity.setSequence(line);
                entries.add(entity);
            }

        }
        return entries;
    }

    protected ArrayList<String> splitLine(String line) {
        return Lists.newArrayList(Splitter.on(" ").omitEmptyStrings().trimResults().split(line));
    }

    protected String parseName(ArrayList<String> words) {
        return words.get(words.size() - 1);
    }

    protected String parseIdentifier(ArrayList<String> words) {
        return words.get(0).replaceFirst(NEW_ENTRY_SYMBOL, "");
    }

    protected String parseAccession(ArrayList<String> words) {
        return words.get(1);
    }

    protected String parseOrganism(ArrayList<String> words) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : words.subList(2, words.size() - 1)) {
            stringBuffer.append(s).append(" ");
        }
        return stringBuffer.toString().trim();
    }


}
