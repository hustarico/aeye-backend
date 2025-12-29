package _4.aeye.rest;

import _4.aeye.dtos.RegisterRequest;
import _4.aeye.dtos.UpdateRoleRequest;
import _4.aeye.dtos.UpdateUserRequest;
import _4.aeye.dtos.UserDto;
import _4.aeye.entites.User;
import _4.aeye.services.RoleService;
import _4.aeye.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(convertToDto(user));
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody RegisterRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        User updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(convertToDto(updatedUser));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<UserDto> updateUserRole(@PathVariable Long id, @RequestBody UpdateRoleRequest request) {
        User updatedUser = userService.updateUserRole(id, request.getRoleName());
        return ResponseEntity.ok(convertToDto(updatedUser));
    }

    private UserDto convertToDto(User user) {
        String roleName = roleService.getRoleById(user.getRoleId()).getName();
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPhoneNumber(),
                roleName
        );
    }
}
