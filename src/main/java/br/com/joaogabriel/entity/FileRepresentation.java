package br.com.joaogabriel.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_file_upload")
public class FileRepresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String s3Key;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Request request;

    public FileRepresentation() {}

    public FileRepresentation(String filename, String contentType, String s3Key, Request request) {
        this.filename = filename;
        this.contentType = contentType;
        this.s3Key = s3Key;
        this.request = request;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
