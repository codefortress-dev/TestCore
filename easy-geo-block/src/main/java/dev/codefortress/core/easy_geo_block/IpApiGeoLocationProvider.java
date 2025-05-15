package dev.codefortress.core.easy_geo_block;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class IpApiGeoLocationProvider implements GeoLocationProvider {

    private final WebClient webClient;

    public IpApiGeoLocationProvider() {
        this.webClient = WebClient.builder()
                .baseUrl("https://ipapi.co")
                .build();
    }

    @Override
    public String resolveCountryCode(String ip) {
        try {
            return webClient.get()
                    .uri("/{ip}/country/", ip)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(WebClientResponseException.class, e -> Mono.empty())
                    .block(); // bloqueo solo por ser llamada interna breve
        } catch (Exception e) {
            return null;
        }
    }
}
