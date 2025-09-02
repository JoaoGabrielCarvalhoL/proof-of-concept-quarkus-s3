package br.com.joaogabriel.resource;

import br.com.joaogabriel.infrastructure.S3Service;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/s3")
@Produces(MediaType.APPLICATION_JSON)
public class S3HealthCheckResource {

    private final S3Service s3Service;

    public S3HealthCheckResource(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GET
    public Response checkBucket() {
        return Response.ok(this.s3Service.getBucketNames())
                .build();
    }
}
