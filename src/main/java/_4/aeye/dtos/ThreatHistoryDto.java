package _4.aeye.dtos;

import java.time.LocalDateTime;

public class ThreatHistoryDto {
    private String cameraName;
    private LocalDateTime timestamp;

    public ThreatHistoryDto() {
    }

    public ThreatHistoryDto(String cameraName, LocalDateTime timestamp) {
        this.cameraName = cameraName;
        this.timestamp = timestamp;
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
