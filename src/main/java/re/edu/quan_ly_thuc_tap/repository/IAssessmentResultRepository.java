package re.edu.quan_ly_thuc_tap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.edu.quan_ly_thuc_tap.entity.AssessmentResult;

@Repository
public interface IAssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {

    // Kiểm tra xem tiêu chí đã được chấm điểm cho sinh viên nào chưa
    Boolean existsByCriterion_CriterionId(Long criterionId);

    Boolean existsByRound_RoundId(Long roundId);

    boolean existsByRound_RoundIdAndCriterion_CriterionId(Long roundId, Long criterionId);

}