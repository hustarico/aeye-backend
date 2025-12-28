package _4.aeye.rest;

import _4.aeye.entites.Image;
import _4.aeye.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public ResponseEntity<Long> uploadImage(@RequestParam("image") MultipartFile image) {
        Image savedImage = imageService.saveImage(image);
        return ResponseEntity.ok(savedImage.getId());
    }

    @PostMapping("/with-camera-name")
    public ResponseEntity<Long> uploadImageWithCameraName(
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "cameraName", required = false) String cameraName) {
        Image savedImage = cameraName != null ? imageService.saveImage(image, cameraName) : imageService.saveImage(image);
        return ResponseEntity.ok(savedImage.getId());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateImageData(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image) {
        Image updatedImage = imageService.updateImage(id, image);
        return ResponseEntity.ok(updatedImage.getId());
    }

    @PatchMapping("/{id}/with-camera-name")
    public ResponseEntity<Long> updateImageWithCameraName(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "cameraName", required = false) String cameraName) {
        Image updatedImage = cameraName != null ? imageService.updateImage(id, image, cameraName) : imageService.updateImage(id, image);
        return ResponseEntity.ok(updatedImage.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageService.getImage(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, image.getContentType())
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(image.getData().length))
                .body(image.getData());
    }
}
