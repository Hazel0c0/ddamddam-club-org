package kr.co.ddamddam.user.service;

import kr.co.ddamddam.user.dto.request.UserRequestSignUpDTO;
import kr.co.ddamddam.user.dto.response.UserSignUpResponseDTO;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.exception.DuplicatedEmailException;
import kr.co.ddamddam.user.exception.NoRegisteredArgumentsException;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserSingUpService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    //회원가입 처리
    public UserSignUpResponseDTO create(final UserRequestSignUpDTO dto)
            throws RuntimeException {

        if (dto == null) {
            throw new NoRegisteredArgumentsException("가입 정보가 없습니다.");
        }
        String email = dto.getUserEmail();

        if (isDuplicate(email)) {
            log.warn("이메일이 중복되었습니다. - {}", email);
            throw new DuplicatedEmailException("중복된 이메일입니다.");
        }

        // 패스워드 인코딩(유저 엔티티로 변환하기전에 해야할일 !)
        String encoded = encoder.encode(dto.getUserPw());
        //회원 객체 비밀번호 인코딩된 비밀번호로 저장
        dto.setUserPw(encoded);

        // 유저 엔터티로 변환
        User user = dto.toEntity();
        //엔티티를 저장하고 리턴값 반환
        User saved = userRepository.save(user);

        log.info("회원가입 정상 수행됨! - saved user - {}", saved);

        return new UserSignUpResponseDTO(saved);

    }

    public boolean isDuplicate(String email) {
        return userRepository.existsByUserEmail(email);
    }

//    //유저정보 수정기능
//    public UserModifyResponseDTO modify(UserModifyRequestDTO dto){
//
//        //이메일 값 받아오기
//        String email = dto.getUserEmail();
//
//        //이메일로 유저정보 얻기 (이메일은 유일값이기때문에 ..)
//        User founduser = userRepository.findByUserEmail(email).orElseThrow(() -> new Exception("회원정보 수정에 실패했습니다!"));
//
//        //이메일로 찾은 객체에 dto에 수정하고자 하는 정보들을 뽑아서 새로 집어넣고 다시 저장해준다
//        founduser.setUserAddress(dto.getUserAddress());
//        founduser.setUserFullAddress(dto.getUserFullAddress());
//        founduser.setUserPhone(dto.getUserPhone());
//        founduser.setUserName(dto.getUsername());
//
//        User modiftideuser = userRepository.save(founduser);
//
//        return new UserModifyresponseDTO(modiftideuser);
//    }
//
//
//    //회원탈퇴 기능 구현
//    public boolean deleteUser(UserDeleteRequestDTO dto) {
//
//        User user = userRepository.findById(dto.getUserId()).orElseThrow(
//                () -> new RuntimeException("가입된 회원이 아닙니다.")
//        );
//
//        String encodedPassword = user.getUserPassword(); //db저장 비번
//        if (!encoder.matches(dto.getUserPassword(),encodedPassword)){
//            throw new RuntimeException("비밀번호가 일치하지않아 회원탈퇴를 진행할 수 없습니다");
//        }
//
//        userRepository.deleteById(dto.getUserId());
//        return true;
//    }




}
