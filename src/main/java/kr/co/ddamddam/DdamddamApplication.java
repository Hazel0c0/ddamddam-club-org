package kr.co.ddamddam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

@SpringBootApplication
public class DdamddamApplication {


    public static void main(String[] args) {
        SpringApplication.run(DdamddamApplication.class, args);
        String inputUrl = "http://openapi.work.go.kr/opi/opi/opia/wantedApi.do?authKey=WNLIS5RDCEK7WOBRD73GA2VR1HJ&returnType=xml&display=100&callTp=L&region=11000&keyword==%EA%B0%9C%EB%B0%9C%EC%9E%90";
        try {
            URL url = new URL(inputUrl);
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            String result = bf.readLine();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void xmlToJson(String str) {

        String xml = str;
//        JSONOB
    }

}
