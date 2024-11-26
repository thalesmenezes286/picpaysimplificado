package com.dev.picpaysimplificado.services;

import com.dev.picpaysimplificado.dto.UserDTO;
import com.dev.picpaysimplificado.entities.User;
import com.dev.picpaysimplificado.entities.UserType;
import com.dev.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuário do tipo logista não está autorizado a realizar transações");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Usuário insuficiente");
        }
    }

    public User findById(Long id) throws Exception{
        return this.userRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado!"));
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }

    public User createUser(UserDTO userDTO){
        var newUser = new User(userDTO);
        this.saveUser(newUser);

        return newUser;
    }

    public List<User> getAllUsers() {
       return this.userRepository.findAll();
    }
}
