package uk.ac.ebi.atlas.interceptors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimingInterceptorTest {

    private TimingInterceptor subject;

    private HttpServletRequestWrapper requestWrapper;

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private StopWatch stopWatch;

    @Before
    public void setUp() throws Exception {
        subject = new TimingInterceptor();
        requestWrapper = new HttpServletRequestWrapper(requestMock);
        when(requestMock.getAttribute(TimingInterceptor.STOP_WATCH)).thenReturn(stopWatch);
    }

    @Test
    public void testPreHandle() throws Exception {
        assertThat(subject.preHandle(requestWrapper, null, null), is(true));

        StopWatch stopWatch = (StopWatch) requestWrapper.getAttribute(TimingInterceptor.STOP_WATCH);
        assertThat(stopWatch, is(not(nullValue())));
    }

    @Test
    public void testPostHandle() throws Exception {
        subject.postHandle(requestMock, null, null, null);

        verify(requestMock).getAttribute(TimingInterceptor.STOP_WATCH);
        verify(stopWatch).stop();
    }
}