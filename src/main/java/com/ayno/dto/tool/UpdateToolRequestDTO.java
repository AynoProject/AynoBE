package com.ayno.dto.tool;

import com.ayno.entity.enums.Category;
import com.ayno.entity.enums.PriceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "AI툴 내용 수정 요청 DTO")
public class UpdateToolRequestDTO {
    @Schema(description = "툴 이름", example = "ChatGPT")
    private String toolName;
    @Schema(description = "로고Url", example = "https://ayno-rank-csv.s3.ap-northeast-2.amazonaws.com/uploads/tool-logos/ChatGPT.csv")
    private String logoUrl;
    @Schema(description = "카테고리", example = "DOCUMENT_EDITING")
    private Category category;
    @Schema(description = "툴 주소", example = "https://chatgpt.com/")
    private String toolLink;
    @Schema(description = "툴 정보", example = "가장 유명하고 많이 쓰는 생성형 ai 입니다")
    private String toolInfor;
    @Schema(description = "무료/유료", example = "FREE")
    private PriceType isFree;
}
