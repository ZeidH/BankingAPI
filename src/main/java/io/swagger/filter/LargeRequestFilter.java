package io.swagger.filter;

import lombok.extern.java.Log;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Log
@Component
public class LargeRequestFilter implements Filter {
    public static final int MAX_SIZE = 1;
    //private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        int size = request.getContentLength();

        //log.error("Request size: " + size);
        if (size > 0) {
            //logger.severe("request with size " + size + " was rejected");
            throw new IllegalArgumentException("Request too large");
        } else {
            chain.doFilter(request, response);
        }
    }
}
