package kr.co.ddamddam.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationResponse<T> {

    private ApplicationResult result;
    private T payload;

    public static <T> ApplicationResponse<T> ok(T payload) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(HttpStatus.OK.value())
                        .message("ok")
                        .build())
                .payload(payload)
                .build();
    }

    public static <T> ApplicationResponse<T> bad(T payload) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("bad request")
                        .build())
                .payload(payload)
                .build();
    }

    public static <T> ApplicationResponse<T> error(T payload) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("internal server error")
                        .build())
                .payload(payload)
                .build();
    }

}
