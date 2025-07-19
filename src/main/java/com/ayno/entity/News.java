package com.ayno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "news")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Column(nullable = false)
    private String title;

    @Column(length = 200)
    private String newsImageUrl;

    @Column(nullable = false, length = 200)
    private String summary;

    @Column(nullable = false, length = 200)
    private String newsLink;

    public void updateNews(String title, String newsImageUrl,
                               String summary, String newsLink) {
        this.title = title;
        this.newsImageUrl = newsImageUrl;
        this.summary = summary;
        this.newsLink = newsLink;
    }
}
