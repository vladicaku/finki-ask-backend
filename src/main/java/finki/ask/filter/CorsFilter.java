package finki.ask.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/*"})
public class CorsFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
//		response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//		response.addHeader("Access-Control-Allow-Headers", "x-requested-with");
//		response.addHeader("Access-Control-Expose-Headers", "x-requested-with");
		response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}

// public class CorsFilter extends OncePerRequestFilter {
// @Override
// protected void doFilterInternal(HttpServletRequest request,
// HttpServletResponse response, FilterChain filterChain)
// throws ServletException, IOException {
// System.out.println("<<<<<< FILTER");
// response.addHeader("Access-Control-Allow-Origin", "*");
//
// if (request.getHeader("Access-Control-Request-Method") != null &&
// "OPTIONS".equals(request.getMethod())) {
// // CORS "pre-flight" request
// response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
//// response.addHeader("Access-Control-Allow-Headers", "Authorization");
// response.addHeader("Access-Control-Allow-Headers", "Content-Type");
// response.addHeader("Access-Control-Max-Age", "1");
// }
//
// filterChain.doFilter(request, response);
// }
//
// }