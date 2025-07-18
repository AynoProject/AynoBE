package com.ayno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rank")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rank extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rankId;

    @Column(nullable = false)
    private Long rank;

    @Column(nullable = false)
    private Long score;

    @Column(nullable = false)
    private Long mauScore;

    @Column(nullable = false)
    private Long monthMauChangeRateScore;

    @Column(nullable = false)
    private Long avgStayTimeScore;

    @Column(nullable = false)
    private Long avgPagesPerVisitScore;

    @Column(nullable = false)
    private Long boundRateScore;

    @Column(nullable = false)
    private Boolean isLatest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toolName", nullable = false)
    private Tool tool;

    public void setIsLatest(boolean isLatest) {
        this.isLatest = isLatest;
    }
}
