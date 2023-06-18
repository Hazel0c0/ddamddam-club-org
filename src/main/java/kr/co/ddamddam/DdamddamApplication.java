package kr.co.ddamddam;

import kr.co.ddamddam.company.dto.request.CompanyRequestDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DdamddamApplication {
    public static int INDENT_FACTOR = 4;

    // 조경훈 찬양해!
    // 조경훈은 멋있어?
    public static void main(String[] args) throws IOException {
        SpringApplication.run(DdamddamApplication.class, args);


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

        CompanyRequestDTO dto = new CompanyRequestDTO();
        List<CompanyRequestDTO> wantedList = new ArrayList<>();

        for (int i=0; i< wantedArray.length(); i++) {
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

            // Print the extracted values if desired
//            System.out.println("title: " + title);
//            System.out.println("company: " + company);
//            System.out.println("career: " + career);
//            System.out.println("wantedInfoUrl: " + wantedInfoUrl);
//            System.out.println("basicAddr: " + basicAddr);
//            System.out.println("detailAddr: " + detailAddr);
//            System.out.println("sal: " + sal);
//            System.out.println("regDt: " + regDt);
//            System.out.println("closeDt: " + closeDt);
        }

        //dto로 전달할 ObjectMapper 생성
//        ObjectMapper objectMapper = new ObjectMapper();

        // Deserialize JSON to DTO class
//        CompanyRequestDTO companyRequestDTO = objectMapper.readValue(jsonPrettyPrintString, CompanyRequestDTO.class);

        //	 DTO에 접근
//        for (int i =0 ; i< wantedArray.length(); i++){
//            System.out.println("title: " + companyRequestDTO.getTitle());
//            System.out.println("name: " + companyRequestDTO.getCompany());
//            System.out.println("career: " + companyRequestDTO.getCareer());
//            System.out.println("url: " + companyRequestDTO.getWantedInfoUrl());
//            System.out.println("adddr: " + companyRequestDTO.getBasicAddr() + companyRequestDTO.getDetailAddr());
//            System.out.println("sal: " + companyRequestDTO.getSal());
//            System.out.println("regdate: " + companyRequestDTO.getRegDt());
//            System.out.println("enddate: " + companyRequestDTO.getCloseDt());
//
//        }
    }

}
