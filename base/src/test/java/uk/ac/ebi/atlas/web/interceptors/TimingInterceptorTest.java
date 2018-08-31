package uk.ac.ebi.atlas.web.interceptors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.web.interceptors.TimingInterceptor.STOP_WATCH;

@RunWith(MockitoJUnitRunner.class)
public class TimingInterceptorTest {
    private HttpServletRequestWrapper requestWrapper;

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private StopWatch stopWatch;

    private TimingInterceptor subject;

    @Before
    public void setUp() {
        subject = new TimingInterceptor();
        requestWrapper = new HttpServletRequestWrapper(requestMock);
        when(requestMock.getAttribute(STOP_WATCH)).thenReturn(stopWatch);
    }

    @Test
    public void testPreHandle() {
        assertThat(subject.preHandle(requestWrapper, null, null)).isTrue();

        StopWatch localStopWatch = (StopWatch) requestWrapper.getAttribute(STOP_WATCH);
        assertThat(localStopWatch).isNotNull();
    }

    @Test
    public void testPostHandle() {
        subject.postHandle(requestMock, null, null, null);

        verify(requestMock).getAttribute(STOP_WATCH);
        verify(stopWatch).stop();
    }
}
