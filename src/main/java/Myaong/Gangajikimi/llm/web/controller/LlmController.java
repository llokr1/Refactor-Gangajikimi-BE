package Myaong.Gangajikimi.llm.web.controller;

import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import Myaong.Gangajikimi.llm.service.LlmService;
import Myaong.Gangajikimi.llm.web.dto.request.LlmRequest;
import Myaong.Gangajikimi.llm.web.dto.response.LlmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/llm-api")
@RequiredArgsConstructor
public class LlmController {

    private final LlmService llmService;

    @PostMapping("/test1")
    public ResponseEntity<GlobalResponse> getFormattedOutput(@RequestBody LlmRequest request){

        return GlobalResponse.onSuccess(SuccessCode.OK, llmService.formattingDogInfo(request));

    }


}
