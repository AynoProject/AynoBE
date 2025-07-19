package com.ayno.repository;

import com.ayno.dto.news.ShowNewsDetailsResponseDTO;
import com.ayno.dto.news.ShowNewsResponseDTO;
import com.ayno.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT new com.ayno.dto.news.ShowNewsResponseDTO(" +
            "n.title, n.newsImageUrl, n.summary, n.newsLink, n.createdAt) " +
            "FROM News n ")
    Page<ShowNewsResponseDTO> findAllNewsAsDTO(Pageable pageable);

    @Query("SELECT new com.ayno.dto.news.ShowNewsDetailsResponseDTO(" +
            "n.title, n.newsImageUrl, n.summary, n.newsLink) " +
            "FROM News n " +
            "WHERE n.newsId = :newsId")
    Optional<ShowNewsDetailsResponseDTO> findNewsByNewsIdAsDTO(Long newsId);

    Optional<News> findNewsByNewsId(Long newsId);
}
