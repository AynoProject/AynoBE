package com.ayno.entity;

import com.ayno.entity.enums.Category;
import com.ayno.entity.enums.PriceType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tool")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tool {
    @Id
    @Column(length = 50)
    private String toolName;

    @Column(length = 200)
    private String logoUrl;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = true , length = 200)
    private String toolLink;

    @Column(nullable = true, length = 200)
    private String toolInfor;

    @Column(nullable = true)
    private PriceType isFree;

    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rank> ranks = new ArrayList<>();

    public void updateToolInfor(String toolName, String logoUrl, Category category,
                                String toolLink, String toolInfor, PriceType isFree) {
        this.toolName = toolName;
        this.logoUrl = logoUrl;
        this.category = category;
        this.toolLink = toolLink;
        this.toolInfor = toolInfor;
        this.isFree = isFree;
    }
}
