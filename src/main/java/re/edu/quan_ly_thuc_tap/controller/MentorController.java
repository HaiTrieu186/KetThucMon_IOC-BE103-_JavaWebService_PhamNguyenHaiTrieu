package re.edu.quan_ly_thuc_tap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.edu.quan_ly_thuc_tap.dto.request.MentorCreateRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.request.MentorUpdateRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.response.ApiResponse;
import re.edu.quan_ly_thuc_tap.dto.response.MentorResponse;
import re.edu.quan_ly_thuc_tap.dto.response.pagination.PageResponse;
import re.edu.quan_ly_thuc_tap.service.IMentorService;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentors")
public class MentorController {
    private final IMentorService mentorService;

    /**
     * Lấy danh sách tất cả giáo viên hướng dẫn
     * ADMIN: Xem đầy đủ
     * STUDENT: Xem thông tin chung
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT')")
    public ResponseEntity<?> getAllMentors(
            @RequestParam(required = false) String keyword,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        PageResponse<?> data = mentorService.getAllMentor(keyword, pageable);

        return ResponseEntity.ok(
                ApiResponse.<PageResponse<?>>builder()
                        .success(true)
                        .message("Lấy danh sách giáo viên hướng dẫn thành công!")
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * Lấy thông tin chi tiết một giáo viên hướng dẫn theo ID
     * ADMIN: Xem tất cả
     * MENTOR: Chỉ xem của mình
     * STUDENT: Xem thông tin chung
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<?> getMentorById(
            @PathVariable Long id
    ) {
        Object data = mentorService.findMentorById(id);

        return ResponseEntity.ok(
                ApiResponse.<Object>builder()
                        .success(true)
                        .message("Lấy thông tin chi tiết giáo viên hướng dẫn thành công!")
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * Tạo thông tin giáo viên hướng dẫn mới
     * Chỉ ADMIN mới có quyền tạo
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createMentor(
            @Valid @RequestBody MentorCreateRequestDTO dto) {
        MentorResponse data = mentorService.createMentor(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<MentorResponse>builder()
                        .success(true)
                        .message("Tạo giáo viên hướng dẫn mới thành công!")
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * Cập nhật thông tin chi tiết giáo viên hướng dẫn
     * ADMIN: Cập nhật bất kỳ ai
     * MENTOR: Chỉ cập nhật của chính mình
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR')")
    public ResponseEntity<?> updateMentor(
            @PathVariable Long id,
            @Valid @RequestBody MentorUpdateRequestDTO dto
    ) {
        MentorResponse data = mentorService.updateMentor(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<MentorResponse>builder()
                        .success(true)
                        .message("Cập nhật thông tin giáo viên hướng dẫn thành công!")
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}