package com.anigame.ticket_services.infrastructure.security;

import com.anigame.ticket_services.infrastructure.security.dto.JwtPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtCoDec {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String encode(String id, String issuer, String subject,
                         Instant issuedAt, Instant expiresAt, Map<String,String> claim) {
        var tokenClaims = id == null ? JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(subject)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .claim("scope", claim.get("scope"))
                .build() : JwtClaimsSet.builder()
                .id(id)
                .issuer(issuer)
                .subject(subject)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .claim("scope", claim.get("scope"))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(tokenClaims)).getTokenValue();
    }

    public JwtPayload decode(String jwt) {
        var decodedJwt = jwtDecoder.decode(jwt);
        var claims = Map.of("scope", decodedJwt.getClaims().get("scope").toString());
        return new JwtPayload(
                decodedJwt.getId(),
                decodedJwt.getIssuer().toString(),
                decodedJwt.getSubject(),
                decodedJwt.getIssuedAt(),
                decodedJwt.getExpiresAt(),
                claims
        );
    }
}
