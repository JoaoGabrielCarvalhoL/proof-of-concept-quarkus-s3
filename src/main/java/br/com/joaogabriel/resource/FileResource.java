package br.com.joaogabriel.resource;

import br.com.joaogabriel.service.FileUploadService;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload; // For RESTEasy Reactive
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.UUID;

@Path("/users/{requestId}/files")
public class FileResource {

    private final FileUploadService fileUploadService;

    public FileResource(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
                               @PathParam("requestId") UUID requestId,
                               @RestForm FileUpload file) {
       String key = fileUploadService.upload(file, requestId);
       return Response.status(Response.Status.CREATED)
               .entity(key).build();
    }
}
