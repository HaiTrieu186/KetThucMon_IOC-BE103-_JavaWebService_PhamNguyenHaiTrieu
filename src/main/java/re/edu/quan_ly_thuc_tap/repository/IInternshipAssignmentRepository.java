package re.edu.quan_ly_thuc_tap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import re.edu.quan_ly_thuc_tap.entity.InternshipAssignment;

@Repository
public interface IInternshipAssignmentRepository extends JpaRepository<InternshipAssignment, Long> {
    boolean existsByPhase_PhaseId(Long phaseId);
    Boolean existsByStudent_StudentIdAndMentor_MentorId(Long studentStudentId, Long mentorMentorId);
}
