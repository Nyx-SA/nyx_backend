package com.github.grupo_s.nyx_app.users;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }

    public User getUserById(long id){
        User user = userRepository.findById(id);
        return user;
    }

    public User getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    public User getUserByEmail(String mail){
        Optional<User> user = userRepository.findByEmail(mail);
        return user.orElse(null);
    }

    public User saveUser(User user){

        //Normalizamos el email del usuario
        String normalizedEmail = user.getEmail().replaceAll("\\s+", "").trim().toLowerCase();

        //Normalizamos el nombre de usuario
        String normalizedUsername = user.getUsername().replaceAll("\\s+", " ").trim().toLowerCase();

        if (verifyUsername(normalizedUsername)) {
            if (verifyEmail(normalizedEmail)) {
                user.setUsername(normalizedUsername);
                user.setEmail(normalizedEmail);
                User savedUser = userRepository.save(user);

                return savedUser;
            }
            throw new EntityNotFoundException("Email already exists");
        }
        throw new EntityNotFoundException("Username already exists");
    }

    public User updateUser(long id, User updatedUser) {

        //1. Buscamos el usuario
        User user = userRepository.findById(id);
        //2. Chequeamos que exista
        if (user == null) throw new EntityNotFoundException("User not found");
        //3. Actualizamos el id de los nuevos datos
        updatedUser.setId(user.getId());
        updatedUser.setCreatedAt(user.getCreatedAt());

        //Normalizamos el email del usuario
        String normalizedEmail = updatedUser.getEmail().replaceAll("\\s+", "").trim().toLowerCase();
        updatedUser.setEmail(normalizedEmail);

        //Normalizamos el nombre de usuario
        String normalizedUsername = updatedUser.getUsername().replaceAll("\\s+", " ").trim().toLowerCase();
        updatedUser.setUsername(normalizedUsername);

        if (verifyUsername(updatedUser.getUsername(), updatedUser.getId()) && verifyEmail(updatedUser.getEmail(), updatedUser.getId())) {
            return userRepository.save(updatedUser);
        }
        throw new EntityNotFoundException("Username or mail already exists");
    }

    public void deleteUser(long id){
        User user = this.getUserById(id);

        if (user == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        };
        userRepository.delete(user);
    }

    public boolean verifyUsername(String username){
        List<User> users = this.getAll();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyUsername(String username,long id){
        List<User> users = this.getAll();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getId() != id) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyEmail(String email){
        List<User> users = this.getAll();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyEmail(String email,long id){
        List<User> users = this.getAll();

        for (User user : users) {
            if (user.getEmail().equals(email) && user.getId() != id) {
                return false;
            }
        }
        return true;
    }



}
