package kr.co.ddamddam.user.repository;

import kr.co.ddamddam.chat.dto.response.UserResponseDTO;
import kr.co.ddamddam.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //이메일 중복체크
//    @Query("select count(*) from User u where u.userEmail=:userEmail")
    boolean existsByUserEmail(String userEmail);

    Optional<User> findByUserEmail(String userEmail);

    User findByUserNickname(String userNickname);

    @Query("SELECT u FROM User u WHERE u.userEmail=:userEmail")
    Optional<User> findByUserEmailByKakaoLogin(String userEmail);

}
