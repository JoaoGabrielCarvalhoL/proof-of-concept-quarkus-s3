package br.com.joaogabriel.service;

import br.com.joaogabriel.entity.FileRepresentation;
import br.com.joaogabriel.entity.Request;
import br.com.joaogabriel.exception.ResourceNotFoundException;
import br.com.joaogabriel.infrastructure.S3Service;
import br.com.joaogabriel.repository.FileRepresentationDao;
import br.com.joaogabriel.repository.RequestDao;
import org.jboss.resteasy.reactive.multipart.FileUpload; // For RESTEasy Reactive
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class FileUploadService {

    private final S3Service s3Service;
    private final FileRepresentationDao fileRepresentationDao;
    private final RequestDao requestDao;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public FileUploadService(S3Service s3Service, FileRepresentationDao fileRepresentationDao, RequestDao requestDao) {
        this.s3Service = s3Service;
        this.fileRepresentationDao = fileRepresentationDao;
        this.requestDao = requestDao;
    }

    public String upload(FileUpload fileUpload, UUID requestId) {
        String filename = fileUpload.fileName();
        String contentType = fileUpload.contentType();

        Request request = Optional.ofNullable(this.requestDao.findById(requestId))
                .orElseThrow(() -> new ResourceNotFoundException("Request not found into database. Id: " + requestId));

        String key = this.s3Service.upload(fileUpload, request);
        FileRepresentation fileRepresentation = new FileRepresentation(filename, contentType, key, request);
        this.fileRepresentationDao.save(fileRepresentation);
        logger.log(Level.INFO, "Saving file representation: {0} into database.", fileRepresentation);
        return key;
    }
}
