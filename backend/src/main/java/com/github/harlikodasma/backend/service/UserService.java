package com.github.harlikodasma.backend.service;

import com.github.harlikodasma.backend.bean.UserQueryReturn;
import com.github.harlikodasma.backend.model.User;
import com.github.harlikodasma.backend.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

import static io.jsonwebtoken.Jwts.*;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    public UserService() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<String> registerUser(User user) {
        if(user.getEmail() == null) {
            return new ResponseEntity<>("E-mail ei saa olla tühi!", HttpStatus.LENGTH_REQUIRED);
        }
        if(user.getPasswordHash() == null || user.getPasswordHash().length() < 6) {
            return new ResponseEntity<>("Parool peab olema vähemalt 6 tähemärki pikk!", HttpStatus.LENGTH_REQUIRED);
        }
        if(userRepository.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("Selle e-mailiga on juba kasutaja loodud!", HttpStatus.ALREADY_REPORTED);
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        User newUser = userRepository.saveAndFlush(user);
        return new ResponseEntity<>(newUser.getId().toString(), HttpStatus.OK);
    }

    // returning the same entity for wrong email and password to confuse random triers and bruteforce
    public ResponseEntity<JSONObject> loginUser(User user) {
        if(userRepository.findByEmail(user.getEmail()) == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // wrong e-mail
        } else {
            String password = userRepository.findByEmail(user.getEmail()).getPasswordHash();
            if(passwordEncoder.matches(user.getPasswordHash(), password)) {
                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

                long millisecondsNow = System.currentTimeMillis();
                Date dateNow = new Date(millisecondsNow);

                byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
                Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

                JwtBuilder builder = builder()
                        .setIssuedAt(dateNow)
                        .setSubject(user.getEmail())
                        .signWith(signingKey);

                // token is set to expire after 24 hours
                long expirationMilliseconds = millisecondsNow + 86400000;
                Date expirationDate = new Date(expirationMilliseconds);
                builder.setExpiration(expirationDate);

                JSONObject json = new JSONObject();
                User loggedInUser = userExists(user);

                json.put("id", loggedInUser.getId());
                json.put("email", loggedInUser.getEmail());
                json.put("token", builder.compact());
                json.put("businessClientAccount", loggedInUser.isBusinessClientAccount());
                json.put("admin", loggedInUser.isAdmin());

                return new ResponseEntity<>(json, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // wrong password
            }
        }
    }

    public User userExists(User user) {
        return userRepository.findByEmail(user.getEmail());
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUserByID(Long id) {
        return userRepository.findByID(id);
    }

    public List<UserQueryReturn> findUserStats() {
        return userRepository.userStats();
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(token).getBody();
            return true;
        } catch(UnsupportedJwtException | MalformedJwtException | SignatureException | ExpiredJwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
