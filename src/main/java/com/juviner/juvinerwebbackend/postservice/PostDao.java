package com.juviner.juvinerwebbackend.postservice;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PostDao extends CrudRepository<Post, Integer> {
    public List<Post> findByThreadId(int threadId);
    public void deleteByThreadId(int threadId);
    public boolean existsByThreadId(int threadId);
    public Optional<Post> findFirstByThreadId(int threadId);
    public Optional<Post> findLastByThreadId(int threadId);
}
