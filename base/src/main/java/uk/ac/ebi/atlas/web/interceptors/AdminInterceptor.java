package uk.ac.ebi.atlas.web.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
public class AdminInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getUserPrincipal() == null) {
            return false;
        }

        LOGGER.info(
                "<preHandle> username: {}, request: {}, query: {}",
                request.getUserPrincipal().getName(),
                request.getRequestURI(),
                request.getQueryString());

        return true;
    }
}
