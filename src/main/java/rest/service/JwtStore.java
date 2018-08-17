package rest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import rest.dto.UserAuth;

import javax.ejb.Stateless;
import java.time.Instant;
import java.util.Date;

@Stateless
public class JwtStore {
    public String generateToken(final UserAuth userAuth) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");

            return JWT.create()
                    .withClaim("username", userAuth.getUserName())
                    .withClaim("role", userAuth.getRole().toString())
                    .withIssuedAt(Date.from(Instant.now().plusSeconds(3600)))
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            return null;
        }
    }

    private String generateUserAuth(final String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getPayload();
    }
}
