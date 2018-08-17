package rest.resources;

import rest.Secured;
import rest.domain.User;
import rest.domain.UserRepository;
import rest.service.AuthenticationFilter;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Optional;

@Path("/users")
public class UserResource {
    @EJB
    private AuthenticationFilter authenticationFilter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Response getUsers(ContainerRequestContext requestContext) {
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (!authenticationFilter.validateToken(authorizationHeader))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        return Response.status(Response.Status.OK)
                .entity(UserRepository.getInstance().findAll())
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        return Response
                .status(Response.Status.CREATED)
                .entity(UserRepository.getInstance().add(user))
                .build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id, @Context UriInfo uriInfo) {
        URI uriURI = uriInfo
                .getBaseUriBuilder()
                .path(UserResource.class)
                .path("{id}")
                .resolveTemplate("id", id)
                .build();

        Optional<User> user = UserRepository.getInstance().getUserById(id);
        return user.map(x -> {
            user.get().setSelf(Link.fromUri(uriURI).build());
            return Response
                    .status(Response.Status.OK)
                    .entity(user.get())
                    .build();
        }).orElse(Response
                .status(Response.Status.NOT_FOUND)
                .build());
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        return Response
                .status(Response.Status.OK)
                .build();
    }

    @PUT
    public Response updateUser(User user) {
        Optional<User> optionalUser = UserRepository.getInstance().update(user);
        return optionalUser.map(x -> Response
                .status(Response.Status.OK)
                .build())
                .orElse(Response
                        .status(Response.Status.NOT_FOUND)
                        .build());
    }

    @GET
    @Path("/byFirstName")
    public Response findUserByFirstName(@HeaderParam("firstName") String firstName) {
        Optional<User> optionalUser = UserRepository.getInstance().findAll().stream()
                .filter(x -> x.getFirstName().equals(firstName))
                .findFirst();
        return optionalUser.map(x -> Response
                .status(Response.Status.OK)
                .entity(optionalUser.get())
                .build())
                .orElse(Response
                        .status(Response.Status.NOT_FOUND)
                        .build());
    }
}
