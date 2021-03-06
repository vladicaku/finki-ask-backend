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
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Origin", "http://192.168.1.124, 192.168.1.124, http://192.168.1.124:8090, 192.168.1.124:8090");
		//response.setHeader("Access-Control-Allow-Origin", "http://192.168.0.109, 192.168.1.124, http://192.168.0.109:8090, 192.168.0.109:8090, http://192.168.0.107:80, 192.168.0.107:80, 192.168.0.107");
		response.setHeader("Access-Control-Allow-Origin", "http://192.168.1.125, 192.168.1.125, http://192.168.1.125:8090, 192.168.1.1259:8090, http://192.168.0.107:80, 192.168.0.107:80, 192.168.0.107");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTION, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Origin, Content-Type, Accept, x-auth-token, x-requested-with");
	    response.setHeader("Access-Control-Expose-Headers", "Cookie, Cookies, Set-Cookie");
	    response.setHeader("Access-Control-Allow-Credentials", "true");
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
