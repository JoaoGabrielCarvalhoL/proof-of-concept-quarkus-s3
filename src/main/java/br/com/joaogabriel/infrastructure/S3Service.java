package br.com.joaogabriel.infrastructure;

import java.io.File;
import br.com.joaogabriel.entity.Request;

import org.jboss.resteasy.reactive.multipart.FileUpload; // For RESTEasy Reactive
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;


import java.io.IOException;

import java.net.URI;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class S3Service {

    private final S3Client s3Client;
    private final String bucket;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public S3Service(@ConfigProperty(name = "aws.s3.bucket.name") String bucket) {
        this.bucket = bucket;
        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create("http://127.0.0.1:4566"))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("key", "key")))
                .build();
    }

    public String upload(FileUpload fileUpload, Request request) {
        String key = String.format("uploads/%s/%s/%s", request.getUser().getId(),
                request.getId(), fileUpload.name());
        PutObjectRequest objectRequest = PutObjectRequest
                .builder()
                .bucket(this.bucket)
                .key(key)
                .contentType(fileUpload.contentType())
                .build();

        this.s3Client.putObject(objectRequest, RequestBody.fromFile(fileUpload.uploadedFile()));
        logger.log(Level.INFO, "File uploaded to Amazon S3. Key: {0}", key);
        return key;
    }


    private void clearTemporaryFile(File file){
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException exception) {
            logger.log(Level.WARNING, "File cannot be deleted. Reason: {0}. Path: {1}",
                    new Object[] {exception.getMessage(), file.getAbsolutePath()});
        }

    }

    public List<String> getBucketNames() {
        return s3Client.listBuckets()
                .buckets()
                .stream()
                .map(b -> b.name())
                .collect(Collectors.toList());
    }


    private void createBucket() {
        try {
            boolean exists = s3Client.listBuckets().buckets().stream()
                    .anyMatch(b -> b.name().equals(this.bucket));
            if (!exists) {
                s3Client.createBucket(b -> b.bucket(this.bucket));
                logger.log(Level.INFO,"Bucket created: {0}", this.bucket);
            } else {
                logger.log(Level.INFO, "Bucket already exists: {0}", this.bucket);
            }
        } catch (S3Exception e) {
            throw new RuntimeException("Failed to create bucket: " + e.awsErrorDetails().errorMessage(), e);
        }
    }



}
