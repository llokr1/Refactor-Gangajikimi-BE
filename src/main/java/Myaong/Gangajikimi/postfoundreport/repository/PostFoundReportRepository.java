package Myaong.Gangajikimi.postfoundreport.repository;

import Myaong.Gangajikimi.postfoundreport.entity.PostFoundReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFoundReportRepository extends JpaRepository<PostFoundReport, Long> {
    
    boolean existsByPostFoundIdAndReporterId(Long postFoundId, Long reporterId);
}
