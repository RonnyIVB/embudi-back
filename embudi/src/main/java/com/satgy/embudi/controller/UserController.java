package com.satgy.embudi.controller;

import com.satgy.embudi.model.User;
import com.satgy.embudi.service.UserServiceI;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserServiceI service;

//    @GetMapping
//    public ResponseEntity<List<User>> findAll() {
//        return ResponseEntity.ok(service.findAll());
//    }

    @GetMapping("/myself/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id) {
        // I use 'myself' because I want to use this only by de another MS.
        return (ResponseEntity<User>) service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<User> findById(@PathVariable("uuid") String uuid) {
        return service.findByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable("email") String email) {
        return service.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    // This code is in UserPasswordController too.
//    @PostMapping
//    public ResponseEntity<User> create(@Valid @RequestBody User user){
//        User u = service.findByEmail(user.getEmail());
//        if (u == null) return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
//        else return Funciones.getResponse("Correo ya existe", "El correo " + user.getEmail() + " ya está utilizado", HttpStatus.BAD_REQUEST);
//    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user) {
        Optional<User> ou = service.findByUuid(user.getUuid());
        return ou.map(u ->
                ResponseEntity.ok(service.update(user)))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<User> delete(@PathVariable("uuid") String uuid) {
        return (ResponseEntity<User>) service.findByUuid(uuid)
                .map(i -> {
                    service.delete(i.getUserId());
                    return ResponseEntity.ok(i);
                })
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
