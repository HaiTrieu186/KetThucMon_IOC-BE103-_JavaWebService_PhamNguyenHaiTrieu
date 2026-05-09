package re.edu.quan_ly_thuc_tap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.edu.quan_ly_thuc_tap.dto.request.UserCreateRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.request.UserUpdateRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.request.UserUpdateRoleRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.request.UserUpdateStatusRequestDTO;
import re.edu.quan_ly_thuc_tap.dto.response.ApiResponse;
import re.edu.quan_ly_thuc_tap.dto.response.UserResponse;
import re.edu.quan_ly_thuc_tap.dto.response.pagination.PageResponse;
import re.edu.quan_ly_thuc_tap.service.IUserService;
import re.edu.quan_ly_thuc_tap.util.enums.RoleEnum;
import re.edu.quan_ly_thuc_tap.util.helper.PageResponseHelper;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final IUserService userService;

    //  GET /api/users?role=MENTOR&page=0&size=10&sort=createdAt,desc
    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) RoleEnum role,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<UserResponse> pageResponse = userService.getAllUsers(role, pageable);

        return ResponseEntity.ok(
                ApiResponse.<PageResponse<UserResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách người dùng thành công!")
                        .data(pageResponse)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    //  GET /api/users/{id}
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Lấy thông tin người dùng thành công!")
                        .data(userService.getUserById(userId))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    //  POST /api/users
    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserCreateRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Tạo người dùng thành công!")
                        .data(userService.createUser(dto))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    //  PUT /api/users/{id}
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequestDTO dto
    ) {
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Cập nhật thông tin người dùng thành công!")
                        .data(userService.updateUser(userId, dto))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    //  PUT /api/users/{id}/status
    @PutMapping("/{userId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateStatusRequestDTO dto
    ) {
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Cập nhật trạng thái người dùng thành công!")
                        .data(userService.updateStatus(userId, dto))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    //  PUT /api/users/{id}/role
    @PutMapping("/{userId}/role")
    public ResponseEntity<?> updateRole(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRoleRequestDTO dto
    ) {
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Cập nhật vai trò người dùng thành công!")
                        .data(userService.updateRole(userId, dto))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }


    //  DELETE /api/users/{id}
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Xóa người dùng thành công!")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
