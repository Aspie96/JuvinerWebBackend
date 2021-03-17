package com.juviner.juvinerwebbackend.userservice;

import com.nimbusds.jose.jwk.JWKSet;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@RestController
@RequestMapping
public class UserControllerA {
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserControllerA(UserDao userDao) {
        this.userDao = userDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        System.out.println(username);
        Optional<User> user = this.userDao.findByUsername(username);
        return user.get();
    }
    
    @Autowired
    private JWKSet jwkSet;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        return this.jwkSet.toJSONObject();
    }
    
    @GetMapping("/self")
    public User getDetails(Authentication auth) {
        User u = this.userDao.findByUsername(auth.getName()).get();
        return u;
    }
    
    @PutMapping("/self")
    public User postDetails(Authentication auth, @RequestBody Map<String, Object> body) {
        User old = this.userDao.findByUsername(auth.getName()).get();
        User newU = new User(old.getId(), old.getUsername(), (String)body.get("description"), old.getEmail(), old.getPassword(), old.getAvatar(), null, null);
        User u = this.userDao.save(newU);
        return u;
    }
   
    @PostMapping("/register")
    public User register(@RequestBody Map<String, Object> body) {
        User user = new User((String)body.get("username"), (String)body.get("description"), (String)body.get("email"), passwordEncoder.encode((String)body.get("password")), null, null);
        return this.userDao.save(user);
    }
    
    @GetMapping(value="/default_avatar")
    public ResponseEntity<InputStreamResource> getAvatar() throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(new ClassPathResource("nerd-pngrepo-com.png").getInputStream()));
    }
    
    @GetMapping(value="{username}/avatar")
    public Mono<ResponseEntity<byte[]>> getAvatar(@PathVariable String username) throws NoSuchAlgorithmException {
        String email = userDao.findByUsername(username).get().getEmail();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(email.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest);
        WebClient client = WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(
                HttpClient.create().followRedirect(true)
        )).build();
        Mono<ResponseEntity<byte[]>> response = client.get()
            .uri("https://www.gravatar.com/avatar/" + myHash + "?d=404")
            .exchangeToMono(res -> {
                Mono<ResponseEntity<byte[]>> result;
                System.out.println("C");
                if(res.statusCode() == HttpStatus.NOT_FOUND) {
                    System.out.println(myHash);
                    System.out.println("D");
                    result = client.get()
                        .uri("https://cdn.libravatar.org/avatar/" + myHash.toLowerCase() + "?d=404").exchangeToMono(res1 -> {
                            Mono<ResponseEntity<byte[]>> result1;
                            System.out.println(res1.statusCode());
                            if(res1.statusCode() == HttpStatus.NOT_FOUND) {
                                result1 = Mono.just(ResponseEntity.status(HttpStatus.FOUND).location(URI.create("../default_avatar")).build());
                            } else {
                                result1 = res1.bodyToMono(byte[].class).map(array -> ResponseEntity.ok().contentType(res1.headers().contentType().get()).body(array));
                            }
                            return result1;
                        });
                } else {
                    System.out.println("D" + res.statusCode());
                    result = res.bodyToMono(byte[].class).map(array -> ResponseEntity.ok().contentType(res.headers().contentType().get()).body(array));
                }
                return result;
            });
        return response;
    }
}
