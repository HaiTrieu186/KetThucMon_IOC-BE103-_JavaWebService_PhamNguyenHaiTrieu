package re.edu.quan_ly_thuc_tap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.edu.quan_ly_thuc_tap.entity.RoundCriteria;

@Repository
public interface IRoundCriteriaRepository extends JpaRepository<RoundCriteria, Long> {

    // Kiểm tra xem tiêu chí này đã được sử dụng trong bất kỳ đợt đánh giá nào chưa
    boolean existsByCriterion_CriterionId(Long criterionId);
    void deleteAllByRound_RoundId(Long roundId);

}
