package dev.codefortress.core.easy_geo_block;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class GeoBlockService {

    private final GeoLocationProvider locationProvider;
    private final GeoBlockProperties properties;

    public GeoBlockService(GeoLocationProvider provider, GeoBlockProperties props) {
        this.locationProvider = provider;
        this.properties = props;
    }

    public boolean isBlocked(String ip) {
        if (!properties.isEnabled()) return false;

        String countryCode = locationProvider.resolveCountryCode(ip);
        if (countryCode == null) return true;

        countryCode = countryCode.trim().toUpperCase(Locale.ROOT);
        return !properties.getAllowedCountries().contains(countryCode);
    }

    public String getCountry(String ip) {
        return locationProvider.resolveCountryCode(ip);
    }

    public String getBlockMessage() {
        return properties.getBlockMessage();
    }
}
