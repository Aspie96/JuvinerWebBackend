package com.juviner.juvinerwebbackend.postservice;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PostController {
    private final PostDao postDao;
    
    @RabbitListener(queues="thread-created")
    public void receive(HashMap<String, Object> message) {
        Post post = new Post((Integer)message.get("thread_id"), (String)message.get("username"), (String)message.get("text"), new Timestamp((Long)message.get("time")));
        this.postDao.save(post);
    }
    
    @RabbitListener(queues="thread-deleted")
    public void receive(int threadId) {
        this.postDao.deleteByThreadId(threadId);
    }
    
    public PostController(PostDao postDao) {
        this.postDao = postDao;
    }
    
    @GetMapping("/thread/{threadId}")
    public List<Post> getUser(@PathVariable int threadId) {
        List<Post> posts = this.postDao.findByThreadId(threadId);
        return posts;
    }
    
    @PostMapping("/thread/{threadId}")
    public ResponseEntity post(Authentication auth, @PathVariable int threadId, @RequestBody Map<String, Object> body) {
        Post post = new Post(threadId, auth.getName(), (String)body.get("text"), new Timestamp((Long)System.currentTimeMillis()));
        this.postDao.save(post);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
}
