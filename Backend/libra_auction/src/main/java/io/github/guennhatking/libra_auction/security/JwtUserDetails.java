package io.github.guennhatking.libra_auction.security;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Minimal UserDetails implementation that only carries the user id extracted from the JWT.
 * This class is used as the principal for @AuthenticationPrincipal injection.
 */
public class JwtUserDetails implements UserDetails {
    private final String userId;

    public JwtUserDetails(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    // ----- UserDetails contract (no authorities needed for our current checks) -----
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
