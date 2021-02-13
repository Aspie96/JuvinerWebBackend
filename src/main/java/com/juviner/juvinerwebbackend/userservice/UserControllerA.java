package com.juviner.juvinerwebbackend.userservice;

import com.nimbusds.jose.jwk.JWKSet;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserControllerA {
    private final UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserControllerA(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        Optional<User> user = this.userDao.findByUsername(username);
        return user.get();
    }
    
    @Autowired
    private JWKSet jwkSet;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        return this.jwkSet.toJSONObject();
    }
    
    /*@PostMapping("/login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password) {
        Optional<User> user = this.userDao.findByUsername(username);
        System.out.println(username + password);
        if(user.isPresent()) {
            System.out.println(username + password);
            System.out.print(user.get().getPassword());
            if(this.encoder.matches(user.get().getPassword(), password)) {
                return new ResponseEntity(Jwts.builder()
                    .setSubject(user.get().getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, "demoyugijhuijkbkhuyhbvgyuhjihubgvyjihubvgyjiokpdemoyugijhuijkbkhuyhbvgyuhjihubgvyjihubvgyjiokp")
                    .compact(), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }*/
    
    @GetMapping("/self")
    public User getDetails(Authentication auth) {
        User u = this.userDao.findByUsername(auth.getName()).get();
        return u;
    }
    
    @PutMapping("/self")
    public User postDetails(Authentication auth, @RequestBody Map<String, Object> body) {
        User old = this.userDao.findByUsername(auth.getName()).get();
        User newU = new User(old.getId(), old.getUsername(), (String)body.get("description"), old.getEmail(), old.getPassword(), old.getAvatar(), old.getGithub());
        User u = this.userDao.save(newU);
        return u;
    }
    
    @PostMapping("/register")
    public User register(@RequestBody Map<String, Object> body) {
        User user = new User((String)body.get("username"), (String)body.get("description"), (String)body.get("email"), passwordEncoder.encode((String)body.get("password")), null, null);
        return this.userDao.save(user);
    }
}
