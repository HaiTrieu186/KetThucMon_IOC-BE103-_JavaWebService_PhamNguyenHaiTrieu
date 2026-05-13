package re.edu.quan_ly_thuc_tap.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationCriteriaUpdateRequestDTO {

    @NotBlank(message = "Tên tiêu chí đánh giá không được để trống")
    @Size(max = 200, message = "Tên tiêu chí đánh giá không được vượt quá 200 ký tự")
    private String criterionName;

    private String description;

    @NotNull(message = "Điểm tối đa không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Điểm tối đa phải lớn hơn 0")
    @Digits(integer = 3, fraction = 2, message = "Điểm tối đa chỉ được phép có tối đa 3 chữ số phần nguyên và 2 chữ số phần thập phân")
    private BigDecimal maxScore;
}