package _4.aeye.dtos;

public class UserDto {
    private Long id;
    private String username;
    private String phoneNumber;
    private String role;

    public UserDto(Long id, String username, String phoneNumber, String role) {
        this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
