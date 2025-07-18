package com.ayno.entity;

import com.ayno.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admin")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin extends BaseTimeEntity{
    @Id
    @Column(length = 50)
    private String Id;  // 관리자 ID (관리자로그인용 ID, 이메일 아님)

    @Column(nullable = false, length = 200)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToMany(mappedBy = "admin")
    private List<Log> logs = new ArrayList<>();
}
