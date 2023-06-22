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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserSignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final S3Service s3Service;

    @Value("${upload.path.profile}")
    private String uploadRootPath;

    //회원가입 처리
    public UserSignUpResponseDTO create(
            final UserRequestSignUpDTO dto,
            final String uploadedFilePath)
            throws RuntimeException {

        if (dto == null) {
            throw new NoRegisteredArgumentsException("가입 정보가 없습니다.");
        }
        String email = dto.getUserEmail();
        String nickname = dto.getUserNickName();

        //이메일 중복검사
        if (isDuplicate(email)) {
            log.warn("이메일이 중복되었습니다. - {}", email);
            throw new DuplicatedEmailException("중복된 이메일입니다.");
        }

        //닉네임 중복검사
        if(isNicknameExist(nickname)){
            log.warn("닉네임이 중복되었습니다. - {}", nickname);
            throw new DuplicatedEmailException("이미 존재하는 닉네임입니다.");
        }

        // 패스워드 인코딩(유저 엔티티로 변환하기전에 해야할일 !)
        String encoded = encoder.encode(dto.getUserPw());
        //회원 객체 비밀번호 인코딩된 비밀번호로 저장
        dto.setUserPw(encoded);

        // 유저 엔터티로 변환
        User user = dto.toEntity(uploadedFilePath);

        //엔티티를 저장하고 리턴값 반환
        User saved = userRepository.save(user);

        log.info("회원가입 정상 수행됨! - saved user - {}", saved);

        return new UserSignUpResponseDTO(saved);

    }

    public boolean isDuplicate(String email) {
        return userRepository.existsByUserEmail(email);
    }

    public boolean isNicknameExist(String nickname){
        return userRepository.existsByUserNickname(nickname);
    }



    /**
     * 업로드된 파일을 서버에 저장하고 저장 경로를 리턴
     * @param originalFile - 업로드된 파일의 정보
     * @return 실제로 저장된 이미지의 경로
     */
    public String uploadProfileImage(MultipartFile originalFile) throws IOException {

        //AWS적용하기(주석친 부분은 로컬에 저장하는거)
        // 루트 디렉토리가 존재하는지 확인 후 존재하지 않으면 생성
//        File rootDir = new File(uploadRootPath);
//        if (!rootDir.exists()) rootDir.mkdir();

        // 파일명을 유니크하게 변경
        String uniqueFileName = UUID.randomUUID()
                + "_" + originalFile.getOriginalFilename();

        // 파일을 저장
//        File uploadFile = new File(uploadRootPath + "/" + uniqueFileName);
//        originalFile.transferTo(uploadFile);

        //파일을 s3 버킷에 저장
        String uploadUrl = s3Service.uploadToS3Bucket(originalFile.getBytes(), uniqueFileName);

        return uploadUrl;
    }

    public String getProfilePath(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow();
//        return uploadRootPath + "/" + user.getUserProfile();
        return user.getUserProfile();
    }


}






