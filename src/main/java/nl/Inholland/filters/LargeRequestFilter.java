package nl.Inholland.filters;

import lombok.extern.java.Log;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Log
@Component
@Order(2)
public class LargeRequestFilter implements Filter {
    public static final int MAX_SIZE = 1;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        int size = request.getContentLength();

        if (size > 2000) {
            throw new IllegalArgumentException("Request too large");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
