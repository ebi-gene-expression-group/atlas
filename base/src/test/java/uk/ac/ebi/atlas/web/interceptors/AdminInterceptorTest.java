package uk.ac.ebi.atlas.web.interceptors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import java.util.concurrent.ThreadLocalRandom;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminInterceptorTest {
    @Mock
    private HttpServletRequest requestMock;

    private AdminInterceptor subject;

    @Before
    public void setUp() {
        subject = new AdminInterceptor();
    }

    @Test
    public void anyAuthenticatedUserHasAdminRights() {
        when(requestMock.getUserPrincipal()).thenReturn(() -> random(ThreadLocalRandom.current().nextInt(100)));
        assertThat(subject.preHandle(requestMock, null, null)).isTrue();

        when(requestMock.getUserPrincipal()).thenReturn(null);
        assertThat(subject.preHandle(requestMock, null, null)).isFalse();
    }
}
