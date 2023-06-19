package kr.co.ddamddam.company.service;

import kr.co.ddamddam.company.dto.response.CompanyDetailResponseDTO;
import kr.co.ddamddam.company.dto.response.CompanyListPageResponseDTO;
import kr.co.ddamddam.company.dto.page.PageDTO;
import kr.co.ddamddam.company.dto.page.PageResponseDTO;
import kr.co.ddamddam.company.dto.request.CompanyRequestDTO;
import kr.co.ddamddam.company.dto.response.CompanyListResponseDTO;
import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompanyService {


    private final CompanyRepository companyRepository;
    public static int INDENT_FACTOR = 4;

    //api xml데이터를 json으로 변경해서 DB에 저장
    public void processExternalData() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("https://openapi.work.go.kr/opi/opi/opia/wantedApi.do?authKey=WNLIS5RDCEK7WOBRD73GA2VR1HJ&returnType=xml&display=50&callTp=L&region=11000&keyword==%EA%B0%9C%EB%B0%9C%EC%9E%90").openConnection();
        conn.connect();
        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
        StringBuffer st = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {

            st.append(line);
        }

        // Convert XML to JSON
        JSONObject xmlJSONObj = XML.toJSONObject(st.toString());

        String jsonPrettyPrintString = xmlJSONObj.toString(INDENT_FACTOR);

        JSONObject jsonObject = new JSONObject(jsonPrettyPrintString);

        // Get the 'wantedRoot' object
        JSONObject wantedRoot = jsonObject.getJSONObject("wantedRoot");

        // Get the 'wanted' array from 'wantedRoot'
        JSONArray wantedArray = wantedRoot.getJSONArray("wanted");

        // Create a new JSON object with only the 'wanted' array
        JSONObject newJson = new JSONObject();
        newJson.put("wanted", wantedArray);

//        System.out.println(jsonObject.toString(INDENT_FACTOR));
        System.out.println(newJson.toString());

        CompanyRequestDTO dto = new CompanyRequestDTO();
        List<CompanyRequestDTO> wantedList = new ArrayList<>();


        for (int i = 0; i < wantedArray.length(); i++) {

            JSONObject value = wantedArray.getJSONObject(i);
            // Extract the desired values from the JSON object
            String title = value.getString("title");
            String company = value.getString("company");
            String career = value.getString("career");
            String wantedInfoUrl = value.getString("wantedInfoUrl");
            String basicAddr = value.getString("basicAddr");
            String detailAddr = value.getString("detailAddr");
            String sal = value.getString("sal");
            String regDt = value.getString("regDt");
            String closeDt = value.getString("closeDt");

            // Populate the DTO object with the extracted values
            dto.setTitle(title);
            dto.setCompany(company);
            dto.setCareer(career);
            dto.setWantedInfoUrl(wantedInfoUrl);
            dto.setBasicAddr(basicAddr);
            dto.setDetailAddr(detailAddr);
            dto.setSal(sal);
            dto.setRegDt(regDt);
            dto.setCloseDt(closeDt);

            // Add the DTO object to the list if needed
            wantedList.add(dto);

            for (CompanyRequestDTO companyRequestDTO : wantedList) {
                Company company1 = companyRequestDTO.toEntity();
                companyRepository.save(company1);
            }

        }
    }



        public CompanyListPageResponseDTO getList (PageDTO pageDTO){
            PageRequest pageable = getPageable(pageDTO);

            // 데이터베이스에서 QNA 게시글 목록 조회 후 DTO 리스트로 꺼내기
            Page<Company> companies = companyRepository.findAll(pageable);
            List<CompanyListResponseDTO> companyListResponseDTOList = getCompanyDTOList(companies);


            //JSON 형태로 변형
            return CompanyListPageResponseDTO.builder()
                    .count(companyListResponseDTOList.size())
                    .count(companyListResponseDTOList.size())
                    .pageInfo(new PageResponseDTO<Company>(companies))
                    .responseList(companyListResponseDTOList)
                    .build();
        }
        private PageRequest getPageable (PageDTO pageDTO){
            return PageRequest.of(
                    pageDTO.getPage() - 1,
                    pageDTO.getSize(),
                    Sort.by("reviewDate").descending()
            );
        }

        private List<CompanyListResponseDTO> getCompanyDTOList (Page < Company > companies) {
            return companies.getContent().stream()
                    .map(CompanyListResponseDTO::new)
                    .collect(toList());
        }

        //게시글 상세조회
    public CompanyDetailResponseDTO getDetail(Long companyIdx){
        Optional<Company> companyOptional = companyRepository.findById(companyIdx);

        if(companyOptional.isPresent()){
            Company company = companyOptional.get();
            return new CompanyDetailResponseDTO(company);
        } else {
//            throw new CompanyNotFoundException("Company not found with ID"+companyIdx);
        }
        return null;

    }








}

