package com.kuretru.web.gemini.filter;

import com.kuretru.api.common.wrapper.RequestParamSnakeCaseToCamelCaseRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Component
@WebFilter(urlPatterns = {
        "/oauth2",
}, filterName = "RequestParamSnakeCaseToCamelCaseFilter")
public class RequestParamSnakeCaseToCamelCaseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new RequestParamSnakeCaseToCamelCaseRequestWrapper((HttpServletRequest)request), response);
    }

}
