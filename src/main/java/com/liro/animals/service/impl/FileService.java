package com.liro.animals.service.impl;

import com.liro.animals.config.OciClient;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.repositories.AnimalRepository;
import com.liro.animals.util.Util;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.transfer.UploadConfiguration;
import com.oracle.bmc.objectstorage.transfer.UploadManager;
import com.oracle.bmc.retrier.RetryConfiguration;
import com.oracle.bmc.retrier.RetryOptions;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@Component
public class FileService {

    @Autowired
    private ObjectStorage ociClient;

    @Autowired
    private Util util;

    @Autowired
    private AnimalRepository animalRepository;

    @Transactional
    public void uploadProfilePicture(MultipartFile file, Long animalId, UserDTO userDTO) {
        Animal animal = util.validatePermissions(animalId, userDTO, true, false, false);

        try {

            String contentType = file.getContentType();
            String fileName = "profile-" + animal.getId();

            File body = convertMultipartToFile(file, fileName);

            UploadConfiguration uploadConfiguration = UploadConfiguration.builder()
                    .allowMultipartUploads(true)
                    .allowParallelUploads(true)
                    .lengthPerUploadPart(16) // Ajusta según tus necesidades
                    .build();

            UploadManager uploadManager = new UploadManager(ociClient, uploadConfiguration);
            String storageBucketName = "bucket-20241105-1622";
            String nameSpace = "axkd8tvejaz9";
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucketName(storageBucketName)
                    .namespaceName(nameSpace)
                    .objectName(fileName)
                    .contentType(contentType)
                    .build();

            UploadManager.UploadRequest uploadRequest = UploadManager.UploadRequest.builder(body)
                    .allowOverwrite(true)
                    .build(putObjectRequest);
            UploadManager.UploadResponse uploadResponse = uploadManager.upload(uploadRequest);


            String profilePictureURL = String.format("https://objectstorage.%s.oraclecloud.com/n/%s/b/%s/o/%s",
                    "sa-santiago-1", nameSpace, storageBucketName, fileName);
            animal.setProfilePictureURL(profilePictureURL);

            animalRepository.save(animal);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Transactional
    public void deleteProfilePicture(Long animalId, UserDTO userDTO) {

        Animal animal = util.validatePermissions(animalId, userDTO, true, false, false);

        if (isNotBlank(animal.getProfilePictureURL())) {
            deleteImage(animal.getProfilePictureURL());
        }

        animal.setProfilePictureURL(null);
        animalRepository.save(animal);
    }


    private void deleteImage(String profilePictureURL) {
        try {
            String[] urlParts = profilePictureURL.split("/");
            String bucketName = urlParts[urlParts.length - 3];
            String objectName = urlParts[urlParts.length - 1];

            RetryConfiguration retryConfiguration = RetryConfiguration.builder()
                    .retryOptions(new RetryOptions(3))
                    .build();

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucketName(bucketName)
                    .namespaceName("axkd8tvejaz9")
                    .objectName(objectName)
                    .retryConfiguration(retryConfiguration)
                    .build();

            ociClient.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete previous file.", e);
        }
    }

    private static File convertMultipartToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipartFile.transferTo(convFile);
        return convFile;
    }
}