package com.ayno.dto.rank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "이미지 업로드 S3 주소 요청 DTO")
public class S3UploadAiLogoRequestDTO {
    @Schema(description = "이미지이름", example = "ChatGPT.svg")
    private String fileName;
    @Schema(description = "형식", example = "image/svg+xml")
    private String contentType;
}
