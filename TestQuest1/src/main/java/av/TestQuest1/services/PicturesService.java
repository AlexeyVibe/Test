package av.TestQuest1.services;

import av.TestQuest1.models.Picture;
import av.TestQuest1.repositories.PeopleRepository;
import av.TestQuest1.repositories.PicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PicturesService {


    @Value("${upload.path}")
    private String uploadPath;
    private final PicturesRepository picturesRepository;
    private final PeopleRepository peopleRepository;


    @Autowired
    public PicturesService( PicturesRepository picturesRepository, PeopleRepository peopleRepository) {
        this.picturesRepository = picturesRepository;
        this.peopleRepository = peopleRepository;

    }

    public String findById(int id){
        Optional<Picture> picture = picturesRepository.findById(id);
        return picture.get().getUrl();
    }

    @Transactional
    public String uploadPicture(MultipartFile file, String name)  {

        Path path = Paths.get(uploadPath + file.getOriginalFilename());

        Picture picture = new Picture();

        if (name != null){
            picture.setPerson(peopleRepository.findByName(name).get());
        }

        try {
            Files.write(path,file.getBytes());

            picture.setUrl(file.getOriginalFilename());
            picturesRepository.save(picture);

            return "http://localhost:8080/" + picture.getUrl();

        } catch (IOException e) {
            return "Вам не удалось загрузить фаил";
        }
    }

    public Optional<Picture> findByUrl( String url){
        return picturesRepository.findByUrl(url);
    }





}
