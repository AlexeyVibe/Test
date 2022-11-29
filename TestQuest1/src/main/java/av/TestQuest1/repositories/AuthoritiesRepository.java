package av.TestQuest1.repositories;

import av.TestQuest1.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authority,Integer> {
    Optional<Authority> findByRole (String role);
}
