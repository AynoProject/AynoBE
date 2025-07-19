package com.ayno.controller;

import com.ayno.dto.common.Response;
import com.ayno.dto.news.*;
import com.ayno.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @Operation(
            summary = "뉴스 생성",
            description = "뉴스 요약을 생성합니다"
    )
    @PostMapping("/admin/news")
    public ResponseEntity<Response<CreateNewsResponseDTO>> createNews(
            @RequestBody CreateNewsRequestDTO request)
    {
        return ResponseEntity.ok(Response.success(newsService.createNews(request)));
    }

    @Operation(
            summary = "뉴스 전체 조회",
            description = "모든 뉴스와 페이지네이션 관련정보를 가져옵니다"
    )
    @GetMapping("/news")
    public ResponseEntity<Response<Page<ShowNewsResponseDTO>>> getAllNews(
            @ParameterObject
            @PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable)
    {
        return ResponseEntity.ok(Response.success(newsService.getAllNews(pageable)));
    }

    @Operation(
            summary = "뉴스 하나 상세 조회",
            description = "뉴스 하나 상세 정보를 가져옵니다"
    )
    @GetMapping("/news/{newsId}")
    public ResponseEntity<Response<ShowNewsDetailsResponseDTO>> getAllNews(
            @PathVariable Long newsId)
    {
        return ResponseEntity.ok(Response.success(newsService.getNewsDetails(newsId)));
    }

    @Operation(
            summary = "뉴스 수정",
            description = "뉴스 하나 상세 정보를 수정합니다"
    )
    @PutMapping("/admin/news/{newsId}")
    public ResponseEntity<Response<UpdateNewsResponseDTO>> updateNews(
            @RequestBody UpdateNewsRequestDTO request,
            @PathVariable Long newsId)
    {
        return ResponseEntity.ok(Response.success(newsService.updateNews(newsId, request)));
    }

    @Operation(
            summary = "뉴스 삭제",
            description = "뉴스 하나를 삭제합니다"
    )
    @DeleteMapping("/admin/news/{newsId}")
    public ResponseEntity<Response<DeleteNewsResponseDTO>> deleteNews(
            @PathVariable Long newsId)
    {
        return ResponseEntity.ok(Response.success(newsService.deleteNews(newsId)));
    }
}
