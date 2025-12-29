package _4.aeye.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping("/load")
    public String test(){
        return "loadedd";
    }

    @GetMapping("/secured")
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    public String securedEndpoint() {
        return "This is a secured endpoint - you must be logged in!";
    }

    @GetMapping("/admin-only")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnlyEndpoint() {
        return "This is an admin-only endpoint!";
    }
}
