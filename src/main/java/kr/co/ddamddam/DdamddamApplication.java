package kr.co.ddamddam;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.ddamddam.company.dto.request.CompanyRequestDTO;
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




			System.out.println(jsonObject.toString(INDENT_FACTOR));


        //dto로 전달할 ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // Deserialize JSON to DTO class
        CompanyRequestDTO companyRequestDTO = objectMapper.readValue(jsonPrettyPrintString, CompanyRequestDTO.class);

		//	 DTO에 접근
        System.out.println("title: " + companyRequestDTO.getTitle());
        System.out.println("name: " + companyRequestDTO.getCompany());
        System.out.println("career: " + companyRequestDTO.getCareer());
        System.out.println("url: " + companyRequestDTO.getWantedInfoUrl());
        System.out.println("adddr: " + companyRequestDTO.getBasicAddr() + companyRequestDTO.getDetailAddr());
        System.out.println("sal: " + companyRequestDTO.getSal());
        System.out.println("regdate: " + companyRequestDTO.getRegDt());
        System.out.println("enddate: " + companyRequestDTO.getCloseDt());
		}

	}
