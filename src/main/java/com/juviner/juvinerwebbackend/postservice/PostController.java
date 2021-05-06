package com.juviner.juvinerwebbackend.postservice;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PostController {
    private final PostDao postDao;
    
    @Autowired
    private RabbitTemplate template;
    private final Queue createdQueue;
    private final Queue deletedQueue;
    
    @RabbitListener(queues="thread-created")
    public void receive(HashMap<String, Object> message) {
        Post post = new Post((Integer)message.get("thread_id"), (String)message.get("username"), (String)message.get("text"), new Timestamp((Long)message.get("time")));
        this.postDao.save(post);
    }
    
    @RabbitListener(queues="thread-deleted")
    @Transactional
    public void receive(Integer threadId) {
        this.postDao.deleteByThreadId(threadId);
    }
    
    public PostController(PostDao postDao) {
        this.postDao = postDao;
        this.createdQueue = new Queue("post-created");
        this.deletedQueue = new Queue("post-deleted");
    }
    
    @GetMapping("/thread/{threadId}")
    public List<Post> getUser(@PathVariable int threadId) {
        List<Post> posts = this.postDao.findByThreadIdOrderByTimeAsc(threadId);
        return posts;
    }
    
    @PostMapping("/thread/{threadId}")
    @Transactional
    public ResponseEntity post(Authentication auth, @PathVariable int threadId, @RequestBody Map<String, Object> body) {
        if(this.postDao.existsByThreadId(threadId)) {
            long time = System.currentTimeMillis();
            Post post = new Post(threadId, auth.getName(), (String)body.get("text"), new Timestamp((Long)time));
            this.postDao.save(post);
            HashMap<String, Object> message = new HashMap<>();
            message.put("thread_id", threadId);
            message.put("time", time);
            template.convertAndSend(this.createdQueue.getName(), message);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(Authentication auth, @PathVariable int id) {
        Optional<Post> post = this.postDao.findById(id);
        if(post.isPresent()) {
            if(auth.getName().equals(post.get().getUsername())) {
                if(this.postDao.findFirstByThreadIdOrderByTimeAsc(post.get().getThreadId()).get().getId() == post.get().getId()) {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                } else {
                    this.postDao.delete(post.get());
                    HashMap<String, Object> message = new HashMap<>();
                    message.put("thread_id", post.get().getThreadId());
                    message.put("time", (long)this.postDao.findFirstByThreadIdOrderByTimeDesc(post.get().getThreadId()).get().getTime().getTime());
                    template.convertAndSend(this.deletedQueue.getName(), message);
                    System.out.println("Del post");
                    return new ResponseEntity(HttpStatus.OK);
                }
            } else {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
