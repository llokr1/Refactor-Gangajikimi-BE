package Myaong.Gangajikimi.templocation.repository;

import Myaong.Gangajikimi.postfound.entity.PostFound;
import Myaong.Gangajikimi.templocation.entity.TempLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TempLocationRepository extends JpaRepository<TempLocation, Long> {

    Optional<TempLocation> findTempLocationByPostFound(PostFound postFound);

}