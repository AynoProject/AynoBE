package com.ayno.controller;

import com.ayno.config.security.CustomUserDetails;
import com.ayno.dto.common.Response;
import com.ayno.dto.rank.DeleteCsvResponseDTO;
import com.ayno.dto.rank.GetCsvHistoryResponseDTO;
import com.ayno.dto.rank.RankUploadResponseDTO;
import com.ayno.service.RankService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @Operation(
            summary = "랭킹 CSV 파일 히스토리 가져오기",
            description = "관리자가 랭킹 CSV 파일 히스토리를 가져옵니다"
    )
    @GetMapping("/rank")
    public ResponseEntity<Response<List<GetCsvHistoryResponseDTO>>> getRankHistory(){
        return ResponseEntity.ok(Response.success(rankService.getRankHistory()));
    }

    @Operation(
            summary = "랭킹 CSV 파일 업로드",
            description = "관리자가 랭킹 CSV 파일을 업로드합니다."
    )
    @PostMapping(value = "/rank", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<RankUploadResponseDTO>> uploadRankCsv(
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        return ResponseEntity.ok(Response.success(rankService.uploadCsv(file, userDetails)));
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
