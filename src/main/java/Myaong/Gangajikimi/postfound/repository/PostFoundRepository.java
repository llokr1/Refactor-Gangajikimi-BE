package Myaong.Gangajikimi.postfound.repository;

import Myaong.Gangajikimi.postfound.entity.PostFound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostFoundRepository extends JpaRepository<PostFound, Long> {

    Optional<PostFound> findPostFoundById(Long id);

}