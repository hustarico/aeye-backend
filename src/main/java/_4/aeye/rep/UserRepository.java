package _4.aeye.rep;

import _4.aeye.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    @Query("SELECT u.phoneNumber FROM User u")
    List<String> getAllPhoneNumbers();
}

