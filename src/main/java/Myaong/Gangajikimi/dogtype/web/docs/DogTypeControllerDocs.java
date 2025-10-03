package Myaong.Gangajikimi.dogtype.web.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "DogType", description = "견종 관리 API")
public interface DogTypeControllerDocs {

    @Operation(summary = "견종 자동완성", description = "입력된 키워드가 포함된 견종을 검색합니다. 최소 2글자 이상 입력해야 하며, 키워드가 포함된 전체 견종명을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200", 
                description = "검색 성공",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = String.class),
                    examples = @ExampleObject(
                        name = "자동완성 검색 예시",
                        summary = "키워드 '말티'로 검색한 결과 - '말티'가 포함된 모든 견종명",
                        value = "[\"말티즈\"]"
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "잘못된 요청 (키워드가 2글자 미만인 경우)",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "에러 응답 예시",
                        summary = "키워드가 너무 짧은 경우 (1글자 입력)",
                        value = "[]"
                    )
                )
            )
    })
    List<String> searchDogTypes(
            @Parameter(
                description = "검색 키워드 (최소 2글자) - 혹시 몰라서 구현해놓음", 
                example = "말티",
                required = true
            )
            @RequestParam String keyword
    );

    @Operation(summary = "전체 견종 목록", description = "DB에 등록된 모든 견종 목록을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200", 
                description = "조회 성공",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = String.class),
                    examples = @ExampleObject(
                        name = "전체 견종 목록 예시",
                        summary = "DB에 등록된 모든 견종",
                        value = "[\"골든 리트리버\", \"래브라도 리트리버\", \"말티즈\", \"말라뮤트\", \"불독\", \"치와와\", \"푸들\", \"허스키\", \"요크셔테리어\", \"비글\"]"
                    )
                )
            )
    })
    List<String> getAllDogTypes();
}