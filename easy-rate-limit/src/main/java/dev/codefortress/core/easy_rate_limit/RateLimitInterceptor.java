package dev.codefortress.core.easy_rate_limit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimitStorage storage;
    private final RateLimitConfig config;

    public RateLimitInterceptor(RateLimitStorage storage, RateLimitConfig config) {
        this.storage = storage;
        this.config = config;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = extractClientIp(request);

        if (storage.isLimitExceeded(ip, config.getMaxRequests())) {
            response.setStatus(HttpServletResponse.SC_TOO_MANY_REQUESTS); // 429
            response.getWriter().write("Rate limit exceeded");
            return false;
        }

        return true;
    }

    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isBlank()) {
            return xfHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
