package dev.codefortress.core.easy_context;
public class ExecutionContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();
    private static final ThreadLocal<TenantContext> tenantContext = new ThreadLocal<>();

    public static void setUser(UserContext user) {
        userContext.set(user);
    }

    public static UserContext getUser() {
        return userContext.get();
    }

    public static void clearUser() {
        userContext.remove();
    }

    public static void setTenant(TenantContext tenant) {
        tenantContext.set(tenant);
    }

    public static TenantContext getTenant() {
        return tenantContext.get();
    }

    public static void clearTenant() {
        tenantContext.remove();
    }

    public static void clearAll() {
        clearUser();
        clearTenant();
    }
}