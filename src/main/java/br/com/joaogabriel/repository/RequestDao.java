package br.com.joaogabriel.repository;

import br.com.joaogabriel.entity.Request;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class RequestDao {

    private final EntityManager entityManager;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public RequestDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Request save(Request request) {
        logger.log(Level.INFO, "Saving request: {0} into database.", request);
        this.entityManager.persist(request);
        return request;
    }

    public Request findById(UUID id) {
        return Optional.ofNullable(this.entityManager.find(Request.class, id))
                .orElseThrow(() -> new RuntimeException("Request not found into database. Id: " + id));
    }

    public List<Request> findAll() {
        return this.entityManager.createQuery(
                        "SELECT r FROM Request r JOIN FETCH r.user", Request.class)
                .getResultList();
    }



}
