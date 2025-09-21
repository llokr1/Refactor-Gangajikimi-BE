package Myaong.Gangajikimi.postlost.repository;

import Myaong.Gangajikimi.postlost.entity.PostLost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLostRepository extends JpaRepository<PostLost,Long> {

    Optional<PostLost> findPostLostById(Long id);


}
