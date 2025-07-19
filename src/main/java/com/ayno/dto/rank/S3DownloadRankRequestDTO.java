package com.ayno.dto.rank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "db 등록을 위한 S3 key값 요청 DTO")
public class S3DownloadRankRequestDTO {
    @Schema(description = "키값", example = "https://ayno-rank-csv.s3.ap-northeast-2.amazonaws.com/uploads/2025_07_AI.csv")
    private String s3Url;
}
