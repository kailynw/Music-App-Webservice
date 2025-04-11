package com.restendpoints.musicapp.service;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.*;

@Service
public class AuthService {

    public String validateJwtToken(String token){
        String domain = "dev-d7c2feqndyw6nn1w.us.auth0.com"; // Replace with your Auth0 domain (e.g., dev-1234.us.auth0.com)

        try {
            // 1. Create a JWK Provider to fetch the JWKS data
            JwkProvider provider = new JwkProviderBuilder(domain)
                    .cached(10, 24, TimeUnit.HOURS) // Cache up to 10 keys for 24 hours
                    .rateLimited(10, 1, TimeUnit.MINUTES) // Rate limit calls to the endpoint
                    .build();

            // 2. Decode the JWT to extract the Key ID (kid)
            DecodedJWT jwt = JWT.decode(token);
            String kid = jwt.getKeyId();

            // 3. Retrieve the JWK (JSON Web Key) using the kid
            Jwk jwk = provider.get(kid);

            // 4. Get the public key (RSAPublicKey) from the JWK
            RSAPublicKey publicKey = (RSAPublicKey) jwk.getPublicKey();

            // 5. Create an Algorithm instance with the retrieved public key
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

            // 6. Create a JWTVerifier instance to validate the token
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("https://" + domain + "/") // Verify the token's issuer
                    .build();

            // 7. Verify the token
            DecodedJWT verifiedJwt = verifier.verify(token);
            return verifiedJwt.getClaim("sub").asString();
        } catch (JWTVerificationException e) {
            System.err.println("Invalid token: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error verifying token: " + e.getMessage());
            return null;
        }
    }

}
