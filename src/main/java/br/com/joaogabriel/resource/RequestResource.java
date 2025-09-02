package br.com.joaogabriel.resource;

import br.com.joaogabriel.entity.Request;
import br.com.joaogabriel.payload.request.RequestPost;
import br.com.joaogabriel.service.RequestService;
import br.com.joaogabriel.utils.UriUtils;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/requests")
@Consumes(MediaType.APPLICATION_JSON)
public class RequestResource {

    private final RequestService requestService;

    public RequestResource(RequestService requestService) {
        this.requestService = requestService;
    }

    @POST
    @Path("/{userId}")
    public Response save(@PathParam("userId") UUID userId, @Valid RequestPost requestPost) {
        Request saved = this.requestService.save(requestPost, userId);
        return Response.created(UriUtils.genereateURI("requests", saved.getId()))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(this.requestService.findById(id))
                .build();
    }
}
