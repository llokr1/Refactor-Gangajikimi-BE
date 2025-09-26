package Myaong.Gangajikimi.s3file.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import Myaong.Gangajikimi.s3file.converter.S3Converter;
import Myaong.Gangajikimi.s3file.dto.PresignedUrlRequest;
import Myaong.Gangajikimi.s3file.dto.PresignedUrlResponse;
import Myaong.Gangajikimi.s3file.service.S3Service;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {

	private final S3Service s3Service;

	@PostMapping("/presigned-url")
	public ResponseEntity<GlobalResponse> generatePresignedUrl(@RequestBody PresignedUrlRequest request) {

		var presigned = s3Service.generatePresignedUrl(request.getFileName(), request.getContentType());
		PresignedUrlResponse response = S3Converter.toResponseDto(presigned);

		return GlobalResponse.onSuccess(SuccessCode.OK, response);
	}
}