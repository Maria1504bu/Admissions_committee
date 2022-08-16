package filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter( urlPatterns = { "/jsp/*"},
initParams = { @WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class PageRedirectSecurity implements Filter {
    private static final Logger LOG = Logger.getLogger(PageRedirectSecurity.class);
    private String indexPath;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.debug("PageRedirectFilter initialization");
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + indexPath);
        LOG.debug("Redirect user to index.jsp because he tried to get jsp page explicitly");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOG.debug("PageRedirectFilter destruction");
    }
}
