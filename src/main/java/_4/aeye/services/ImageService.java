package _4.aeye.services;

import _4.aeye.entites.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image saveImage(MultipartFile file);
    Image saveImage(MultipartFile file, String cameraName);
    Image updateImage(Long id, MultipartFile file);
    Image updateImage(Long id, MultipartFile file, String cameraName);
    Image getImage(Long id);
}
