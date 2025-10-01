package Myaong.Gangajikimi.dogtype.web.controller;

import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import Myaong.Gangajikimi.dogtype.service.DogTypeService;
import Myaong.Gangajikimi.dogtype.web.docs.DogTypeControllerDocs;
import Myaong.Gangajikimi.dogtype.web.dto.DogTypeAutocompleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dog-types")
@RequiredArgsConstructor
public class DogTypeController implements DogTypeControllerDocs {

    private final DogTypeService dogTypeService;

    @GetMapping("/search")
    public ResponseEntity<GlobalResponse> searchDogTypes(@RequestParam("keyword") String keyword) {
        List<String> responses = dogTypeService.searchDogTypes(keyword);
        return GlobalResponse.onSuccess(SuccessCode.OK, responses);
    }

    /*
     * 혹시 몰라서 만들어둔 모든 견종 이름 조회 API
     */
    @GetMapping
    public ResponseEntity<GlobalResponse> getAllDogTypes() {
        List<String> responses = dogTypeService.getAllDogTypes();
        return GlobalResponse.onSuccess(SuccessCode.OK, responses);
    }
}
