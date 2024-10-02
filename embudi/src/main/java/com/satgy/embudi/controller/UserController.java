package com.satgy.embudi.controller;

import com.satgy.embudi.general.Funciones;
import com.satgy.embudi.model.User;
import com.satgy.embudi.service.UserServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceI service;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id) {
        return (ResponseEntity<User>) service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<User> findById(@PathVariable("uuid") String uuid) {
        User u = service.findByUuid(uuid);
        if (u == null) return ResponseEntity.notFound().build(); // Funciones.getResponse("Not found", "The Uuid " + uuid + " doesn't exist");
        else return ResponseEntity.ok(u);
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user){
        User u = service.findByEmail(user.getEmail());
        if (u == null) return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
        else return Funciones.getResponse("Correo ya existe", "El correo " + user.getEmail() + " ya está utilizado");
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user){
        return (ResponseEntity<User>) service.findById(user.getUserId())
                .map(i -> ResponseEntity.status(HttpStatus.CREATED).body(service.update(user)))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") Long id){
        return (ResponseEntity<User>) service.findById(id)
                .map(i -> {
                    service.delete(id);
                    return ResponseEntity.ok(i);
                })
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
