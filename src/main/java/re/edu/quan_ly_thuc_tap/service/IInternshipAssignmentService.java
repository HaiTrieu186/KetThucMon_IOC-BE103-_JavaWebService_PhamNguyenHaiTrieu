package re.edu.quan_ly_thuc_tap.service;

import org.springframework.data.domain.Pageable;
import re.edu.quan_ly_thuc_tap.dto.request.InternshipAssignmentCreateRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.request.InternshipAssignmentUpdateStatusRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.response.InternshipAssignmentResponse;
import re.edu.quan_ly_thuc_tap.dto.response.pagination.PageResponse;

public interface IInternshipAssignmentService {
    PageResponse<InternshipAssignmentResponse> getAllAssignments(Pageable pageable);
    InternshipAssignmentResponse getAssignmentById(Long assignmentId);
    InternshipAssignmentResponse createAssignment(InternshipAssignmentCreateRequestDTO dto);
    InternshipAssignmentResponse updateStatus(Long assignmentId, InternshipAssignmentUpdateStatusRequestDTO dto);
}