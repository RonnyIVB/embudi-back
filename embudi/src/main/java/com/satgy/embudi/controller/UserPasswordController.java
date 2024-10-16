package com.satgy.embudi.controller;

import com.satgy.embudi.dto.AuthResponseDto;
import com.satgy.embudi.dto.Login;
import com.satgy.embudi.general.Email;
import com.satgy.embudi.general.Funciones;
import com.satgy.embudi.general.Str;
import com.satgy.embudi.model.User;
import com.satgy.embudi.model.UserPassword;
import com.satgy.embudi.security.JwtTokenProvider;
import com.satgy.embudi.service.UserPasswordServiceImp;
import com.satgy.embudi.service.UserServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.satgy.embudi.general.Par.SIGN_UP_URL;

@Controller
@RequestMapping(SIGN_UP_URL)
public class UserPasswordController {

    @Autowired
    private AuthenticationManager authenticationManager;
    //private PasswordEncoder passwordEncoder;
    @Autowired
    private UserPasswordServiceImp userPasswordService;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /*@Autowired
    public UserPasswordController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserPasswordServiceImp userPasswordService, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userPasswordService = userPasswordService;
        this.tokenProvider = tokenProvider;
    }*/

    private static final Logger log = LoggerFactory.getLogger(UserPasswordController.class);

    @Autowired
    private UserServiceI service;
    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable("email") String email) {
        return service.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody Login login) {
        if (Str.esNulo(login.getEmail()))
            return Funciones.getResponse("Error en correo", "Por favor ingresa el correo", HttpStatus.BAD_REQUEST);
        else if (Str.esNulo(login.getPassword()))
            return Funciones.getResponse("Error en contraseña", "Por favor ingresa la contraseña", HttpStatus.BAD_REQUEST);
        else {
            // check if the user exists
            List<UserPassword> ucList = userPasswordService.findByEmail(login.getEmail());
            if (ucList.isEmpty()) {
                //log.error("Else: Entry to a Service Create Post. Login:" + login);
                UserPassword up = userPasswordService.create(login);

                //new Email().sendEmail(login.getEmail(), "Welcome to Embudi", "You are sign up on Embudi");
                return new ResponseEntity<>(up.getUser(), HttpStatus.CREATED);
            } else {
                return Funciones.getResponse("Cambiar el correo", "El correo ya existe", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> logIn(@RequestBody Login login) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    login.getEmail(), login.getPassword()
            ));
        SecurityContextHolder.getContext().setAuthentication(auth);
        // Here the credentials were validated and the process is within the security context of the application
        String token = tokenProvider.tokenGenerate(auth);
        return service.findByEmail(login.getEmail())
                .map(user -> ResponseEntity.ok(
                        new AuthResponseDto(token, user.getUuid()))
                )
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
