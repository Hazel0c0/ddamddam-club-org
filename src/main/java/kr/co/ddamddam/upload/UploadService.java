package kr.co.ddamddam.upload;

import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadService {


  private final ProjectRepository projectRepository;

  @Value("${upload.path.project}")
  private String projectUploadRootPath;

  @Value("${upload.path.qna}")
  private String qnaUploadRootPath;
  /**
   * 업로드된 파일을 서버에 저장하고 저장 경로를 리턴
   * @param originalFile - 업로드된 파일의 정보
   * @return 실제로 저장된 이미지의 경로
   */
  public String uploadFileImage(MultipartFile originalFile ,String boardType) throws IOException {
    String uploadRootPath;

    if ("project".equals(boardType)) {
      uploadRootPath = projectUploadRootPath;
    } else if ("qna".equals(boardType)) {
      uploadRootPath = qnaUploadRootPath;
    } else {
      throw new IllegalArgumentException("Unsupported board type: " + boardType);
    }

    File rootDir = new File(uploadRootPath);
    if (!rootDir.exists()) rootDir.mkdir();

    String uniqueFileName = UUID.randomUUID()
        + "_" + originalFile.getOriginalFilename();

    File uploadFile = new File(uploadRootPath + "/" + uniqueFileName);
    originalFile.transferTo(uploadFile);

    return uniqueFileName;
  }

  // 파일 경로 리턴
  public String getFilePath(Long projectIdx,String boardType) {
    Project project = projectRepository.findById(projectIdx).orElseThrow();

    String uploadRootPath;
    if ("project".equals(boardType)) {
      uploadRootPath = projectUploadRootPath;
    } else if ("qna".equals(boardType)) {
      uploadRootPath = qnaUploadRootPath;
    } else {
      throw new IllegalArgumentException("Unsupported board type: " + boardType);
    }

    return uploadRootPath + File.separator + project.getProjectImg();

  }
}
