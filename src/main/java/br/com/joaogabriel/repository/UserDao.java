package br.com.joaogabriel.repository;

import br.com.joaogabriel.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UserDao {

    private final EntityManager entityManager;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public User save(User user) {
        logger.log(Level.INFO, "Saving user: {0} into database.", user);
        this.entityManager.persist(user);
        return user;
    }

    public List<User> findAllWithRequests() {
        this.logger.log(Level.INFO, "Getting all requests");
        return entityManager.createQuery(
                        "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.requests", User.class)
                .getResultList();
    }

    public User findById(UUID id) {
        this.logger.log(Level.INFO, "Getting request by id: {0}", id);
        return entityManager.find(User.class, id);
    }
}
