package _4.aeye.rest;

import org.springframework.web.bind.annotation.RestController;
import _4.aeye.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/users")
public class UsersDataController {

    private final UserService userService;

    UsersDataController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/phones")
    public ResponseEntity<?> getPhones() {
        return ResponseEntity.ok(userService.getAllPhoneNumbers());
    }
    
}
