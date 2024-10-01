package com.satgy.embudi.service;

import com.satgy.embudi.dto.Login;
import com.satgy.embudi.general.Email;
import com.satgy.embudi.general.Str;
import com.satgy.embudi.model.User;
import com.satgy.embudi.model.UserPassword;
import com.satgy.embudi.repository.UserPasswordRepositoryI;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserPasswordServiceImp implements UserPasswordServiceI, UserDetailsService {

    @Autowired
    private UserPasswordRepositoryI passwordRepo;

    @Autowired
    private UserServiceI userService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public UserPassword validate(String email, String password) {
        Query q = em.createQuery("select u from UserPassword u where u.user.email = '" + email + "' and u.password = '" + password + "'");
        List <UserPassword> users = q.getResultList();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String resetPassword(String email) {
        User user = userService.findByEmail(email.toLowerCase().trim());
        if (user != null) {
            String password = Str.getAlfanumericoAleatorio(8);
            String codificada = encoder.encode(password);
            passwordRepo.setPassword(user.getUserid(), codificada);

            String mensaje = "Se ha actualizado la contraseña:\n" + Str.asciiString(13) + password;
            new Email().sendEmail(user.getEmail(), "Reestablecer contraseña", mensaje);
            return "Se ha enviado el email";
        } else {
            return "No existe ese email";
        }
    }

    // To be able to inject this class, I must to add the bean in the class WebSecurity
    // public AuthenticationManager authenticationManagerBean()
    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;

    @Override
    public String changePassword(Login login) {
        if (login.getPassword().equals(login.getNewPassword())) { return "las contraseñas actual y nueva deben ser diferentes"; }
        String emailLimpio = login.getEmail().toLowerCase().trim();
        User user = userService.findByEmail(emailLimpio);
        if (user == null) { return "No existe el email"; }

        try{
            Authentication a = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), login.getPassword(), new ArrayList<>()));
            if (a.isAuthenticated()) {
                String codificada = encoder.encode(login.getNewPassword());
                passwordRepo.setPassword(user.getUserid(), codificada);
                String mensaje = "Se ha cambiado la contraseña\n" + Str.asciiString(13);
                new Email().sendEmail(user.getEmail(), "Cambio de contraseña", mensaje);
                return "ok";
            } else {
                return "Ocurrió un error, intente nuevamente";
            }
        } catch(Exception e) {
            return "La contraseña actual está incorrecta";
        }
    }

    @Override
    public Optional<UserPassword> findById(Long userpasswordId) {
        return passwordRepo.findById(userpasswordId);
    }

    @Override
    public List<UserPassword> findByEmail(String email) {
        return passwordRepo.findByEmail(email);
    }

    @Override
    public UserPassword create(Login login) {
        // first create User and after insert (create) UserPassword
        User user = new User();
        user.setEmail(login.getEmail());
        user.setEnable(true);
        user.setEntryDate(new Date());
        user.setLastEntryDate(new Date());
        user = userService.create(user);

        UserPassword userPassword = new UserPassword();
        userPassword.setUserpasswordId(user.getUserid()); // este campo PK no es autogenerado, le pongo el iduser
        userPassword.setPassword(encoder.encode(login.getPassword()));
        userPassword.setUser(user);
        return passwordRepo.save(userPassword);
    }

    @Override
    public UserPassword update(UserPassword userPassword) {
        return passwordRepo.save(userPassword);
    }

    @Override
    public void delete(Long id) {
        passwordRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        // buscar si existe el email
        List<UserPassword> upList = passwordRepo.findByEmail(email);
        //System.out.println("UNO");
        // si no encuentra el email lanzar una excepción
        if (upList.size() == 0) throw new UsernameNotFoundException(email);
        //System.out.println("DOS");

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ArrayList credentials = new ArrayList<>();
        UserPassword uc = upList.get(0); // tomo el primer user
        //User u = new User(email, uc.getPassword(), credentials);
        //System.out.println("u.isEnabled() : " + u.isEnabled());
        //for (GrantedAuthority ga: u.getAuthorities()){
        //System.out.println("authority: " + ga.getAuthority());
        //}
        return new org.springframework.security.core.userdetails.User(email, uc.getPassword(), credentials);
    }
}
