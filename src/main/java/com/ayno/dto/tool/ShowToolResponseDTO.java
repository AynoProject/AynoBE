package com.ayno.dto.tool;

import com.ayno.entity.enums.Category;
import com.ayno.entity.enums.PriceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowToolResponseDTO {
    private String toolName;
    private String logoUrl;
    private Category category;
    private String toolLink;
    private String toolInfor;
    private PriceType isFree;
    private Long rank;
    private Long score;
}
