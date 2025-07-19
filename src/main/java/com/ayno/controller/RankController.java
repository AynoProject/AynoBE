package com.ayno.controller;

import com.ayno.config.security.CustomUserDetails;
import com.ayno.dto.common.Response;
import com.ayno.dto.rank.*;
import com.ayno.service.RankService;
import com.ayno.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;
    private final S3Service s3Service;

    @Operation(
            summary = "랭킹 CSV 파일 히스토리 가져오기",
            description = "관리자가 랭킹 CSV 파일 히스토리를 가져옵니다"
    )
    @GetMapping("/rank")
    public ResponseEntity<Response<List<GetCsvHistoryResponseDTO>>> getRankHistory(){
        return ResponseEntity.ok(Response.success(rankService.getRankHistory()));
    }

    @Operation(
            summary = "랭킹 CSV 파일 업로드 용 Presigned URL 발급",
            description = "관리자가 S3에 랭킹 CSV 파일을 업로드할 수 있도록 서명된 URL을 발급합니다."
    )
    @PostMapping("/rank/upload-url")
    public ResponseEntity<Response<String>> generateRankUploadUrl(@RequestBody S3UploadRankRequestDTO request) {
        String url = s3Service.generatePresignedUrl(request.getFileName(), request.getContentType());
        return ResponseEntity.ok(Response.success(url));
    }

    @Operation(
            summary = "S3에 업로드된 랭킹 CSV 파일 db등록",
            description = "관리자가 업로드된 S3 CSV 파일 경로를 전달하여 랭킹을 db에 등록합니다."
    )
    @PostMapping(value = "/rank", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<RankUploadResponseDTO>> uploadRankCsv(
            @ModelAttribute S3DownloadRankRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        return ResponseEntity.ok(Response.success(rankService.uploadCsvFromS3(request.getS3Url(), userDetails)));
    }

    @Operation(
            summary = "랭킹 CSV 파일 삭제",
            description = "관리자가 최신 버전 랭킹 CSV 파일을 삭제하고 이전 버전으로 변경"
    )
    @DeleteMapping("/rank/delete/latest")
    public ResponseEntity<Response<DeleteCsvResponseDTO>> deleteCsv(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity.ok(Response.success(rankService.deleteLatestCsvUpload(userDetails)));
    }


}
