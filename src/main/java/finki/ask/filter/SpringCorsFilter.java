package finki.ask.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.filter.OncePerRequestFilter;

//@PreFilter(value = "")
public class SpringCorsFilter extends OncePerRequestFilter  {
    static final String ORIGIN = "Origin";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println(">>>>>>>>>>>>>> SSC");
    	
           String origin = request.getHeader(ORIGIN);
           response.setHeader("Access-Control-Allow-Origin", origin);//* or origin as u prefer
           response.setHeader("Access-Control-Allow-Credentials", "true");
           response.setHeader("Access-Control-Allow-Headers",
                    request.getHeader("Access-Control-Request-Headers"));

        filterChain.doFilter(request, response);
    }
}