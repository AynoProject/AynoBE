package com.ayno.dto.news;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "뉴스 생성 요청 DTO")
public class CreateNewsRequestDTO {
    @Schema(description = "뉴스 제목", example = "뉴스로 지식 쌓는 쳇 gpt")
    private String title;
    @Schema(description = "뉴스 이미지Url", example = "https://ayno-rank-csv.s3.ap-northeast-2.amazonaws.com/uploads/news-image/ChatGPT.csv")
    private String newsImageUrl;
    @Schema(description = "뉴스 요약", example = "언론사가 지금까지 이뤄놓은 많은 작업물을 쳇 gpt가 마음껏 빨아들이도록 방치하는것은 끔찍한 실수가 될 것이다")
    private String summary;
    @Schema(description = "뉴스 주소", example = "https://naver.com")
    private String newsLink;
}
