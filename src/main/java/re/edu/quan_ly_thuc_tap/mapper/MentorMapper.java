package re.edu.quan_ly_thuc_tap.mapper;

import org.mapstruct.*;
import re.edu.quan_ly_thuc_tap.dto.request.MentorCreateRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.request.MentorUpdateRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.response.MentorResponse;
import re.edu.quan_ly_thuc_tap.entity.Mentor;
import re.edu.quan_ly_thuc_tap.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MentorMapper {

    @Mapping(target = "passwordHash", source = "password")
    User toUser(MentorCreateRequestDTO dto);

    Mentor toMentor(MentorCreateRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(MentorUpdateRequestDTO dto, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMentorFromDto(MentorUpdateRequestDTO dto, @MappingTarget Mentor mentor);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "isActive", source = "user.isActive")
    MentorResponse toMentorResponse(Mentor mentor);
}