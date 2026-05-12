package re.edu.quan_ly_thuc_tap.service;

import org.springframework.data.domain.Pageable;
import re.edu.quan_ly_thuc_tap.dto.request.MentorCreateRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.request.MentorUpdateRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.response.MentorResponse;
import re.edu.quan_ly_thuc_tap.dto.response.pagination.PageResponse;

public interface IMentorService {
    PageResponse<?> getAllMentor(String keyword, Pageable pageable);
    Object findMentorById(Long mentorId);
    MentorResponse createMentor(MentorCreateRequestDTO dto);
    MentorResponse updateMentor(Long mentorId, MentorUpdateRequestDTO dto);
}
