package com.juviner.juvinerwebbackend.postservice;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PostDao extends CrudRepository<Post, Integer> {
    List<Post> findByThreadId(int threadId);
    public void deleteByThreadId(int threadId);
}
