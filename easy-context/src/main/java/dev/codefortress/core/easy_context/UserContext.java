package dev.codefortress.core.easy_context;
public class UserContext {
    private final String username;
    private final String email;
    private final String[] roles;
    // Contexto compartido entre los diferentes módulos de la aplicación
    public UserContext(String username, String email, String[] roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String[] getRoles() {
        return roles;
    }
}