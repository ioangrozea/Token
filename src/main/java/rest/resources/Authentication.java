package rest.resources;


import rest.dto.UserAuth;
import rest.service.JwtStore;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;


@Path("/authentication")
public class Authentication {
    @EJB
    private JwtStore jwtStore;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response userLogin(UserAuth userAuth) {
        String token = jwtStore.generateToken(userAuth);
        return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
    }
}
