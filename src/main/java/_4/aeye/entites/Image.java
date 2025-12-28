package _4.aeye.entites;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] data;

    private String contentType;

    private Instant createdAt;

    private String cameraName = "camera1";

    public Image() {
    }

    public Image(byte[] data, String contentType, Instant createdAt) {
        this.data = data;
        this.contentType = contentType;
        this.createdAt = createdAt;
        this.cameraName = "camera1";
    }

    public Image(byte[] data, String contentType, Instant createdAt, String cameraName) {
        this.data = data;
        this.contentType = contentType;
        this.createdAt = createdAt;
        this.cameraName = cameraName != null ? cameraName : "camera1";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName != null ? cameraName : "camera1";
    }
}
