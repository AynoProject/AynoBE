package com.ayno.service;

import com.ayno.config.exception.CustomException;
import com.ayno.dto.news.*;
import com.ayno.entity.News;
import com.ayno.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    public CreateNewsResponseDTO createNews(CreateNewsRequestDTO request){
        News news = News.builder()
                .title(request.getTitle())
                .newsImageUrl(request.getNewsImageUrl())
                .summary(request.getSummary())
                .newsLink(request.getNewsLink())
                .build();

        newsRepository.save(news);

        return new CreateNewsResponseDTO("뉴스 생성이 성공하였습니다");
    }

    public Page<ShowNewsResponseDTO> getAllNews(Pageable pageable){
        return newsRepository.findAllNewsAsDTO(pageable);
    }

    public ShowNewsDetailsResponseDTO getNewsDetails(Long newsId){
        return newsRepository.findNewsByNewsIdAsDTO(newsId)
                .orElseThrow(() -> new CustomException("NEWS_NOT_FOUND", "해당 뉴스가 존재하지 않습니다"));
    }

    @Transactional
    public UpdateNewsResponseDTO updateNews(Long newsId, UpdateNewsRequestDTO request){
        News news = newsRepository.findNewsByNewsId(newsId)
                .orElseThrow(() -> new CustomException("NEWS_NOT_FOUND", "해당 뉴스가 존재하지 않습니다"));

        news.updateNews(request.getTitle(), request.getNewsImageUrl(),
                request.getSummary(), request.getNewsLink());

        return new  UpdateNewsResponseDTO("뉴스 요약이 성공적으로 수정되었습니다", news.getNewsId());
    }

    @Transactional
    public DeleteNewsResponseDTO deleteNews(Long newsId){
        News news = newsRepository.findNewsByNewsId(newsId)
                .orElseThrow(() -> new CustomException("NEWS_NOT_FOUND", "해당 뉴스가 존재하지 않습니다"));

        newsRepository.delete(news);

        return new DeleteNewsResponseDTO("클립 삭제 완료", news.getNewsId());
    }



}
