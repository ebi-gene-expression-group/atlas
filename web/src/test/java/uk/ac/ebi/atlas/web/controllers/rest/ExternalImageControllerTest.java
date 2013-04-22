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

package uk.ac.ebi.atlas.web.controllers.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.utils.ImageIOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalImageControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";
    public static final String EXTRA_INFO_PATH_TEMPLATE = "magetab/{0}/{0}-extra-info.png";
    public static final String RNA_SEQ_PATH_TEMPLATE = "magetab/{0}/{0}-{1}-mvaPlot.png";
    public static final String MICROARRAY_PATH_TEMPLATE = "magetab/{0}/{0}_{1}-{2}-mvaPlot.png";
    public static final String CONTRAST_NAME = "contrastName";
    public static final String ARRAY_DESIGN_ACCESSION = "arrayDesignAccession";
    public static final String FILE_LOCATION = "fileLocation";

    @Mock
    private ImageIOUtils imageIOUtilsMock;

    @Mock
    private HttpServletResponse responseMock;

    @Mock
    private BufferedImage bufferedImageMock;

    @Mock
    private ServletOutputStream outputStreamMock;

    private ExternalImageController subject;

    @Before
    public void setUp() throws Exception {
        subject = new ExternalImageController(imageIOUtilsMock,
                EXTRA_INFO_PATH_TEMPLATE,
                RNA_SEQ_PATH_TEMPLATE,
                MICROARRAY_PATH_TEMPLATE);

        when(imageIOUtilsMock.read(any(File.class))).thenReturn(bufferedImageMock);
        when(responseMock.getOutputStream()).thenReturn(outputStreamMock);
        when(imageIOUtilsMock.write(bufferedImageMock, "png", outputStreamMock)).thenReturn(true);
    }

    @Test
    public void testStreamExtraInfoImage() throws Exception {
        subject.streamExtraInfoImage(responseMock, EXPERIMENT_ACCESSION);

        File file = new File(MessageFormat.format(EXTRA_INFO_PATH_TEMPLATE, EXPERIMENT_ACCESSION));
        verify(imageIOUtilsMock).read(file);
        verify(responseMock).setContentType("image/png");
        verify(imageIOUtilsMock).write(bufferedImageMock, "png", outputStreamMock);
        verify(outputStreamMock).close();
    }

    @Test
    public void testStreamMicroarrayMaPlotImage() throws Exception {
        subject.streamMicroarrayMaPlotImage(responseMock, EXPERIMENT_ACCESSION, ARRAY_DESIGN_ACCESSION, CONTRAST_NAME);

        File file = new File(MessageFormat.format(MICROARRAY_PATH_TEMPLATE, EXPERIMENT_ACCESSION, ARRAY_DESIGN_ACCESSION, CONTRAST_NAME));
        verify(imageIOUtilsMock).read(file);
        verify(responseMock).setContentType("image/png");
        verify(imageIOUtilsMock).write(bufferedImageMock, "png", outputStreamMock);
        verify(outputStreamMock).close();
    }

    @Test
    public void testStreamRnaSeqMaPlotImage() throws Exception {
        subject.streamRnaSeqMaPlotImage(responseMock, EXPERIMENT_ACCESSION, CONTRAST_NAME);

        File file = new File(MessageFormat.format(RNA_SEQ_PATH_TEMPLATE, EXPERIMENT_ACCESSION, CONTRAST_NAME));
        verify(imageIOUtilsMock).read(file);
        verify(responseMock).setContentType("image/png");
        verify(imageIOUtilsMock).write(bufferedImageMock, "png", outputStreamMock);
        verify(outputStreamMock).close();
    }

    @Test
    public void testStreamExternalImage() throws Exception {
        subject.streamExternalImage(responseMock, FILE_LOCATION);

        File file = new File(FILE_LOCATION);
        verify(imageIOUtilsMock).read(file);
        verify(responseMock).setContentType("image/png");
        verify(imageIOUtilsMock).write(bufferedImageMock, "png", outputStreamMock);
        verify(outputStreamMock).close();
    }
}