package kr.co.ddamddam.changxml;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.ddamddam.company.dto.request.CompanyRequestDTO;
import org.json.JSONObject;
import org.json.XML;

public class Main {
    public static int INDENT_FACTOR = 4;
    public static void main(String args[]) throws MalformedURLException,
            IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("https://openapi.work.go.kr/opi/opi/opia/wantedApi.do?authKey=WNLIS5RDCEK7WOBRD73GA2VR1HJ&returnType=xml&display=50100&callTp=L&region=11000&keyword==%EA%B0%9C%EB%B0%9C%EC%9E%90").openConnection();
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

//        //Json 키값 변경하기
        JSONObject jsonObject = new JSONObject(jsonPrettyPrintString);
//        jsonObject.put("companyName", jsonObject.get("company"));
//        jsonObject.put("companyTitle", jsonObject.get("title"));
//        jsonObject.put("companyCareer", jsonObject.get("career"));
//        jsonObject.put("companyArea", jsonObject.get("basicAddr"));
//        jsonObject.put("companyUrl", jsonObject.get("wantedInfoUrl"));
//        jsonObject.put("companySal", jsonObject.get("sal"));
//        jsonObject.put("companyDate", jsonObject.get("regDt"));
//        jsonObject.put("companyEnddate", jsonObject.get("closeDt"));


        System.out.println(jsonObject.toString(INDENT_FACTOR));
//        System.out.println(jsonObject.toString());

//
//        //dto로 전달할 ObjectMapper 생성
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // Deserialize JSON to DTO class
//        CompanyRequestDTO companyRequestDTO = objectMapper.readValue(jsonPrettyPrintString, CompanyRequestDTO.class);

        // Access the data in the DTO
//        System.out.println("title: " + companyRequestDTO.getCompanyTitle());
//        System.out.println("name: " + companyRequestDTO.getCompanyName());
//        System.out.println("Content: " + companyRequestDTO.getCompanyName());
//        System.out.println("name: " + companyRequestDTO.getCompanyName());
//        System.out.println("name: " + companyRequestDTO.getCompanyName());
//        System.out.println("name: " + companyRequestDTO.getCompanyName());
//        System.out.println("name: " + companyRequestDTO.getCompanyName());
//        System.out.println("name: " + companyRequestDTO.getCompanyName());
//        System.out.println("name: " + companyRequestDTO.getCompanyName());
//        System.out.println("name: " + companyRequestDTO.getCompanyName());



    }

}
