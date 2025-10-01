package Myaong.Gangajikimi.dogtype.web.docs;

import Myaong.Gangajikimi.common.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "DogType", description = "견종 관련 API")
public interface DogTypeControllerDocs {

    @Operation(
            summary = "견종 자동완성 검색",
            description = "견종명의 일부를 입력하면 해당하는 견종 목록을 반환합니다. (최소 2글자 입력 필요) 예: '말' 입력 시 '말티즈' 반환"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "검색 성공",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GlobalResponse.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": "COMMON200",
                                                "message": "SUCCESS!",
                                                "result": [
                                                    "말티즈"
                                                ]
                                            }
                                            """,
                                    description = "result: String 배열 - 매칭되는 견종명 목록 (예: '말' 검색 시 '말티즈' 반환)"
                            )
                    )
            )
    })
    ResponseEntity<GlobalResponse> searchDogTypes(
            @Parameter(description = "검색 키워드 (최소 2글자)", example = "말")
            String keyword
    );

    @Operation(
            summary = "전체 견종 목록 조회",
            description = "등록된 모든 견종 목록을 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GlobalResponse.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": "COMMON200",
                                                "message": "SUCCESS!",
                                                "result": [
                                                    "믹스견",
                                                    "알 수 없음",
                                                    "시바견",
                                                    "푸들",
                                                    "말티즈",
                                                    "골든리트리버",
                                                    "래브라도리트리버",
                                                    "비글",
                                                    "불독",
                                                    "치와와",
                                                    "요크셔테리어",
                                                    "포메라니안",
                                                    "허스키",
                                                    "진돗개"
                                                ]
                                            }
                                            """,
                                    description = "result: String 배열 - 전체 견종명 목록"
                            )
                    )
            )
    })
    ResponseEntity<GlobalResponse> getAllDogTypes();
}
