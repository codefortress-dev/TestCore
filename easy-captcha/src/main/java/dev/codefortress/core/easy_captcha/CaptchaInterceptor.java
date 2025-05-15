package dev.codefortress.core.easy_captcha;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.List;

public class CaptchaInterceptor implements HandlerInterceptor {

    private final CaptchaService captchaService;
    private final List<String> protectedPaths;

    public CaptchaInterceptor(CaptchaService captchaService, List<String> protectedPaths) {
        this.captchaService = captchaService;
        this.protectedPaths = protectedPaths;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (shouldIntercept(request.getRequestURI())) {
            if (captchaService.isSuspicious(request)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("text/plain");
                response.getWriter().write(captchaService.getBlockMessage());
                return false;
            }
        }
        return true;
    }

    private boolean shouldIntercept(String uri) {
        return protectedPaths.stream().anyMatch(uri::startsWith);
    }
}
