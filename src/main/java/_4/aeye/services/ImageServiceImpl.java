package _4.aeye.services;

import _4.aeye.entites.Image;
import _4.aeye.rep.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image saveImage(MultipartFile file) {
        return saveImage(file, "camera1");
    }

    @Override
    public Image saveImage(MultipartFile file, String cameraName) {
        try {
            byte[] imageData = file.getBytes();
            String contentType = file.getContentType();
            Instant createdAt = Instant.now();

            Image image = new Image(imageData, contentType, createdAt, cameraName);
            return imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }

    @Override
    public Image updateImage(Long id, MultipartFile file) {
        Image image = getImage(id);
        return updateImageData(image, file, image.getCameraName());
    }

    @Override
    public Image updateImage(Long id, MultipartFile file, String cameraName) {
        Image image = getImage(id);
        return updateImageData(image, file, cameraName);
    }

    private Image updateImageData(Image image, MultipartFile file, String cameraName) {
        try {
            image.setData(file.getBytes());
            image.setContentType(file.getContentType());
            image.setCameraName(cameraName);
            return imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update image", e);
        }
    }

    @Override
    public Image getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }
}
