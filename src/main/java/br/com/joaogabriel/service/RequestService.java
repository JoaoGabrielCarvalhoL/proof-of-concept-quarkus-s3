package br.com.joaogabriel.service;

import br.com.joaogabriel.entity.Request;
import br.com.joaogabriel.entity.User;
import br.com.joaogabriel.exception.ResourceNotFoundException;
import br.com.joaogabriel.payload.request.RequestPost;
import br.com.joaogabriel.repository.RequestDao;
import br.com.joaogabriel.repository.UserDao;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class RequestService {

    private final UserDao userDao;
    private final RequestDao requestDao;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public RequestService(UserDao userDao, RequestDao requestDao) {
        this.userDao = userDao;
        this.requestDao = requestDao;
    }

    public Request save(RequestPost requestPost, UUID userId) {
        User user = Optional.ofNullable(this.userDao.findById(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User not found into database"));
        Request request = new Request(requestPost.description(), user);
        this.logger.log(Level.INFO, "Saving request: {0}, into database.", request);
        return this.requestDao.save(request);
    }

    public Request findById(UUID id) {
        return Optional.ofNullable(this.requestDao.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Request not found into database. Id: " + id));
    }


}
