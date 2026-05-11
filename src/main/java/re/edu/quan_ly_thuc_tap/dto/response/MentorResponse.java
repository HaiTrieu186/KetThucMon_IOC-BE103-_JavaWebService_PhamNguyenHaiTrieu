package re.edu.quan_ly_thuc_tap.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MentorResponse {
    // User
    private Long userId;
    private String userName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String role;
    private Boolean isActive;

    // Mentor
    private String department;
    private String academicRank;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}