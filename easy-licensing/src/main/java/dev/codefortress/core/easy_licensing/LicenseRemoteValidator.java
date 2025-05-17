package dev.codefortress.core.easy_licensing;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.stereotype.Component;

@Component
public class LicenseRemoteValidator {

    private final WebClient client;

    public LicenseRemoteValidator() {
        this.client = WebClient.builder()
                .baseUrl("https://license.codefortress.dev/api")
                .build();
    }

    public LicenseInfo validate(String product, String key, String domain) {
        try {
            return client.post()
                    .uri("/validate")
                    .bodyValue(new ValidationRequest(product, key, domain))
                    .retrieve()
                    .bodyToMono(LicenseInfo.class)
                    .block();
        } catch (WebClientResponseException e) {
            return null;
        }
    }

    private record ValidationRequest(String product, String key, String domain) {}
}
