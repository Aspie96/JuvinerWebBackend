package com.juviner.juvinerweb.threadservice;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ThreadController {
    private final ThreadDao threadDao;

    @Autowired
    private RabbitTemplate template;
    private Queue createdQueue;
    private Queue deletedQueue;
    
    public ThreadController(ThreadDao threadDao) {
        this.threadDao = threadDao;
        this.createdQueue = new Queue("thread-created");
        this.deletedQueue = new Queue("thread-deleted");
    }

    @GetMapping("/{id}")
    public Thread getThread(@PathVariable int id) {
        Optional<Thread> thread = this.threadDao.findById(id);
        return thread.get();
    }

    @GetMapping("/category/{categoryId}")
    public List<Thread> getByCategory(@PathVariable int categoryId) {
        List<Thread> list = this.threadDao.findByCategoryId(categoryId);
        return list;
    }

    @PostMapping("/")
    public ResponseEntity postThread(Authentication auth, @RequestBody Map<String, Object> body) {
        Thread thread = new Thread((String)body.get("title"), (int)body.get("categoryId"), auth.getName(), (String)body.get("text"));
        thread = this.threadDao.save(thread);
        HashMap<String, Object> message = new HashMap<>();
        message.put("thread_id", thread.getId());
        message.put("username", auth.getName());
        message.put("text", (String)body.get("text"));
        message.put("time", System.currentTimeMillis());
        template.convertAndSend(this.createdQueue.getName(), message);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Location", "/" + thread.getId());
        return new ResponseEntity<Thread>(thread, headers, HttpStatus.CREATED);
    }
    
    @PostMapping("/demo")
    public void d() {
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteThread(@RequestHeader String auth, @PathVariable int id) {
        if(auth != null) {
            Optional<Thread> thread = this.threadDao.findById(id);
            if(thread.isPresent()) {
                if(thread.get().getUsername().equals(auth)) {
                    this.threadDao.deleteById(id);
                    template.convertAndSend(this.deletedQueue.getName(), id);
                    return new ResponseEntity(HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
}
