package re.edu.quan_ly_thuc_tap.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import re.edu.quan_ly_thuc_tap.entity.User;
import re.edu.quan_ly_thuc_tap.repository.IUserRepository;
import re.edu.quan_ly_thuc_tap.util.enums.RoleEnum;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra DB có username 'admin' chưa
        if (!userRepository.existsByUserName("admin")) {

            // Nếu chưa có thì khởi tạo 1 User mới
            User admin = User.builder()
                    .userName("admin")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .fullName("Quản trị viên Hệ thống")
                    .email("admin@ptit.edu.vn")
                    .role(RoleEnum.ADMIN)
                    .isActive(true)
                    .build();

            // Lưu xuống DB
            userRepository.save(admin);

            System.out.println("=> Đã tạo tự động tài khoản Root: admin / admin123");
        }
    }
}
