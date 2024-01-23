package com.project.tegshop.service.image;

import com.project.tegshop.model.ImageData;
import com.project.tegshop.repository.StorageRepository;
import com.project.tegshop.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageRepository storageRepository;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build();
        if(imageData == null) {
            throw new IOException("File upload fail!");
        }
        storageRepository.save(imageData);
        return "file uploaded successfully : " + file.getOriginalFilename();
    }

    @Override
    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = storageRepository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}
