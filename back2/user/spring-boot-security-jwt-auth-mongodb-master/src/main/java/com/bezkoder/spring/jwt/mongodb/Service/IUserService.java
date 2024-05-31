package com.bezkoder.spring.jwt.mongodb.Service;

import com.bezkoder.spring.jwt.mongodb.models.User;

import java.util.List;

public interface IUserService {


     List<User> getAllUsers();

     public boolean deleteUser(String id);


     User retrieveUser(String id);
     User updateUser(String id, User user);
}