package _4.aeye.services;

import _4.aeye.dtos.RegisterRequest;
import _4.aeye.dtos.UpdateUserRequest;
import _4.aeye.entites.Role;
import _4.aeye.entites.User;
import _4.aeye.rep.RoleRepository;
import _4.aeye.rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

        @Override

        public User createUser(RegisterRequest request) {

            if (userRepository.findByUsername(request.getUsername()).isPresent()) {

                throw new RuntimeException("Username already exists");

            }

    

            if (request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty()) {

                throw new RuntimeException("Phone number is required");

            }

    

            if (!request.getPassword().equals(request.getConfirmPassword())) {

                throw new RuntimeException("Passwords do not match");

            }

    

            User user = new User();

            user.setUsername(request.getUsername());

            user.setPhoneNumber(request.getPhoneNumber());

            user.setPassword(passwordEncoder.encode(request.getPassword()));

            user.setEnabled(true);

    

            // Assign default USER role

            Role userRole = roleRepository.findByName("USER")

                    .orElseThrow(() -> new RuntimeException("Default role not found"));

            user.setRoleId(userRole.getId());

    

            return userRepository.save(user);

        }

    

        @Override

        public User registerUser(RegisterRequest request) {

            return createUser(request);

        }

    

        @Override

        public User getUserByUsername(String username) {

            return userRepository.findByUsername(username)

                    .orElseThrow(() -> new RuntimeException("User not found"));

        }

    

        @Override

        public List<String> getAllPhoneNumbers(){

            return userRepository.getAllPhoneNumbers();

        }

    

        @Override

        public List<User> getAllUsers() {

            return userRepository.findAll();

        }

    

        @Override

        public User getUserById(Long id) {

            return userRepository.findById(id)

                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        }

    

        @Override

        public void deleteUser(Long id) {

            if (!userRepository.existsById(id)) {

                throw new RuntimeException("User not found with id: " + id);

            }

            userRepository.deleteById(id);

        }

    

        @Override

        public User updateUser(Long id, UpdateUserRequest request) {

            User user = getUserById(id);

            user.setUsername(request.getUsername());

            user.setPhoneNumber(request.getPhoneNumber());

            return userRepository.save(user);

        }

    

        @Override

        public User updateUserRole(Long id, String roleName) {

            User user = getUserById(id);

            Role role = roleRepository.findByName(roleName)

                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

            user.setRoleId(role.getId());

            return userRepository.save(user);

        }

    }
