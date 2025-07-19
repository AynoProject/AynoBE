package com.ayno.dto.rank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "RANK파일 업로드 S3 주소 요청 DTO")
public class S3UploadRankRequestDTO {
    @Schema(description = "파일이름", example = "2025_07_AI.csv")
    private String fileName;
    @Schema(description = "형식", example = "text/csv")
    private String contentType;
}
