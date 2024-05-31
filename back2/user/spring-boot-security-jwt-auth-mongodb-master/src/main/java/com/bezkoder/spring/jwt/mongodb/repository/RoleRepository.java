package com.bezkoder.spring.jwt.mongodb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bezkoder.spring.jwt.mongodb.models.ERole;
import com.bezkoder.spring.jwt.mongodb.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);/* pour encapsuler les résultats des méthodes qui peuvent retourner null.
   */

/*Déclaration d'une méthode personnalisée findByName pour rechercher un rôle par son nom (ERole).
Retourne un Optional<Role> pour gérer le cas où le rôle n'existe pas*/
}
