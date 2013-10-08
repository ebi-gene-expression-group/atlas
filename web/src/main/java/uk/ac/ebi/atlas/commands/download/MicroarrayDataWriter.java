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

//package uk.ac.ebi.atlas.commands.download;
//
//import org.apache.commons.lang.StringUtils;
//import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
//import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
//import uk.ac.ebi.atlas.utils.TsvReaderUtils;
//
//import javax.inject.Inject;
//import java.text.MessageFormat;
//
//import static com.google.common.base.Preconditions.checkNotNull;
//
//class MicroarrayDataWriter extends ExpressionsWriterImpl {
//
//    private String arrayDesignAccession;
//
//    @Inject
//    public MicroarrayDataWriter(TsvReaderUtils tsvReaderUtils) {
//        super(tsvReaderUtils);
//    }
//
//    public void setArrayDesignAccession(String arrayDesignAccession) {
//        this.arrayDesignAccession = arrayDesignAccession;
//    }
//
//    @Override
//    protected String formatUrl(String fileUrlTemplate, String experimentAccession) {
//        return MessageFormat.format(fileUrlTemplate, experimentAccession, arrayDesignAccession);
//    }
//}
