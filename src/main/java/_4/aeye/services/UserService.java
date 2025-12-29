package _4.aeye.services;

import java.util.List;

import _4.aeye.dtos.RegisterRequest;
import _4.aeye.dtos.UpdateUserRequest;
import _4.aeye.entites.User;

public interface UserService {
    User registerUser(RegisterRequest request);
    User getUserByUsername(String username);

    List<String> getAllPhoneNumbers();

    List<User> getAllUsers();

    User getUserById(Long id);

    void deleteUser(Long id);

    User updateUser(Long id, UpdateUserRequest request);
    
    User updateUserRole(Long id, String roleName);

    User createUser(RegisterRequest request);
}
