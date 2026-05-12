package re.edu.quan_ly_thuc_tap.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import re.edu.quan_ly_thuc_tap.entity.Mentor;

import java.util.Optional;

@Repository
public interface IMentorRepository extends JpaRepository<Mentor, Long> {

    @Query("""
        SELECT m FROM Mentor m
        JOIN m.user u
        WHERE LOWER(u.fullName) LIKE LOWER(:keyword)
    """)
    Page<Mentor> findAllMentors(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    Optional<Mentor> findByMentorId(Long mentorId);

    boolean existsByMentorId(Long mentorId);


}
