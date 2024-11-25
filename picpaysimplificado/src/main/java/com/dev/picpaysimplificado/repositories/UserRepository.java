package com.dev.picpaysimplificado.repositories;

import com.dev.picpaysimplificado.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Buscar usuários pelo Documento informado no cadastro
    Optional<User> findUserByDocument(String document);

    //Buscar usuários pelo seu respectivo Id
    Optional<User> findUserById(Long id);
}
