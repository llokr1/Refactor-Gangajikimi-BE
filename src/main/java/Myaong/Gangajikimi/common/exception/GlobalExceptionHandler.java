package Myaong.Gangajikimi.common.exception;

import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse> handleGenericException(Exception e) {
        ErrorCode errorCode =
                ErrorCode.INTERNAL_SERVER_ERROR;
        log.error("Unexpected Error Occured");
        log.error(e.getMessage(), e);
        log.error(e.getClass().getSimpleName());

        return GlobalResponse.onFailure(errorCode);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<GlobalResponse> handleGeneralException(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();

        return GlobalResponse.onFailure(errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Valid 검증 실패시 예외처리
    public ResponseEntity<GlobalResponse> handleValidationExceptions(
            MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;

        // 모든 검증 오류를 하나의 문자열로 결합
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return GlobalResponse.onFailure(errorCode, errorMessage);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class) // 파일 크기 초과 예외처리
    public ResponseEntity<GlobalResponse> handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException e) {
        log.error("파일 크기 초과: {}", e.getMessage());
        return GlobalResponse.onFailure(ErrorCode.FILE_SIZE_EXCEEDED);
    }

    @ExceptionHandler(com.fasterxml.jackson.core.JsonParseException.class) // JSON 파싱 오류 처리
    public ResponseEntity<GlobalResponse> handleJsonParseException(
            com.fasterxml.jackson.core.JsonParseException e) {
        log.error("JSON 파싱 오류: {}", e.getMessage());
        return GlobalResponse.onFailure(ErrorCode.VALIDATION_FAILED);
    }

    @ExceptionHandler(com.fasterxml.jackson.databind.JsonMappingException.class) // JSON 매핑 오류 처리
    public ResponseEntity<GlobalResponse> handleJsonMappingException(
            com.fasterxml.jackson.databind.JsonMappingException e) {
        log.error("JSON 매핑 오류: {}", e.getMessage());
        return GlobalResponse.onFailure(ErrorCode.VALIDATION_FAILED);
    }

}