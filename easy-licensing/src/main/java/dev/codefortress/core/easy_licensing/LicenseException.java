package dev.codefortress.core.easy_licensing;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class LicenseException extends RuntimeException {

    public LicenseException(String message) {
        super(message);
    }

    public LicenseException(String message, Throwable cause) {
        super(message, cause);
    }
    public LicenseException(WebClientResponseException cause) {
        super(cause.getMessage(), cause);
    }
}
