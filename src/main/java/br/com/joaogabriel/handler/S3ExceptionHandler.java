package br.com.joaogabriel.handler;

import br.com.joaogabriel.handler.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Provider
public class S3ExceptionHandler implements ExceptionMapper<S3Exception> {

    @Override
    public Response toResponse(S3Exception exception) {
        return Response.status(Response.Status.BAD_GATEWAY)
                .entity(new ErrorResponse(
                        "S3 Exception",
                        "Error on S3 service:" + exception.getMessage(),
                        Response.Status.BAD_GATEWAY.getStatusCode()))
                .build();
    }
}
