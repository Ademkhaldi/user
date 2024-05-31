package com.bezkoder.spring.jwt.mongodb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bezkoder.spring.jwt.mongodb.models.User;


public interface UserRepository extends MongoRepository<User, String> {
  /*Déclaration d'une méthode personnalisée findByUsername pour rechercher un utilisateur par son nom d'utilisateur.
Retourne un Optional<User>*/
  Optional<User> findByUsername(String username);
/*Déclaration d'une méthode existsByUsername pour vérifier l'existence d'un utilisateur par son nom d'utilisateur.
Retourne un Boolean.
java*/
  Boolean existsByUsername(String username);
/*  Boolean existsByEmail(String email);
}
*/
  Boolean existsByEmail(String email);
}
