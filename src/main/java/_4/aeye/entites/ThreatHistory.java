package _4.aeye.entites;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "threat_history")
public class ThreatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cameraName;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public ThreatHistory() {
    }

    public ThreatHistory(String cameraName, LocalDateTime timestamp) {
        this.cameraName = cameraName;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
