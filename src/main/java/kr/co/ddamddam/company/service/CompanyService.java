package kr.co.ddamddam.company.service;

import kr.co.ddamddam.company.dto.page.PageDTO;
import kr.co.ddamddam.company.dto.page.PageResponseDTO;
import kr.co.ddamddam.company.dto.request.CompanyRequestDTO;
import kr.co.ddamddam.company.dto.response.CompanyListPageResponseDTO;
import kr.co.ddamddam.company.dto.response.CompanyListResponseDTO;
import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompanyService {


    private final CompanyRepository companyRepository;

    @Autowired
    private EntityManager entityManager;
    public static int INDENT_FACTOR = 4;

    //api xml데이터를 json으로 변경해서 DB에 저장
    @Transactional
    public void processExternalData(String url) throws IOException {

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
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
//        System.out.println(newJson.toString());

        CompanyRequestDTO dto = new CompanyRequestDTO();
        List<CompanyRequestDTO> wantedList = new ArrayList<>();

        System.out.println(wantedArray.length());
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
            dto.setCompanyTitle(title);
            dto.setCompanyName(company);
            dto.setCompanyCareer(career);
            dto.setCompanyUrl(wantedInfoUrl);
            dto.setCompanyArea(basicAddr);
            dto.setCompanyDetailArea(detailAddr);
            dto.setCompanySal(sal);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dto.setCompanyDate(regDt);
            dto.setCompanyEndDate(closeDt);


            // Create a new Company entity
            Company companyEntity = new Company();
            companyEntity.setCompanyName(company);
            companyEntity.setCompanyTitle(title);
            companyEntity.setCompanyCareer(career);
            companyEntity.setCompanyUrl(wantedInfoUrl);
            companyEntity.setCompanyArea(basicAddr+detailAddr);
            companyEntity.setCompanySal(sal);
//            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            companyEntity.setCompanyDate(regDt);
            companyEntity.setCompanyEnddate(closeDt);

            // Persist the Company entity
            entityManager.persist(companyEntity);
            entityManager.flush();

            // Add the DTO object to the list if needed
            wantedList.add(dto);

            for (CompanyRequestDTO dto2 : wantedList) {
                /*
                System.out.println("Title: " + dto2.getCompanyTitle());
                System.out.println("Company: " + dto2.getCompanyName());
                System.out.println("Career: " + dto2.getCompanyCareer());
                System.out.println("Wanted Info URL: " + dto2.getCompanyUrl());
                System.out.println("Basic Address: " + dto2.getCompanyArea());
                System.out.println("Detail Address: " + dto2.getCompanyDetailArea());
                System.out.println("Salary: " + dto2.getCompanySal());
                System.out.println("Registration Date: " + dto2.getCompanyDate());
                System.out.println("Closing Date: " + dto2.getCompanyEndDate());
                System.out.println("----------------------------------");

                 */
            }
        }
    }

    //전체목록 가져오기
    public CompanyListPageResponseDTO getList(PageDTO pageDTO){

        PageRequest pageable = getPageable(pageDTO);
        // 데이터베이스에서 게시글 목록 조회 후 DTO 리스트로 꺼내기
        Page<Company> companies = companyRepository.findAll(pageable);
        List<CompanyListResponseDTO> companyListResponseDTOList = getCompanyDTOList(companies);


        //JSON 형태로 변형
        return CompanyListPageResponseDTO.builder()
                .count(companyListResponseDTOList.size())
                .count(companyListResponseDTOList.size())
                .pageInfo(new PageResponseDTO<Company>(companies))
                .companyList(companyListResponseDTOList)
                .build();

    }
    private PageRequest getPageable(PageDTO pageDTO) {
        return PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getSize(),
                Sort.by("companyDate").descending()
        );
    }
    private List<CompanyListResponseDTO> getCompanyDTOList(Page<Company> companies) {

        return companies.getContent().stream()
                .map(CompanyListResponseDTO::new)
                .collect(toList());
    }

    //경력 별로 필터링
    public CompanyListPageResponseDTO getCareer(PageDTO pageDTO){

        PageRequest pageable = getPageable(pageDTO);
        // 데이터베이스에서 게시글 목록 조회 후 DTO 리스트로 꺼내기
        Page<Company> companies = companyRepository.findHavingCareer(pageable);
        List<CompanyListResponseDTO> companyListResponseDTOList = getCompanyDTOList(companies);


        //JSON 형태로 변형
        return CompanyListPageResponseDTO.builder()
                .count(companyListResponseDTOList.size())
                .count(companyListResponseDTOList.size())
                .pageInfo(new PageResponseDTO<Company>(companies))
                .companyList(companyListResponseDTOList)
                .build();

    }

    //신입으로 보기
    public CompanyListPageResponseDTO getNewCareer(PageDTO pageDTO){

        PageRequest pageable = getPageable(pageDTO);
        // 데이터베이스에서 게시글 목록 조회 후 DTO 리스트로 꺼내기
        Page<Company> companies = companyRepository.findCareer(pageable);
        List<CompanyListResponseDTO> companyListResponseDTOList = getCompanyDTOList(companies);


        //JSON 형태로 변형
        return CompanyListPageResponseDTO.builder()
                .count(companyListResponseDTOList.size())
                .count(companyListResponseDTOList.size())
                .pageInfo(new PageResponseDTO<Company>(companies))
                .companyList(companyListResponseDTOList)
                .build();

    }


    //경력관계없음
    public CompanyListPageResponseDTO getNoCareer(PageDTO pageDTO){

        PageRequest pageable = getPageable(pageDTO);
        // 데이터베이스에서 게시글 목록 조회 후 DTO 리스트로 꺼내기
        Page<Company> companies = companyRepository.findNoExperience(pageable);
        List<CompanyListResponseDTO> companyListResponseDTOList = getCompanyDTOList(companies);


        //JSON 형태로 변형
        return CompanyListPageResponseDTO.builder()
                .count(companyListResponseDTOList.size())
                .count(companyListResponseDTOList.size())
                .pageInfo(new PageResponseDTO<Company>(companies))
                .companyList(companyListResponseDTOList)
                .build();

    }

    // 키워드 검색
    public CompanyListPageResponseDTO getKeywordList(String keyword,String sort ,PageDTO pageDTO){

        PageRequest pageable = getPageable(pageDTO);
        Page<Company> companies = companyRepository.findByKeyword(keyword,sort,pageable);
        List<CompanyListResponseDTO> companyListResponseDTOS = getCompanyListKeyword(companies);

        return CompanyListPageResponseDTO.builder()
                .count(companyListResponseDTOS.size())
                .pageInfo(new PageResponseDTO<>(companies))
                .companyList(companyListResponseDTOS)
                .build();
    }

    private List<CompanyListResponseDTO> getCompanyListKeyword(Page<Company> companies) {
        return companies.stream()
                .map(CompanyListResponseDTO::new)
                .collect(toList());
    }

    //프론트엔드 불러오기
    public CompanyListPageResponseDTO getFornt(String keyword,PageDTO pageDTO){

        PageRequest pageable = getPageable(pageDTO);
        // 데이터베이스에서 게시글 목록 조회 후 DTO 리스트로 꺼내기
        Page<Company> companies = companyRepository.findFront(keyword,pageable);
        List<CompanyListResponseDTO> companyListResponseDTOList = getCompanyDTOList(companies);


        //JSON 형태로 변형
        return CompanyListPageResponseDTO.builder()
                .count(companyListResponseDTOList.size())
                .pageInfo(new PageResponseDTO<Company>(companies))
                .companyList(companyListResponseDTOList)
                .build();
    }

    //백엔드 불러오기
    public CompanyListPageResponseDTO getBack(String keyword,PageDTO pageDTO){

        PageRequest pageable = getPageable(pageDTO);
        // 데이터베이스에서 게시글 목록 조회 후 DTO 리스트로 꺼내기
        Page<Company> companies = companyRepository.findBack(keyword,pageable);
        List<CompanyListResponseDTO> companyListResponseDTOList = getCompanyDTOList(companies);


        //JSON 형태로 변형
        return CompanyListPageResponseDTO.builder()
                .count(companyListResponseDTOList.size())
                .pageInfo(new PageResponseDTO<Company>(companies))
                .companyList(companyListResponseDTOList)
                .build();
    }



}