package re.edu.quan_ly_thuc_tap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.edu.quan_ly_thuc_tap.entity.User;

import java.util.Optional;

@Repository
public interface IUserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
