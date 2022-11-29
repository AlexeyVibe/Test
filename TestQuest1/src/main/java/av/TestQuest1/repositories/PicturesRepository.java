package av.TestQuest1.repositories;

import av.TestQuest1.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PicturesRepository extends JpaRepository<Picture, Integer> {
    Optional<Picture> findByUrl (String url);
}
