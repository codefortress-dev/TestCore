package dev.codefortress.core.easy_licensing;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatusCode;


public class LicenseRemoteValidator {

    private final WebClient webClient;

    public LicenseRemoteValidator() {
        // Puedes parametrizar el dominio más adelante (propiedades)
        this.webClient = WebClient.builder()
                .baseUrl("https://license.codefortress.dev/api")
                .build();
    }

    public LicenseInfo validateOnline(LicenseProperties properties) {
        try {
            return webClient.post()
                    .uri("/validate")
                    .bodyValue(new LicenseValidationRequest(properties.getProduct(), properties.getKey()))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response ->
                            response.createException().map(LicenseException::new)
                    )
                    .bodyToMono(LicenseInfo.class)
                    .block();
        } catch (Exception e) {
            throw new LicenseException("Error contactando el servidor de licencias", e);
        }
    }

    // Clase interna para request, o puede estar fuera si prefieres
    private record LicenseValidationRequest(String product, String key) {}
}
