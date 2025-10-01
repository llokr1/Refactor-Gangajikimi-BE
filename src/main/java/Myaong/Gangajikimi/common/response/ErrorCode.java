package Myaong.Gangajikimi.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Schema(description = "에러 응답 코드")
public enum ErrorCode {

    // COMMON
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "COMMON400", "유효하지 않는 값입니다."),

    // AUTH
    INVALIDATE_TOKEN(HttpStatus.BAD_REQUEST, "AUTH400", "토큰 정보가 유효하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH401", "AccessToken 유효 기간이 만료되었습니다."),
    TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "AUTH402", "토큰을 찾을 수 없습니다."),
    INVALIDATE_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH403", "비밀번호가 일치하지 않아요."),

    // MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER400", "존재하지 않는 회원 ID입니다."),
    EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, "MEMBER401", "이미 사용 중인 이메일입니다."),
    NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "MEMBER402", "이미 사용 중인 닉네임입니다."),

    // MESSAGE
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "MESSAGE400", "해당 메세지는 존재하지 않습니다."),
    CANNOT_DELETE_MASSAGE(HttpStatus.UNAUTHORIZED,"MESSAGE401","본인의 메세지만 삭제할 수 있습니다."),

    //CHATROOM
    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "CHATROOM400", "해당 채팅방은 존재하지 않습니다."),
    NOT_SAME_PEOPLE(HttpStatus.BAD_REQUEST, "CHATROOM4001", "본인과의 채팅은 생성할 수 없습니다."),

    //POST
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST400", "존재하지 않는 게시글입니다."),
    UNAUTHORIZED_UPDATING(HttpStatus.UNAUTHORIZED, "POST401", "게시글 수정 권한이 없습니다."),
    UNAUTHORIZED_DELETING(HttpStatus.UNAUTHORIZED, "POST402", "게시글 삭제 권한이 없습니다."),
    CANNOT_REPORT_OWN_POST(HttpStatus.BAD_REQUEST, "POST403", "본인의 게시글은 신고할 수 없습니다."),
    ALREADY_REPORTED(HttpStatus.BAD_REQUEST, "POST404", "이미 신고한 게시글입니다."),

    //DOGTYPE
    DOG_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "DOGTYPE404", "해당 견종을 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

}