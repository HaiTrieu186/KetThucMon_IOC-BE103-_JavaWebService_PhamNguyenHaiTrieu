package re.edu.quan_ly_thuc_tap.mapper;

import org.mapstruct.Mapper;
import re.edu.quan_ly_thuc_tap.dto.response.UserResponse;
import re.edu.quan_ly_thuc_tap.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // Entity → UserResponse.
    UserResponse toUserResponse(User user);
}
