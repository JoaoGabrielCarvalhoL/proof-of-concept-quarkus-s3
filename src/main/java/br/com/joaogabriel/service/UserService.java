package br.com.joaogabriel.service;

import br.com.joaogabriel.entity.Request;
import br.com.joaogabriel.entity.User;
import br.com.joaogabriel.exception.ResourceNotFoundException;
import br.com.joaogabriel.payload.user.UserPost;
import br.com.joaogabriel.repository.UserDao;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UserService {

    private final UserDao userDao;
    private final Logger logger = Logger.getLogger(getClass().getName());


    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User save(UserPost request) {
        logger.log(Level.INFO, "Saving user: {0} into database.", request);
        return this.userDao.save(new User(request.username()));
    }

    public User findById(UUID id) {
        return Optional.ofNullable(this.userDao.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Request not found into database. Id: " + id));
    }


}
