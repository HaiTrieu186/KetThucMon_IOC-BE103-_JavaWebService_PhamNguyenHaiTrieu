package re.edu.quan_ly_thuc_tap.service;

import re.edu.quan_ly_thuc_tap.dto.request.AuthLoginRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.request.AuthRefreshRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.response.AuthResponse;
import re.edu.quan_ly_thuc_tap.dto.response.UserResponse;

public interface IAuthService {
    AuthResponse login (AuthLoginRequestDTO request);
    UserResponse getMyInfo();
    AuthResponse refreshToken(AuthRefreshRequestDTO request);
    void logout();
}
