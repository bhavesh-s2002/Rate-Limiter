package com.springBoot.RateGuard.filter;

import com.springBoot.RateGuard.model.RateLimitResult;
import com.springBoot.RateGuard.service.RateLimiterService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
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

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientId =
                httpRequest.getHeader("X-CLIENT-ID");

        RateLimitResult result =
                rateLimiterService.allowRequest(clientId);


        httpResponse.setHeader(
                "X-RateLimit-Limit",
                String.valueOf(
                        result.getLimit()
                )
        );


        httpResponse.setHeader(
                "X-RateLimit-Remaining",
                String.valueOf(
                        result.getRemaining()
                )
        );


        if (!result.isAllowed()) {
            httpResponse.setStatus(429);
            httpResponse.setContentType(
                    "application/json"
            );

            String responseBody =
                    "{"
                            + "\"message\":\"Rate limit exceeded\","
                            + "\"limit\":" + result.getLimit() + ","
                            + "\"remaining\":" + result.getRemaining()
                            + "}";

            httpResponse.getWriter()
                    .write(responseBody);

            return;
        }
        chain.doFilter(request, response);
    }

}
