package com.springBoot.RateGuard.filter;

import com.springBoot.RateGuard.service.RateLimiterService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RateLimitFilter implements Filter {

    private final RateLimiterService rateLimiterService;

    public RateLimitFilter(RateLimiterService rateLimiterService){
        this.rateLimiterService = rateLimiterService;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String clientId = request.getRemoteAddr();

        boolean allowed = rateLimiterService.allowRequest(clientId);

        if (!allowed) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(429);

            httpResponse.getWriter().write(
                    "Too many requests. Try later."
            );

            return;
        }
        chain.doFilter(request, response);
    }

}
