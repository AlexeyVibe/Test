package av.TestQuest1.controllers;

import av.TestQuest1.services.PicturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@RequestMapping("/picture")
public class PictureController {

    private final PicturesService picturesService;

    @Autowired
    public PictureController(PicturesService picturesService) {
        this.picturesService = picturesService;
    }


    @PostMapping
    public String uploadPicture(@RequestBody MultipartFile file,@RequestParam(required = false) String name) {
        if (!file.isEmpty()) {
            return picturesService.uploadPicture(file,name);
        } else {
            return "Файл не удалось загрузить, потому что файл пустой.";
        }
    }





}
