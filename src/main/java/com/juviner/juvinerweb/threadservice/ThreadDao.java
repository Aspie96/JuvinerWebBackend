package com.juviner.juvinerweb.threadservice;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ThreadDao extends CrudRepository<Thread, Integer> {
    List<Thread> findByCategoryIdOrderByUpdatetimeDesc(int categoryId);
}
