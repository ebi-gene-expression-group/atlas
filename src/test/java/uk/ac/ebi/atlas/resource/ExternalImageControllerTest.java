
package uk.ac.ebi.atlas.resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.resource.ContrastImageFactory;
import uk.ac.ebi.atlas.resource.ExternalImageController;
import uk.ac.ebi.atlas.utils.ImageIOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalImageControllerTest {

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


    @Mock
    ExtraInfoFactory extraInfoFactory;
    @Mock
    ContrastImageFactory contrastImageFactory;

    @Before
    public void setUp() throws Exception {
        subject = new ExternalImageController(imageIOUtilsMock,extraInfoFactory,contrastImageFactory);

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