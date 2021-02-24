package com.juviner.juvinerwebbackend.userservice;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {
    public Optional<User> findByUsername(String username);
}