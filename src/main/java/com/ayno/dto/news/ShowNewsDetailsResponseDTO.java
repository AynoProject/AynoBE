package com.ayno.dto.news;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowNewsDetailsResponseDTO {
    private String title;
    private String newsImageUrl;
    private String summary;
    private String newsLink;
}
