package Myaong.Gangajikimi.dogtype.repository;

import Myaong.Gangajikimi.dogtype.entity.DogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DogTypeRepository extends JpaRepository<DogType, Long> {
    
    Optional<DogType> findByType(String type);
    
    List<DogType> findByTypeContainingIgnoreCase(String type);
}