package br.com.joaogabriel.repository;

import br.com.joaogabriel.entity.FileRepresentation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class FileRepresentationDao {

    private final EntityManager entityManager;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public FileRepresentationDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public FileRepresentation save(FileRepresentation request) {
        logger.log(Level.INFO, "Saving file: {0} into database.", request);
        this.entityManager.persist(request);
        return request;
    }

    public FileRepresentation findById(UUID id) {
        return Optional.ofNullable(this.entityManager.find(FileRepresentation.class, id))
                .orElseThrow(() -> new RuntimeException("File not found into database. Id: " + id));
    }
}
