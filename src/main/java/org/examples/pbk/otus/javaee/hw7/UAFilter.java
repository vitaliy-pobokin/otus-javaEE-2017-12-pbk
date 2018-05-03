package org.examples.pbk.otus.javaee.hw7;

import com.blueconic.browscap.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter (filterName = "UserAgentFilter", urlPatterns = "/*")
public class UAFilter implements Filter {
    private static final String BROWSER_SUPPORTED_COOKIE = "BrowserSupported";
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String DOWNLOAD_BROWSER_PATH = "/download_browser";
    private static final Map<String, Integer> MIN_REQUIREMENTS = new HashMap<>();

    static {
        MIN_REQUIREMENTS.put("Chrome", 50);
        MIN_REQUIREMENTS.put("Firefox", 45);
        MIN_REQUIREMENTS.put("Opera", 38);
        MIN_REQUIREMENTS.put("MSIE", 10);
    }

    private UserAgentParser parser;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.parser = UserAgentParserProvider.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isCookiePresent(BROWSER_SUPPORTED_COOKIE, (HttpServletRequest) request)) {
            chain.doFilter(request, response);
        } else {
            if (isBrowserSupported((HttpServletRequest) request)) {
                ((HttpServletResponse) response).addCookie(new Cookie(BROWSER_SUPPORTED_COOKIE, ""));
                chain.doFilter(request, response);
            } else {
                request.getRequestDispatcher(DOWNLOAD_BROWSER_PATH).forward(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isCookiePresent(String cookieName, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName))
                    return true;
            }
        }
        return false;
    }

    private boolean isBrowserSupported(HttpServletRequest request) {
        String userAgent = request.getHeader(USER_AGENT_HEADER);
        Capabilities capabilities = parser.parse(userAgent);
        String browser = capabilities.getBrowser();
        String version = capabilities.getBrowserMajorVersion();
        return (MIN_REQUIREMENTS.containsKey(browser) && Integer.parseInt(version) >= MIN_REQUIREMENTS.get(browser));
    }
}
