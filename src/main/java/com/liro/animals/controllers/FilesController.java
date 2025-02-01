package com.liro.animals.controllers;

import com.liro.animals.service.impl.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.liro.animals.util.Util.getUser;

@RestController
@RequestMapping("/files")
public class FilesController {

    private final FileService fileService;

    @Autowired
    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(path = "/{animalId}/profilePicture", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadProfilePicture(
            @PathVariable("animalId") Long animalId,
            @RequestPart(required = true) MultipartFile file,
            @RequestHeader(name = "Authorization", required = false) String token
    ) {
        fileService.uploadProfilePicture(file, animalId, getUser(token, null));
    }

    @DeleteMapping(path = "/{animalId}/profilePicture")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteProfilePicture(
            @PathVariable("animalId") Long animalId,
            @RequestHeader(name = "Authorization", required = false) String token
    ) {
        fileService.deleteProfilePicture(animalId, getUser(token, null));
    }
}
