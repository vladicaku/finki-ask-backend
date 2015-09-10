package finki.ask.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.filter.OncePerRequestFilter;

public class VaceFilter extends OncePerRequestFilter {

	public VaceFilter() {
		// TODO Auto-generated constructor stub
	}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
    	
    	System.out.println(">>>>>>>>>>VACE FILTER");
    	
    	response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTION, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Origin, Content-Type, Accept, x-auth-token, x-requested-with");
	    response.setHeader("Access-Control-Expose-Headers", "Cookie, Cookies, Set-Cookie");
	    //response.setHeader("Access-Control-Allow-Credentials", "true");

        String origin = request.getHeader("Origin");
        
        if (origin != null) {
        	System.out.println(">>>>>>>>>>>>>>>> " + origin);
            //response.setHeader("Access-Control-Allow-Origin", origin);
            //response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        filterChain.doFilter(request, response);
    }
}