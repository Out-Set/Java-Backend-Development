package com.security.keycloak.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    //This Object is use to convert authority from oauth token to understandable spring format
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final String principleAttribute = "preferred_username";

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
    	
    	System.out.println("GrantedAuthorities: " + jwtGrantedAuthoritiesConverter.convert(jwt));

        //method is use to extract the claims and principals
        Collection<GrantedAuthority> authorities = 
        		Stream.concat(
                        jwtGrantedAuthoritiesConverter
                                .convert(jwt)
                                .stream(),
                        extractRoles(jwt).stream())
                .collect(Collectors.toSet());
        System.out.println("Authorities: " + authorities);
        System.out.println("PrincipleClaimName: " + getPrincipleClaimName(jwt));

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt)
        );
    }


    private String getPrincipleClaimName(Jwt jwt) {
        String claimNames = JwtClaimNames.SUB;
        if(principleAttribute != null) {
            claimNames = principleAttribute;
        }

        return jwt.getClaim(claimNames);
    }


    @SuppressWarnings("unchecked")
	private Collection<GrantedAuthority> extractRoles(Jwt jwt) {

        // Return a Empty Collection if resource_access is not provided in OAuth token
        Map<String,Object> claims = jwt.getClaims();
        System.out.println("Claims: " + claims);

        if(claims.get("realm_access") == null) {
            return Set.of();
        }

        Map<String, Object> realmsAccess = jwt.getClaimAsMap("realm_access");

        System.out.println("RealmRoles: "+realmsAccess.get("roles"));
        return ((List<String>) realmsAccess.get("roles"))
        		.stream().map(roleName -> "ROLE_" + roleName)
        		.map(SimpleGrantedAuthority::new)
        		.collect(Collectors.toList());
    }
}
