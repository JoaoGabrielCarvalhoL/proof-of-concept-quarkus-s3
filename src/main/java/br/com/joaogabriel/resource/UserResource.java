package br.com.joaogabriel.resource;

import br.com.joaogabriel.entity.User;
import br.com.joaogabriel.payload.user.UserPost;
import br.com.joaogabriel.service.UserService;
import br.com.joaogabriel.utils.UriUtils;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    public Response save(@Valid UserPost userPost) {
        User saved = this.userService.save(userPost);
        return Response.created(UriUtils.genereateURI("users", saved.getId()))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(this.userService.findById(id))
                .build();
    }
}
