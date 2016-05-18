
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
import java.io.InputStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalImageControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";
    public static final String EXTRA_INFO_PATH_TEMPLATE = "magetab/{0}/{0}-extra-info.png";
    public static final String RNA_SEQ_PATH_TEMPLATE = "magetab/{0}/{0}-{1}-mvaPlot.png";
    public static final String MICROARRAY_PATH_TEMPLATE = "magetab/{0}/{0}_{1}-{2}-mvaPlot.png";
    public static final String GSEA_PLOT_TEMPLATE = "magetab/{0}/{0}.{1}.{2}.gsea_class_non_dir_both.png";

    @Mock
    private ImageIOUtils imageIOUtilsMock;

    @Mock
    private HttpServletResponse responseMock;

    @Mock
    private BufferedImage bufferedImageMock;

    @Mock
    private ServletOutputStream outputStreamMock;

    private ExternalImageController subject;

    @Mock
    private InputStream imageInputStreamMock;

    @Before
    public void setUp() throws Exception {
        subject = new ExternalImageController(imageIOUtilsMock,
                EXTRA_INFO_PATH_TEMPLATE,
                RNA_SEQ_PATH_TEMPLATE,
                MICROARRAY_PATH_TEMPLATE, GSEA_PLOT_TEMPLATE);

        when(imageIOUtilsMock.read(imageInputStreamMock)).thenReturn(bufferedImageMock);
        when(responseMock.getOutputStream()).thenReturn(outputStreamMock);
        when(imageIOUtilsMock.write(bufferedImageMock, "png", outputStreamMock)).thenReturn(true);
    }


    @Test
    public void testStreamExternalImage() throws Exception {
        subject.streamExternalImage(responseMock, imageInputStreamMock);

        verify(imageIOUtilsMock).read(imageInputStreamMock);
        verify(responseMock).setContentType("image/png");
        verify(imageIOUtilsMock).write(bufferedImageMock, "png", outputStreamMock);
        verify(outputStreamMock).close();
    }
}