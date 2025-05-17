package dev.codefortress.core.easy_rate_limit;

/**
 * Permite saber desde cualquier parte del flujo si la solicitud actual
 * fue bloqueada o aceptada por el sistema de rate-limit.
 */
public class RateLimitContext {

    private static final ThreadLocal<RateLimitState> state = new ThreadLocal<>();

    public static void set(RateLimitState rateLimitState) {
        state.set(rateLimitState);
    }

    public static RateLimitState get() {
        return state.get();
    }

    public static void clear() {
        state.remove();
    }
}
