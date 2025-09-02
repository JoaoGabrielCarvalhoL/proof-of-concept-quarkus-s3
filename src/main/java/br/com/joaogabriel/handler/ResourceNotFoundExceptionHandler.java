package br.com.joaogabriel.handler;

import br.com.joaogabriel.exception.ResourceNotFoundException;
import br.com.joaogabriel.handler.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionHandler implements ExceptionMapper<ResourceNotFoundException> {

    @Override
    public Response toResponse(ResourceNotFoundException exception) {
        return Response.status(Response.Status.BAD_GATEWAY)
                .entity(new ErrorResponse(
                        "ResourceNotFoundException",
                        exception.getMessage(),
                        Response.Status.BAD_REQUEST.getStatusCode()))
                .build();
    }
}
