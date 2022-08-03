package filter;


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*",
	initParams = {@WebInitParam(name = "encoding", value = "UTF-8")}
)
public class EncodingFilter implements Filter {
	public static final Logger logger = Logger.getLogger(EncodingFilter.class);
	private String encoding;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("EncodingFilter initialization ");
		encoding = filterConfig.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.trace("Encoding filter starts to request ==> " + request);
		HttpServletRequest req = (HttpServletRequest) request;
		String characterEncoding = req.getCharacterEncoding();
		logger.debug("current characterEncoding ==> " + characterEncoding);
		if (characterEncoding == null) {
			logger.debug("set encoding: ==> " + encoding);
			req.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
		logger.trace("Encoding filter finished");
	}

	@Override
	public void destroy() {
		logger.debug("EncodingFilter destruction");
	}
}
