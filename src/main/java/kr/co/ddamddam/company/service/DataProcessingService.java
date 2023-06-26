package kr.co.ddamddam.company.service;

import kr.co.ddamddam.company.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class DataProcessingService {

    private final CompanyService companyService;
    private final String allUrl;
    private final Timer timer;
    private final CompanyRepository companyRepository;
    private static final long INTERVAL_MS = 24 * 60 * 60 * 1000; // 24시간


    public DataProcessingService(CompanyService companyService,CompanyRepository companyRepository) {
        this.companyService = companyService;

        this.allUrl = "https://openapi.work.go.kr/opi/opi/opia/wantedApi.do?authKey=WNLIS5RDCEK7WOBRD73GA2VR1HJ&returnType=xml&display=480&callTp=L&region=&keyword==%EA%B0%9C%EB%B0%9C%EC%9E%90";
        this.timer = new Timer();
        this.companyRepository = companyRepository;
        scheduleDataProcessingTask();
    }

    private void scheduleDataProcessingTask() {
        DataProcessingTask task = new DataProcessingTask();
        timer.scheduleAtFixedRate(task, 0, INTERVAL_MS);
    }

    private class DataProcessingTask extends TimerTask {
        @Override
        public void run() {
            try {
                //db를 전부 지운다(중복 방지를 위해서)
                companyRepository.deleteAll();
                //서비스에서 함수 호출해서 다시 넣기
                companyService.processExternalData(allUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
