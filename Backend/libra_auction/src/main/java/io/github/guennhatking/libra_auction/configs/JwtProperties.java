package io.github.guennhatking.libra_auction.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    private long expiration;
    private long refreshableDuration;

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public long getRefreshableDuration() {
        return refreshableDuration;
    }

    public void setRefreshableDuration(long refreshableDuration) {
        this.refreshableDuration = refreshableDuration;
    }
}
