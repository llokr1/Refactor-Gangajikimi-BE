package Myaong.Gangajikimi.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@Schema(description = "API 응답 공통 형식")
public class GlobalResponse {

    @Schema(description = "요청 성공 여부", example = "true")
    private final Boolean isSuccess;

    @Schema(description = "응답 코드", example = "COMMON200")
    private final String code;

    @Schema(description = "응답 메시지", example = "SUCCESS!")
    private final String message;

    @Schema(description = "응답 데이터 (성공 시에만 포함)", example = "{\"id\": 1, \"name\": \"example\"}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object result;

    public static ResponseEntity<GlobalResponse> onSuccess(SuccessCode successCode, Object result){
        return new ResponseEntity<>(
                new GlobalResponse(true, successCode.getCode(), successCode.getMessage(), result),
                successCode.getStatus());
    }

    public static ResponseEntity<GlobalResponse> onSuccess(SuccessCode successCode){
        return new ResponseEntity<>(
                new GlobalResponse(true, successCode.getCode(), successCode.getMessage(), null),
                successCode.getStatus());
    }

    public static ResponseEntity<GlobalResponse> onFailure(ErrorCode errorCode, Object result){
        return new ResponseEntity<>(
                new GlobalResponse(false, errorCode.getCode(), errorCode.getMessage(), result),
                errorCode.getStatus());
    }

    public static ResponseEntity<GlobalResponse> onFailure(ErrorCode errorCode){
        return new ResponseEntity<>(
                new GlobalResponse(false, errorCode.getCode(), errorCode.getMessage(), null),
                errorCode.getStatus());
    }

}