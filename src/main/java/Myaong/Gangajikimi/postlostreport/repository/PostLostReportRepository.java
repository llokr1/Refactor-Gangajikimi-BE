package Myaong.Gangajikimi.postlostreport.repository;

import Myaong.Gangajikimi.postlostreport.entity.PostLostReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLostReportRepository extends JpaRepository<PostLostReport, Long> {
    
    boolean existsByPostLostIdAndReporterId(Long postLostId, Long reporterId);
}
