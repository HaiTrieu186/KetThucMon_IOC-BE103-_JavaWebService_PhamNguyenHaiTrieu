package re.edu.quan_ly_thuc_tap.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import re.edu.quan_ly_thuc_tap.config.security.UserDetailsCustom;
import re.edu.quan_ly_thuc_tap.dto.request.MentorCreateRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.request.MentorUpdateRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.response.MentorResponse;
import re.edu.quan_ly_thuc_tap.dto.response.pagination.PageResponse;
import re.edu.quan_ly_thuc_tap.entity.Mentor;
import re.edu.quan_ly_thuc_tap.entity.Student;
import re.edu.quan_ly_thuc_tap.entity.User;
import re.edu.quan_ly_thuc_tap.exception.BadRequestException;
import re.edu.quan_ly_thuc_tap.exception.DuplicateResourceException;
import re.edu.quan_ly_thuc_tap.exception.ResourceNotFoundException;
import re.edu.quan_ly_thuc_tap.mapper.MentorMapper;
import re.edu.quan_ly_thuc_tap.repository.IInternshipAssignmentRepository;
import re.edu.quan_ly_thuc_tap.repository.IMentorRepository;
import re.edu.quan_ly_thuc_tap.repository.IUserRepository;
import re.edu.quan_ly_thuc_tap.service.IMentorService;
import re.edu.quan_ly_thuc_tap.util.enums.RoleEnum;
import re.edu.quan_ly_thuc_tap.util.helper.PageResponseHelper;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements IMentorService {
    private final MentorMapper mentorMapper;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final IMentorRepository mentorRepository;
    private final IInternshipAssignmentRepository internshipAssignmentRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetails = (UserDetailsCustom) authentication.getPrincipal();
        return userDetails.getUser();
    }

    @Override
    public PageResponse<?> getAllMentor(String keyword, Pageable pageable) {
        User currentUser = getCurrentUser();


        String searchKeyword = StringUtils.hasText(keyword) ? "%" + keyword.trim() + "%" : "%%";
        Page<Mentor> page = mentorRepository.findAllMentors(
                searchKeyword,
                currentUser.getRole() == RoleEnum.STUDENT ? currentUser.getUserId():null,
                pageable);

        // Student
        if (currentUser.getRole() == RoleEnum.STUDENT){
            return PageResponseHelper.toPageResponse(page.map(mentorMapper::toMentorPublicResponse));
        }

        // Admin
        return PageResponseHelper.toPageResponse(page.map(mentorMapper::toMentorResponse));
    }

    @Override
    public Object findMentorById(Long mentorId) {
        User currentUser = getCurrentUser();

        Mentor m = mentorRepository.findById(mentorId).orElseThrow(
                () -> new ResourceNotFoundException("Lỗi: không tìm thấy mentor với id: "+mentorId)
        );

        // Student
        if (currentUser.getRole() == RoleEnum.STUDENT) {
            boolean isAssigned = internshipAssignmentRepository.existsByStudent_StudentIdAndMentor_MentorId(
                    currentUser.getUserId(),
                    mentorId
            );

            if (!isAssigned) {
                throw new AccessDeniedException("Lỗi: Bạn không được phân công cho giáo viên hướng dẫn này!");
            }

            return mentorMapper.toMentorPublicResponse(m);
        }

        // Mentor
        if (currentUser.getRole() == RoleEnum.MENTOR){
            if (!mentorId.equals(currentUser.getUserId())) {
                throw new AccessDeniedException("Lỗi: Bạn không có quyền xem thông tin của mentor khác!");
            }
        }

        // Admin và Mentor trả về như nhau
        return mentorMapper.toMentorResponse(m);

    }

    @Override
    @Transactional
    public MentorResponse createMentor(MentorCreateRequestDTO dto) {
        // 1. Kiểm tra trùng lặp dữ liệu
        if (userRepository.existsByUserName(dto.getUserName())) {
            throw new DuplicateResourceException("Lỗi: Tên đăng nhập đã tồn tại!");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Lỗi: Email đã tồn tại!");
        }

        // 2. Lưu User (để lấy được ID )
        User user = mentorMapper.toUser(dto);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setRole(RoleEnum.MENTOR);
        user.setIsActive(true);
        User savedUser = userRepository.save(user);

        // 3. Lưu Mentor
        Mentor mentor = mentorMapper.toMentor(dto);
        mentor.setUser(savedUser);
        Mentor savedMentor = mentorRepository.save(mentor);

        return mentorMapper.toMentorResponse(savedMentor);
    }

    @Override
    @Transactional
    public MentorResponse updateMentor(Long mentorId, MentorUpdateRequestDTO dto) {
        User currentUser = getCurrentUser();

        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên hướng dẫn với id: " + mentorId));

        // 1. MENTOR chỉ được cập nhật chính mình
        if (currentUser.getRole() == RoleEnum.MENTOR) {
            if (!mentorId.equals(currentUser.getUserId())) {
                throw new AccessDeniedException("Lỗi: Bạn không có quyền cập nhật thông tin của giáo viên khác!");
            }
        }

        // 2. Kiểm tra email trùng lặp nếu có sự thay đổi email
        if (!dto.getEmail().equals(mentor.getUser().getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new BadRequestException("Lỗi: Email đã được sử dụng bởi người dùng khác!");
            }
        }

        // 3. Cập nhật
        mentorMapper.updateUserFromDto(dto, mentor.getUser());
        mentorMapper.updateMentorFromDto(dto, mentor);

        // 4. Lưu lại vào DB
        userRepository.save(mentor.getUser()); // Lưu bảng Users
        Mentor updatedMentor = mentorRepository.save(mentor); // Lưu bảng Mentors

        return mentorMapper.toMentorResponse(updatedMentor);
    }
}
