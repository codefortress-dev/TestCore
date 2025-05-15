package dev.codefortress.core.easy_geo_block;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class GeoBlockInterceptor implements HandlerInterceptor {

    private final GeoBlockService geoBlockService;

    public GeoBlockInterceptor(GeoBlockService geoBlockService) {
        this.geoBlockService = geoBlockService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String clientIp = extractClientIp(request);

        if (geoBlockService.isBlocked(clientIp)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain");
            response.getWriter().write(geoBlockService.getBlockMessage());
            return false;
        }

        return true;
    }

    /**
     * Extrae la IP real del cliente, considerando proxies.
     */
    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isBlank()) {
            return xfHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
