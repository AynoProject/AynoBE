package com.ayno.entity;

import com.ayno.entity.enums.ActionType;
import com.ayno.entity.enums.TargetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "log")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ActionType actionType; // CREATE, UPDATE, DELETE, UPLOAD 등

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TargetType targetType; // RANK, NEWS, TOOL 등

    @Column(length = 200)
    private String targetName; // ex) ChatGPT, 뉴스 제목 등

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id", nullable = true)
    private Admin admin;
}
