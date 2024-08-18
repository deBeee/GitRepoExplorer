package com.example.gitrepoexplorer.infrastructure.security.jwt;

import com.example.gitrepoexplorer.infrastructure.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserDetailsService userDetailsService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        SecurityUser user;
        try {
            user = (SecurityUser) userDetailsService.loadUserByUsername(oidcUser.getEmail());
        } catch (UsernameNotFoundException e) {
            OAuth2Error oauth2Error = new OAuth2Error("invalid_token", "User not found: " + e.getMessage(), null);
            throw new OAuth2AuthenticationException(oauth2Error, e);
        }
        OidcUserInfo userInfo = OidcUserInfo.builder()
                .claim("roles", user.getAuthoritiesAsStringList())
                .build();
        Collection<? extends GrantedAuthority> authorities = Stream.concat(
                oidcUser.getAuthorities().stream(),
                user.getAuthorities().stream()
        ).collect(Collectors.toSet());
        return new DefaultOidcUser(authorities, oidcUser.getIdToken(), userInfo, "sub");
    }

}