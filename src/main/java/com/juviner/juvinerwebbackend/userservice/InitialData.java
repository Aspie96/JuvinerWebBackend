package com.juviner.juvinerwebbackend.userservice;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitialData {
    @Autowired
    private UserDao userDao;
    
    @PostConstruct
    public void runAfterObjectCreated() {
        if(this.userDao.count() == 0) {
            this.userDao.save(new User("Valentino", "L'intelligenza di una piastrella mediamente scema.", "valentino.giudice@edu.unito.it", "$2a$10$57WuuCg0qta6UQoC3qbBhuhcKP5e/iHzCMTl5jIYlkvdEWIlzMXoO", 13873909, "Aspie96", "112497205996347030908"));
            this.userDao.save(new User("Andrea", "yo", "andrea.cattaneo418@edu.unito.it", "$2a$10$.CeUBhi7UlJLVhdMq9Uoee29nKaYuH56GVxUuG5FJqmddRAdhY9lK", 0, null, null));
        }
    }

}
