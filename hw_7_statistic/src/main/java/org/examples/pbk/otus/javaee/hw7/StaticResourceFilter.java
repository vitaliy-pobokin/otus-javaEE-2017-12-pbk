package org.examples.pbk.otus.javaee.hw7;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "StaticResourceFilter", urlPatterns = "/static/*")
public class StaticResourceFilter implements Filter {

    private static final String RESOURCE_PATH = "/static/";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {

        String path = ((HttpServletRequest) request).getServletPath();
        if (path.toLowerCase().startsWith(RESOURCE_PATH)) {
            request.getRequestDispatcher(path).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
