package com.ayno.dto.news;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowNewsResponseDTO {
    private String title;
    private String newsImageUrl;
    private String summary;
    private String newsLink;
    private LocalDateTime createdAt;
}
